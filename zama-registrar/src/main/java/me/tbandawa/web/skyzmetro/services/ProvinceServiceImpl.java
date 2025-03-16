package me.tbandawa.web.skyzmetro.services;

import me.tbandawa.web.skyzmetro.daos.ProvinceDao;
import me.tbandawa.web.skyzmetro.dtos.ProvinceDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProvinceServiceImpl implements ProvinceService {

    private final ProvinceDao provinceDao;

    private final ProvinceMapper provinceMapper;

    public ProvinceServiceImpl(ProvinceDao provinceDao, ProvinceMapper provinceMapper) {
        this.provinceDao = provinceDao;
        this.provinceMapper = provinceMapper;
    }

    @Override
    public List<ProvinceDto> getProvinces() {
        return provinceDao.getProvinces().stream()
                .map(provinceMapper::mapToProvinceDto)
                .collect(Collectors.toList());
    }
}
