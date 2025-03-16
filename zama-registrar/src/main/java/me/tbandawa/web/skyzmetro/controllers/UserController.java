package me.tbandawa.web.skyzmetro.controllers;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import me.tbandawa.web.skyzmetro.dtos.LoginDto;
import me.tbandawa.web.skyzmetro.dtos.LogoutDto;
import me.tbandawa.web.skyzmetro.dtos.RegisterDto;
import me.tbandawa.web.skyzmetro.dtos.UserDto;
import me.tbandawa.web.skyzmetro.services.UserService;

@RestController
@RequestMapping(value = "/zama/api/v1", produces = "application/json")
public class UserController {
	
	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping(value = "/auth/signin", produces = "application/json")
	public ResponseEntity<UserDto> signInUser(@Valid @RequestBody LoginDto loginRequest) {
		UserDto userDto = userService.signInUser(loginRequest);
		URI profileUri = ServletUriComponentsBuilder
				.fromUriString("/user/profile")
				.path(userDto.getUserId().toString())
				.buildAndExpand("")
				.toUri();
		return ResponseEntity.created(profileUri).body(userDto);
	}

	@PreAuthorize("isAuthenticated()")
	@PostMapping(value = "/user", produces = "application/json")
	public ResponseEntity<UserDto> addUser(@Valid @RequestBody RegisterDto registerRequest) {
		UserDto userDto = userService.addUser(registerRequest);
		return ResponseEntity.ok(userDto);
	}

	@PostMapping(value = "/user/create", produces = "application/json")
	public ResponseEntity<UserDto> createUser(@Valid @RequestBody RegisterDto registerRequest) {
		UserDto userDto = userService.addUser(registerRequest);
		return ResponseEntity.ok(userDto);
	}
	
	@PreAuthorize("isAuthenticated()")
	@SecurityRequirement(name = "Bearer Authentication")
	@PutMapping(value = "/user", produces = "application/json")
    public ResponseEntity<UserDto> editProfile(
    	@Valid @RequestBody RegisterDto registerRequest
    ) {
		return ResponseEntity.accepted().body(userService.editUserProfile(registerRequest));
    }
	
	@PreAuthorize("isAuthenticated()")
	@SecurityRequirement(name = "Bearer Authentication")
	@PostMapping(value = "/user/{id}", produces = "application/json")
	public ResponseEntity<UserDto> activateUser(@PathVariable(value = "id") Long id, @RequestParam("isActive") int isActive) {
		return ResponseEntity.ok().body(userService.activateUser(id, isActive));
	}
	
	@PreAuthorize("isAuthenticated()")
	@SecurityRequirement(name = "Bearer Authentication")
	@GetMapping(value = "/user/{id}", produces = "application/json")
    public ResponseEntity<UserDto> getProfile(
    	@PathVariable Long id
    ) {
        return ResponseEntity.ok().body(userService.getUserProfile(id));
    }

	@PreAuthorize("isAuthenticated()")
	@SecurityRequirement(name = "Bearer Authentication")
	@DeleteMapping(value = "/user/{id}", produces = "application/json")
	public ResponseEntity<Integer> deleteUser(
			@PathVariable(value = "id") Long id
	) {
		return ResponseEntity.ok().body(userService.deleteUser(id));
	}
	
	@PreAuthorize("isAuthenticated()") 
	@SecurityRequirement(name = "Bearer Authentication")
	@GetMapping(value = "/logout", produces = "application/json")
    public ResponseEntity<LogoutDto> logoutUser(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
		LogoutDto logoutDto = new LogoutDto();
		logoutDto.setToken(userService.logoutUser(token));
		return ResponseEntity.ok().body(logoutDto);
    }
	
	@PreAuthorize("isAuthenticated()") 
	@SecurityRequirement(name = "Bearer Authentication")
	@GetMapping(value = "/users/{id}", produces = "application/json")
    public ResponseEntity<List<UserDto>> getUsers(@PathVariable Long id) {
		return ResponseEntity.ok().body(userService.getUsers(id));
    }
}
