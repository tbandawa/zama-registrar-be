package me.tbandawa.web.skyzmetro.dtos;

import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class RegisterDto {
	
	private Long userId;

	@NotBlank(message = "First name can not be empty")
	@Size(max = 50, message = "First name can not be longer than 50 characters")
	private String firstname;
	  
	@NotBlank(message = "Last name can not be empty")
	@Size(max = 50, message = "Last name can not be longer than 50 characters")
	private String lastname;

	@NotBlank(message = "Email can not be empty")
	@Email(message = "Invalid email")
	private String email;
	  
	@NotBlank(message = "Password can not be empty")
	@Size(min = 6, max = 40, message = "Password should be between 6 and 40 characters long")
	private String password;

	@NotBlank(message = "District can not be empty")
	private String district;

	private Set<String> role;
	
	private int isActive;
}
