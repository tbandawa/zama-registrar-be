package me.tbandawa.web.skyzmetro.dtos;

import java.util.List;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
	
	private String token;
	
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

	@NotBlank(message = "District can not be empty")
	private String district;

	private List<String> role;
	
	private int isActive;
}
