package me.tbandawa.web.skyzmetro.services;

import me.tbandawa.web.skyzmetro.dtos.MemberDto;
import me.tbandawa.web.skyzmetro.dtos.PagedMembersDto;
import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface RegistrarService {
    PagedMembersDto getMembers(Authentication authentication, int page);
    MemberDto saveMember(Authentication authentication, MemberDto memberDto, MultipartFile photo);
    MemberDto getMember(Authentication authentication, long id);
    List<MemberDto> searchMembers(Authentication authentication, String query);
    MemberDto editMember(Authentication authentication, MemberDto memberDto);
    MemberDto activateMember(Authentication authentication, long id, boolean active);
    List<MemberDto> printMembers(List<Long> members);
}
