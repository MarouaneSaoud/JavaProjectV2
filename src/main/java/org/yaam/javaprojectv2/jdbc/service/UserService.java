package org.yaam.javaprojectv2.jdbc.service;

import org.yaam.javaprojectv2.jdbc.dao.impl.UserDaoImpl;
import org.yaam.javaprojectv2.jdbc.entities.User;

public class UserService {
    UserDaoImpl userDao = new UserDaoImpl();

    public boolean auth(User user){
        return userDao.auth(user);
    }
}
