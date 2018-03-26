package com.leave.dao.impl;

import com.leave.dao.UserDAO;
import com.leave.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDAOImpl extends PersistentDAOImpl<Long, User> implements UserDAO {
	public User findByEmail(String email) {
		String hql = "FROM User as user WHERE user.email = ?";
		List<User> users = (List<User>) hibernateTemplate.find(hql, email);
		return !users.isEmpty() ? users.get(0) : null;
	}
}