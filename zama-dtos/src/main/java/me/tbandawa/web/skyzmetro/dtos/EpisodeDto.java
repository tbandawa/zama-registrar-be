package me.tbandawa.web.skyzmetro.dtos;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class EpisodeDto {
	
    private Long id;
    
    @NotNull(message= "Podcast Id is required")
	private Long podcast_id;
    
    private int episode;
    
    @NotBlank(message = "Episode Title can not be empty")
    @Length(max = 150, message = "Episode title can not be longer than 150 characters")
	private String title;
    
	private String audio_url;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
	private Date created;
}
