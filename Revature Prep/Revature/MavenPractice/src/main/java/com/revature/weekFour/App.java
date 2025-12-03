package com.revature.weekFour;

import java.sql.*;
import java.time.LocalDate;
import java.util.List;

public class App {
    public static void main(String[] args) {
        System.out.println("=== Test SQL App ===");

        MemberDAOImpl memberDAO = new MemberDAOImpl();

        Member drew = new Member("drew@example.com", "password", "Drew", 12, Date.valueOf(LocalDate.now()));
        Member notDrew = new Member("notdrew@example.com", "password", "NotDrew", 6, Date.valueOf(LocalDate.now()));

        memberDAO.createMember(drew);
        memberDAO.createMember(notDrew);

        List<Member> members = memberDAO.findAllMembers();
        for (Member member : members) {
            System.out.println("Member Name: " + member.getFullName());
            System.out.println("Member Email: " + member.getEmail());
            System.out.println("Member Experience: " + member.getExperienceMonths());
        }

        Member foundByEmailMember = memberDAO.findMemberByEmail(notDrew.getEmail());
        System.out.println("Member Name: " + foundByEmailMember.getFullName());
        System.out.println("Member Email: " + foundByEmailMember.getEmail());
        System.out.println("Member Experience: " + foundByEmailMember.getExperienceMonths());
    }
}
