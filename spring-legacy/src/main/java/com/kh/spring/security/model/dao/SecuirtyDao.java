package com.kh.spring.security.model.dao;

import org.springframework.security.core.userdetails.UserDetails;

public interface SecuirtyDao {

	UserDetails loadUserByUsername(String username);

}
