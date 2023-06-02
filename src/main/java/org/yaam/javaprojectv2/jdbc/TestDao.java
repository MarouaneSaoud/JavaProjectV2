package org.yaam.javaprojectv2.jdbc;

import org.yaam.javaprojectv2.jdbc.entities.User;
import org.yaam.javaprojectv2.jdbc.service.UserService;

public class TestDao {

    public static void main(String[] args) {
        UserService userService =  new UserService();
        User user = new User("marouane","1234");
        System.out.println(userService.auth(user));
    }

}
