package org.yaam.javaprojectv2.jdbc.dao;

import org.yaam.javaprojectv2.jdbc.entities.User;

public interface UserDao {

    boolean auth(User user);
}
