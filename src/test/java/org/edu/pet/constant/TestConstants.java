package org.edu.pet.constant;

import lombok.experimental.UtilityClass;
import org.edu.pet.model.User;

@UtilityClass
public class TestConstants {

    public User defaultUser() {

        return User.builder()
                .id(1L)
                .login("test@example.com")
                .password("Hgj*32-(32")
                .build();
    }
}