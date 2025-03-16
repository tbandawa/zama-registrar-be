package me.tbandawa.web.skyzmetro.services;

import me.tbandawa.web.skyzmetro.dtos.PhotoDto;
import me.tbandawa.web.skyzmetro.dtos.MemberDto;
import me.tbandawa.web.skyzmetro.dtos.PagedMembersDto;
import me.tbandawa.web.skyzmetro.entities.Member;
import me.tbandawa.web.skyzmetro.entities.PagedMembers;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class MembersMapper {

    public PagedMembersDto mapToPagedMembersDto(PagedMembers entity) {
        PagedMembersDto dto = new PagedMembersDto();
        dto.setCount(entity.getCount());
        dto.setPerPage(entity.getPerPage());
        dto.setCurrentPage(entity.getCurrentPage());
        dto.setNextPage(entity.getNextPage());
        dto.setResults(
                Optional.ofNullable(entity.getResults())
                        .orElseGet(Collections::emptyList)
                        .stream()
                        .map(this::mapToMemberDto)
                        .collect(Collectors.toList())
        );
        return dto;
    }

    public Member mapToMemberEntity(MemberDto dto) {
        Member entity = new Member();
        entity.setId(dto.getId());
        entity.setNationalIdNumber(dto.getNationalIdNumber());
        entity.setMembershipNumber(dto.getMembershipNumber());
        entity.setNames(dto.getNames());
        entity.setSurname(dto.getSurname());
        entity.setPosition(dto.getPosition());
        entity.setDistrict(dto.getDistrict());
        entity.setCard(dto.getIsCard() == 1);
        entity.setActive(dto.getIsActive() == 1);
        return entity;
    }

    public MemberDto mapToMemberDto(Member entity) {
        MemberDto dto = new MemberDto();
        dto.setId(entity.getId());
        dto.setNationalIdNumber(entity.getNationalIdNumber());
        dto.setMembershipNumber(entity.getMembershipNumber());
        dto.setNames(entity.getNames());
        dto.setSurname(entity.getSurname());
        dto.setPosition(entity.getPosition());
        dto.setDistrict(entity.getDistrict());
        dto.setPhoto(new PhotoDto());
        dto.setIsActive(entity.isActive()? 1 : 0);
        dto.setIsCard(entity.isCard()? 1 : 0);
        dto.setCreated(entity.getCreated());
        return dto;
    }
}
