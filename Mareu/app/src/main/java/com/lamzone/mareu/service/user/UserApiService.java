package com.lamzone.mareu.service.user;

import com.lamzone.mareu.model.User;

import java.util.List;

public interface UserApiService {
    void createUser(User user);

    void deleteUser(User user);

    List<User> getUsers();

    List<User> getUsersSelected(String selected);
}
