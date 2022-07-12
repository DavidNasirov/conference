package com.testapp.conference.core.user.port.input;

import com.testapp.conference.core.user.model.User;

public interface UserUseCase {
    String login(String email, String password);

    String signUp(User user);
}
