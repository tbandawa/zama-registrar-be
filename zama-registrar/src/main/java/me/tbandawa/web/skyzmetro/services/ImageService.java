package me.tbandawa.web.skyzmetro.services;

import org.springframework.web.multipart.MultipartFile;

import me.tbandawa.web.skyzmetro.dtos.PhotoDto;
import me.tbandawa.web.skyzmetro.utils.ResourceType;

public interface ImageService {
	PhotoDto saveImage(Long id, MultipartFile image, ResourceType resourceType);
	PhotoDto getImage(Long id, ResourceType resourceType);
	void deleteImage(Long id, ResourceType resourceType);
}
