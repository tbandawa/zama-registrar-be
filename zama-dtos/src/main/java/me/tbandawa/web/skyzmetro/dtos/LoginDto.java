package me.tbandawa.web.skyzmetro.dtos;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginDto {
	
	@NotBlank(message = "Email can not be empty")
	@Email(message = "Invalid email")
	private String email;

	@NotBlank(message = "Password can not be empty")
	@Size(min = 6, max = 40, message = "Password should be between 6 and 40 characters long")
	private String password;
}
