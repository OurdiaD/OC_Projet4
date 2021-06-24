package com.lamzone.mareu;

import com.lamzone.mareu.di.DI;
import com.lamzone.mareu.model.User;
import com.lamzone.mareu.service.ApiServiceGenerator;
import com.lamzone.mareu.service.user.UserApiService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class UserServiceTest {
    
    private UserApiService service;
    private static int sizeBaseList;
    private List<User> baseList;

    @Before
    public void setup() {
        service = DI.getNewInstanceUserApiService();
        baseList = ApiServiceGenerator.DUMMY_USER;
        sizeBaseList = baseList.size();
    }

    @Test
    public void getAllUserTest() {
        List<User> users = service.getUsers();
        assertTrue(users.size() == sizeBaseList && users.containsAll(baseList));
    }

    @Test
    public void deleteUserTest() {
        User userToDelete = service.getUsers().get(0);
        service.deleteUser(userToDelete);
        assertFalse(service.getUsers().contains(userToDelete));
    }
    
    @Test
    public void createUserTest() {
        User user = new User("romain@lamzone.com");
        service.createUser(user);
        List<User> users = service.getUsers();
        assertTrue(sizeBaseList + 1 == users.size() && users.contains(user));
    }

    @Test
    public void getUserSelectedTest(){
        String userSelected = service.getUsers().get(0).toString();
        String newUser = "laurent@lamzone.com";
        String stringUsersSelected = userSelected + ", " + newUser;
        List<User> listUsersSelected = service.getUsersSelected(stringUsersSelected);

        assertEquals(2, listUsersSelected.size());
        assertSame(service.getUsers().get(0), listUsersSelected.get(0));
        assertEquals(sizeBaseList + 1, service.getUsers().size());
        assertTrue(service.getUsers().contains(listUsersSelected.get(1)));
    }

    @Test
    public void selectedUsersExisting(){
        StringBuilder usersSelected = new StringBuilder();
        for (int i = 0; i<3; i++){
            usersSelected.append(baseList.get(i).toString());
            if (i<2)
                usersSelected.append(", ");
        }
        List<User> listUsersSelected = service.getUsersSelected(usersSelected.toString());
        assertEquals(sizeBaseList, service.getUsers().size());
        assertEquals(3, listUsersSelected.size());
        assertSame(service.getUsers().get(0), listUsersSelected.get(0));
    }

    @Test
    public void selectedNewUsers() {
        String newUsers = "rolland@lamzone.fr, manon@lamzone.fr";
        List<User> selectedUser = service.getUsersSelected(newUsers);

        assertEquals(2, selectedUser.size());
        assertEquals( sizeBaseList +2, service.getUsers().size());
        assertTrue(service.getUsers().containsAll(selectedUser));
    }
}
