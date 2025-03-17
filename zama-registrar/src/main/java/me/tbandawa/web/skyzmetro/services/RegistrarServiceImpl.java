package me.tbandawa.web.skyzmetro.services;

import me.tbandawa.web.skyzmetro.daos.MemberDao;
import me.tbandawa.web.skyzmetro.daos.ProvinceDao;
import me.tbandawa.web.skyzmetro.daos.UserDao;
import me.tbandawa.web.skyzmetro.dtos.*;
import me.tbandawa.web.skyzmetro.entities.Member;
import me.tbandawa.web.skyzmetro.entities.User;
import me.tbandawa.web.skyzmetro.exceptions.InvalidFileTypeException;
import me.tbandawa.web.skyzmetro.exceptions.NotProcessedException;
import me.tbandawa.web.skyzmetro.exceptions.ResourceNotFoundException;
import me.tbandawa.web.skyzmetro.utils.ClientSessions;
import me.tbandawa.web.skyzmetro.utils.ResourceType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.socket.TextMessage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RegistrarServiceImpl implements RegistrarService {

    private final UserDao userDao;

    private final MemberDao memberDao;

    private final MembersMapper membersMapper;

    private final ProvinceDao provinceDao;

    private final ProvinceMapper provinceMapper;

    private final ImageService imageService;

    private final UserMapper userMapper;

    private final MembershipCardBuilder membershipCardBuilder;

    private final ClientSessions clientSessions;

    public RegistrarServiceImpl(
            UserDao userDao,
            MemberDao memberDao,
            MembersMapper membersMapper,
            ProvinceDao provinceDao,
            ProvinceMapper provinceMapper,
            ImageService imageService,
            UserMapper userMapper,
            MembershipCardBuilder membershipCardBuilder,
            ClientSessions clientSessions
    ) {
        this.userDao = userDao;
        this.memberDao = memberDao;
        this.membersMapper = membersMapper;
        this.provinceDao = provinceDao;
        this.provinceMapper = provinceMapper;
        this.imageService = imageService;
        this.userMapper = userMapper;
        this.membershipCardBuilder = membershipCardBuilder;
        this.clientSessions = clientSessions;
    }

    @Override
    public PagedMembersDto getMembers(Authentication authentication, int page) {
        PagedMembersDto pagedMembersDto = membersMapper.mapToPagedMembersDto(memberDao.getMembers(page));
        pagedMembersDto.getResults().stream()
                .peek(memberDto -> {
                    memberDto.setPhoto(imageService.getImage(memberDto.getId(), ResourceType.ZAMA_MEMBER));
                    Optional<User> optionalUser = userDao.getUser(getActiveUser(authentication).getId());
                    optionalUser.ifPresent(user -> memberDto.setAddedBy(userMapper.mapToDto(user)));
                })
                .collect(Collectors.toList());
        return pagedMembersDto;
    }

    @Override
    public MemberDto saveMember(Authentication authentication, MemberDto memberDto, MultipartFile photo) {

        ProvinceDto provinceDto = provinceDao.getProvinces().stream()
                .map(provinceMapper::mapToProvinceDto)
                .filter(dto -> dto.getName().equalsIgnoreCase(memberDto.getDistrict()))
                .findAny()
                .orElse(null);

        if (provinceDto == null) {
            throw new NotProcessedException("Province/District not found: " + memberDto.getDistrict());
        }

        Member member = membersMapper.mapToMemberEntity(memberDto);
        member.setAddedBy(getActiveUser(authentication).getId());
        member = memberDao.saveMember(member);

        member.setMembershipNumber(provinceDto.getCode() + String.format("%05d", member.getId()));

        if (memberDao.editMember(member) > 0){
            memberDto.setMembershipNumber(member.getMembershipNumber());
        }

        if (photo == null) {
            throw new InvalidFileTypeException("Select Member Image");
        }
        if (member.getId() < 1) {
            throw new NotProcessedException("Could not save Member");
        }
        memberDto.setId(member.getId());
        Optional<User> optionalUser = userDao.getUser(getActiveUser(authentication).getId());
        optionalUser.ifPresent(user -> memberDto.setAddedBy(userMapper.mapToDto(user)));
        if (!photo.isEmpty()) {
            memberDto.setPhoto(imageService.saveImage(memberDto.getId(), photo, ResourceType.ZAMA_MEMBER));
        } else {
            memberDto.setPhoto(new PhotoDto());
        }
        updateMembersCall(memberDto);
        return memberDto;
    }

    @Override
    public MemberDto getMember(Authentication authentication, long id) {
        Member member = memberDao.getMember(id)
                .orElseThrow(() -> new ResourceNotFoundException("Member with id: " + id + " not found"));
        MemberDto memberDto = membersMapper.mapToMemberDto(member);
        Optional<User> optionalUser = userDao.getUser(getActiveUser(authentication).getId());
        optionalUser.ifPresent(user -> memberDto.setAddedBy(userMapper.mapToDto(user)));
        memberDto.setPhoto(imageService.getImage(id, ResourceType.ZAMA_MEMBER));
        return memberDto;
    }

    @Override
    public List<MemberDto> searchMembers(Authentication authentication, String query) {
        return memberDao.searchMembers(query).stream()
                .map(membersMapper::mapToMemberDto)
                .peek(memberDto -> {
                    memberDto.setPhoto(imageService.getImage(memberDto.getId(), ResourceType.ZAMA_MEMBER));
                    Optional<User> optionalUser = userDao.getUser(getActiveUser(authentication).getId());
                    optionalUser.ifPresent(user -> memberDto.setAddedBy(userMapper.mapToDto(user)));
                })
                .collect(Collectors.toList());
    }

    @Override
    public MemberDto editMember(Authentication authentication, MemberDto memberDto) {
        Member member = membersMapper.mapToMemberEntity(memberDto);
        if (memberDao.editMember(member) != member.getId()) {
            throw new NotProcessedException("Could not update member changes");
        }
        return memberDto;
    }

    @Override
    public MemberDto activateMember(Authentication authentication, long id, boolean active) {
        if (memberDao.activateMember(id, active) == 0) {
            throw new NotProcessedException("Could not update member changes");
        }
        return getMember(authentication, id);
    }

    @Override
    public ExportDto printMembers(List<Long> members) {
        if (members.isEmpty()) {
            throw new NotProcessedException("No members selected to print");
        }
        List<MemberDto> memberDtoList = new ArrayList<>();
        for (Long id : members) {
            if (memberDao.printMember(id, true) == 0) {
                throw new NotProcessedException("Could not update member changes");
            } else {
                memberDao.getMember(id).ifPresent(member -> {
                    MemberDto memberDto = membersMapper.mapToMemberDto(member);
                    memberDto.setPhoto(imageService.getImage(memberDto.getId(), ResourceType.ZAMA_MEMBER));
                    memberDtoList.add(memberDto);
                });
            }
        }
        return membershipCardBuilder.buildCards(memberDtoList);
    }

    @Async
    private void updateMembersCall(MemberDto memberDto) {
        clientSessions.getWebSocketSessions()
                .forEach(webSocketSession -> {
                    try {
                        webSocketSession.sendMessage(new TextMessage("New member, " + memberDto.getNames() + "  " + memberDto.getSurname() + ", registered"));
                    } catch (IOException e) {
                        e.printStackTrace(System.out);
                    }
                });
    }

    private UserDetailsImpl getActiveUser(Authentication authentication) {
        return (UserDetailsImpl) authentication.getPrincipal();
    }
}
