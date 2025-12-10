package com.revature.weekEight;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Optional;

public class PhoneBookTest {
    private PhoneBook pb = new PhoneBook();

    @Test
    public void findPhoneNumberByName() {
        Optional<String> phoneNumber = pb.findPhoneNumberByName("Jos de Vos");

        Assertions.assertEquals("016/161616", phoneNumber.get());
    }
}
