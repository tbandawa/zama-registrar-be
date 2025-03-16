package me.tbandawa.web.skyzmetro.daos;

import java.util.Optional;

import me.tbandawa.web.skyzmetro.entities.Role;

public interface RoleDao {
	Optional<Role> findByName(String name);
}
