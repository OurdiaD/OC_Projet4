package com.lamzone.mareu.service.user;

import com.lamzone.mareu.model.User;
import com.lamzone.mareu.service.ApiServiceGenerator;

import java.util.ArrayList;
import java.util.List;

public class MainUserApiService implements UserApiService {
    private final List<User> users = ApiServiceGenerator.generateUsers();

    @Override
    public void createUser(User user) {
        users.add(user);
    }

    @Override
    public void deleteUser(User user) {
        users.remove(user);
    }
    
    @Override
    public List<User> getUsers() {
        return users;
    }

    @Override
    public List<User> getUsersSelected(String selected){
        String[] selectedList = selected.split(", ");
        List<User> selectedUser = new ArrayList<>();
        for (String mail : selectedList){
            boolean userFound = false;
            for (User user : users){
                if (mail.equals(user.getMail())){
                    selectedUser.add(user);
                    userFound = true;
                    break;
                }
            }
            if (!userFound){
                User newUser = new User(mail);
                createUser(newUser);
                selectedUser.add(newUser);
            }
        }
        return selectedUser;
    }
}
