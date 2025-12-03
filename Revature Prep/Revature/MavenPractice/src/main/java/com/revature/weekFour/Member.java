package com.revature.weekFour;

import java.sql.Date;

public class Member {
    private int id;
    private String email;
    private String password;
    private String full_name;
    private int experience_months;
    private Date registration_date;

    public Member() {}

    /**
     * Creates a New Member
     * @param email Member Email
     * @param password Member Password
     * @param full_name Member Full Name
     * @param experience_months Member Experience in Months
     * @param registration_date Member Registration Date
     */
    public Member(String email, String password, String full_name, int experience_months, Date registration_date) {
        this.email = email;
        this.password = password;
        this.full_name = full_name;
        this.experience_months = experience_months;
        this.registration_date = registration_date;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return full_name;
    }

    public void setFullName(String full_name) {
        this.full_name = full_name;
    }

    public int getExperienceMonths() {
        return experience_months;
    }

    public void setExperienceMonths(int experience_months) {
        this.experience_months = experience_months;
    }

    public Date getRegistrationDate() {
        return registration_date;
    }

    public void setRegistrationDate(Date registration_date) {
        this.registration_date = registration_date;
    }
}
