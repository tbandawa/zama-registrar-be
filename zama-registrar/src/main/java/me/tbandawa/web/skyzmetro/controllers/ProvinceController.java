package me.tbandawa.web.skyzmetro.controllers;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import me.tbandawa.web.skyzmetro.dtos.ProvinceDto;
import me.tbandawa.web.skyzmetro.services.ProvinceService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/zama/api/v1/registrar/provinces", produces = "application/json")
public class ProvinceController {

    private final ProvinceService provinceService;

    public ProvinceController(ProvinceService provinceService) {
        this.provinceService = provinceService;
    }

    @PreAuthorize("isAuthenticated()")
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping(produces = "application/json")
    public ResponseEntity<List<ProvinceDto>> getProvinces() {
        return ResponseEntity.ok().body(provinceService.getProvinces());
    }
}
