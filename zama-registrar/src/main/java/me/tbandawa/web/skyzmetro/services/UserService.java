package me.tbandawa.web.skyzmetro.services;

import java.util.List;

import org.springframework.security.core.Authentication;

import me.tbandawa.web.skyzmetro.dtos.LoginDto;
import me.tbandawa.web.skyzmetro.dtos.RegisterDto;
import me.tbandawa.web.skyzmetro.dtos.UserDto;

public interface UserService {
	UserDto addUser(RegisterDto request);
	UserDto signInUser(LoginDto request);
	UserDto editUserProfile(RegisterDto request);
	UserDto activateUser(long id, int isActive);
	UserDto getUserProfile(Long id);
	List<UserDto> getUsers(Long id);
	int deleteUser(long id);
    String logoutUser(String token);
}
