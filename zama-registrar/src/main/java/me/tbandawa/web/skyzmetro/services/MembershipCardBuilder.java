package me.tbandawa.web.skyzmetro.services;

import me.tbandawa.web.skyzmetro.dtos.ExportDto;
import me.tbandawa.web.skyzmetro.dtos.MemberDto;

import java.util.List;

public interface MembershipCardBuilder {
    ExportDto buildCards(List<MemberDto> memberDtoList);
}
