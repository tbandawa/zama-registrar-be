package me.tbandawa.web.skyzmetro.entities;

import lombok.Data;

import java.util.List;

@Data
public class PagedMembers {
    private int count;
    private int perPage;
    private int currentPage;
    private int nextPage;
    private List<Member> results;
}
