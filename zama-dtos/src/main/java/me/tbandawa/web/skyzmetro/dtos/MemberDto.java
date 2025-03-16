package me.tbandawa.web.skyzmetro.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
public class MemberDto {

    private Long id;

    @NotBlank(message = "National Identity can not be empty")
    @Length(max = 250, message = "National Identity can not be longer than 250 characters")
    private String nationalIdNumber;

    private String membershipNumber;

    @NotBlank(message = "Names can not be empty")
    @Length(max = 250, message = "Names can not be longer than 250 characters")
    private String names;

    @NotBlank(message = "Surname can not be empty")
    @Length(max = 150, message = "Surname can not be longer than 150 characters")
    private String surname;

    @NotBlank(message = "Position can not be empty")
    @Length(max = 350, message = "Position can not be longer than 350 characters")
    private String position;

    @NotBlank(message = "District can not be empty")
    @Length(max = 150, message = "District can not be longer than 150 characters")
    private String district;

    private UserDto addedBy;

    private PhotoDto photo;

    private int isCard;

    private int isActive;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private Date created;
}
