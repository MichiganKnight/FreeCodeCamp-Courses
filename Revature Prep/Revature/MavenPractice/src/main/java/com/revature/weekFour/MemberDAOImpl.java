package com.revature.weekFour;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MemberDAOImpl implements MemberDAO {
    /**
     * Create a New Member
     * @param newMember Member to Create
     * @return A New Member Object
     */
    @Override
    public Member createMember(Member newMember) {
        try (Connection connection = ConnectionFactory.getConnectionFactory().getConnection()) {
            String sql = "INSERT INTO members (email, password, full_name, experience_months, registration_date) VALUES (?,?,?,?,?)";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, newMember.getEmail());
            preparedStatement.setString(2, newMember.getPassword());
            preparedStatement.setString(3, newMember.getFullName());
            preparedStatement.setInt(4, newMember.getExperienceMonths());
            preparedStatement.setDate(5, newMember.getRegistrationDate());

            int checkInsert = preparedStatement.executeUpdate();

            if (checkInsert == 0) {
                throw new RuntimeException("Member was Not Entered into the Database");
            }

            return newMember;
        } catch (SQLException ex) {
            System.err.println("SQL Exception: " + ex.getMessage());
        }

        return null;
    }

    @Override
    public List<Member> findAllMembers() {
        try (Connection connection = ConnectionFactory.getConnectionFactory().getConnection()) {
            List<Member> members = new ArrayList<>();

            String sql = "SELECT * FROM members";

            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                Member member = new Member();
                member.setEmail(resultSet.getString("email"));
                member.setPassword(resultSet.getString("password"));
                member.setFullName(resultSet.getString("full_name"));
                member.setExperienceMonths(resultSet.getInt("experience_months"));
                member.setRegistrationDate(resultSet.getDate("registration_date"));

                members.add(member);
            }

            return members;
        } catch (SQLException ex) {
            System.err.println("SQL Exception: " + ex.getMessage());
        }

        return null;
    }

    @Override
    public Member findMemberByEmail(String email) {
        try (Connection connection = ConnectionFactory.getConnectionFactory().getConnection()) {
            String sql = "SELECT * FROM members WHERE email = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, email);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (!resultSet.next()) {
                throw new RuntimeException("Invalid Information Entered");
            }

            Member member = new Member();
            member.setEmail(resultSet.getString("email"));
            member.setPassword(resultSet.getString("password"));
            member.setFullName(resultSet.getString("full_name"));
            member.setExperienceMonths(resultSet.getInt("experience_months"));
            member.setRegistrationDate(resultSet.getDate("registration_date"));

            return member;
        } catch (SQLException ex) {
            System.err.println("SQL Exception: " + ex.getMessage());
        }

        return null;
    }

    @Override
    public boolean updateMember(Member updatedMember) {
        try (Connection connection = ConnectionFactory.getConnectionFactory().getConnection()) {
            String sql = "UPDATE members SET password = ? , full_name = ? , experience_months = ? , registration_date = ? WHERE email = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, updatedMember.getPassword());
            preparedStatement.setString(2, updatedMember.getFullName());
            preparedStatement.setInt(3, updatedMember.getExperienceMonths());
            preparedStatement.setDate(4, updatedMember.getRegistrationDate());

            preparedStatement.setString(5, updatedMember.getEmail());

            int checkInsert = preparedStatement.executeUpdate();

            if (checkInsert == 0) {
                throw new RuntimeException("Member was Not Entered into the Database");
            }

            return true;
        } catch (SQLException ex) {
            System.err.println("SQL Exception: " + ex.getMessage());

            return false;
        }
    }

    @Override
    public boolean deleteMemberByEmail(String email) {
        try (Connection connection = ConnectionFactory.getConnectionFactory().getConnection()) {
            String sql = "DELETE FROM members WHERE email = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, email);

            int checkInsert = preparedStatement.executeUpdate();

            if (checkInsert == 0) {
                throw new RuntimeException("Member was Not Entered into the Database");
            }

            return true;
        } catch (SQLException ex) {
            System.err.println("SQL Exception: " + ex.getMessage());

            return false;
        }
    }
}
