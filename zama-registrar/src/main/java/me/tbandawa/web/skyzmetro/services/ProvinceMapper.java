package me.tbandawa.web.skyzmetro.services;

import me.tbandawa.web.skyzmetro.dtos.ProvinceDto;
import me.tbandawa.web.skyzmetro.entities.Province;
import org.springframework.stereotype.Component;

@Component
public class ProvinceMapper {

    public ProvinceDto mapToProvinceDto(Province entity) {
        ProvinceDto dto = new ProvinceDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setCode(entity.getCode());
        return dto;
    }
}
