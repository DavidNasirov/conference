package com.testapp.conference;

import com.testapp.conference.core.user.model.User;
import com.testapp.conference.core.user.model.UserRole;
import com.testapp.conference.core.user.port.input.UserUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.Collections;

import org.springframework.boot.CommandLineRunner;


@SpringBootApplication
public class ConferenceApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(ConferenceApplication.class, args);
    }

    @Autowired
    private UserUseCase userUseCase;

    //dump admin
    @Override
    public void run(String... params) throws Exception {
        User admin = new User();
        admin.setUserId(1L);
        admin.setFirstName("admin");
        admin.setLastName("admin");
        admin.setPassword("admin1111");
        admin.setEmail("admin@gmail.com");
        admin.setUserRoles(new ArrayList<>(Collections.singletonList(UserRole.ADMIN_ROLE)));

        userUseCase.signUp(admin);
    }
}