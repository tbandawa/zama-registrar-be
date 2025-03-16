package me.tbandawa.web.skyzmetro.services;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import me.tbandawa.web.skyzmetro.entities.Role;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import me.tbandawa.web.skyzmetro.dtos.UserDto;
import me.tbandawa.web.skyzmetro.entities.User;

@Component
public class UserMapper {

	public List<UserDto> mapToDtoList(List<User> entity) {
		return Optional.ofNullable(entity)
				.orElseGet(Collections::emptyList)
				.stream()
				.map(this::mapToDto)
				.collect(Collectors.toList());
	}

	public UserDto mapToDto(User entity) {
		UserDto dto = new UserDto();
		dto.setUserId(entity.getId());
		dto.setFirstname(StringUtils.capitalize(entity.getFirstname()));
		dto.setLastname(StringUtils.capitalize(entity.getLastname()));
		dto.setEmail(entity.getEmail());
		dto.setDistrict(entity.getDistrict());
		dto.setRole(entity.getRoles().stream().map(Role::getName).collect(Collectors.toList()));
		dto.setIsActive(entity.getIsActive());
		return dto;
	}
}
