package com.revature.weekFour;

import java.util.List;

public interface MemberDAO {
    public Member createMember(Member newMember);
    public List<Member> findAllMembers();
    public Member findMemberByEmail(String email);
    public boolean updateMember(Member updatedMember);
    public boolean deleteMemberByEmail(String email);
}
