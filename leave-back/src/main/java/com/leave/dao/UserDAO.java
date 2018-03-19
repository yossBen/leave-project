package com.leave.dao;

import com.leave.entity.User;

public interface UserDAO extends PersistentDAO<Long, User> {
    User findByEmail(String email);
}