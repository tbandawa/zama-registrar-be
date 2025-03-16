package me.tbandawa.web.skyzmetro.daos;

import me.tbandawa.web.skyzmetro.entities.*;

import java.util.List;
import java.util.Optional;

public interface MemberDao {
    PagedMembers getMembers(int page);
    Member saveMember(Member member);
    Optional<Member> getMember(long id);
    List<Member> searchMembers(String query);
    int editMember(Member member);
    int activateMember(long id, boolean active);
    int printMember(long id, boolean card);
}
