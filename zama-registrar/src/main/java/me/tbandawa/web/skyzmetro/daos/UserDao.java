package me.tbandawa.web.skyzmetro.daos;

import java.util.List;
import java.util.Optional;

import me.tbandawa.web.skyzmetro.entities.User;

public interface UserDao {
	Long addUser(User user);
	int editUser(User user);
	int activateUser(long id, int isActive);
	Optional<User> getUser(Long id);
	List<User> getUsers(Long id);
	Optional<User> findByEmail(String email);
	int deleteUser(long id);
}
