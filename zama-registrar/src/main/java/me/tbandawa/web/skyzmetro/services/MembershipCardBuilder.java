package me.tbandawa.web.skyzmetro.services;

import me.tbandawa.web.skyzmetro.dtos.MemberDto;

import java.util.List;

public interface MembershipCardBuilder {
    List<MemberDto> buildCards(List<MemberDto> memberDtoList);
}
