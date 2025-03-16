package me.tbandawa.web.skyzmetro.dtos;

import lombok.Data;

import java.util.List;

@Data
public class PagedMembersDto {
    private int count;
    private int perPage;
    private int currentPage;
    private int nextPage;
    private List<MemberDto> results;
}
