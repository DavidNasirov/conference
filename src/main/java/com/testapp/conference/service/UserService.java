package com.testapp.conference.service;

import com.testapp.conference.model.User;

public interface UserService {
    String login(String email, String password);

    String signUp(User user);
}
