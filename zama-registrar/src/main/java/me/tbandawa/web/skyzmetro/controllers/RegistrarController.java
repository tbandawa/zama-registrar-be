package me.tbandawa.web.skyzmetro.controllers;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import me.tbandawa.web.skyzmetro.dtos.*;
import me.tbandawa.web.skyzmetro.services.RegistrarService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/zama/api/v1/registrar", produces = "application/json")
public class RegistrarController {

    private final RegistrarService registrarService;

    public RegistrarController(RegistrarService registrarService) {
        this.registrarService = registrarService;
    }

    @PreAuthorize("isAuthenticated()")
    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = "application/json")
    public ResponseEntity<MemberDto> createMember(
            Authentication authentication,
            @Valid MemberDto memberDto,
            @RequestPart(value = "member_photo", required = false) MultipartFile member_photo
    ) {
        memberDto = registrarService.saveMember(authentication, memberDto, member_photo);
        URI podcastUri = ServletUriComponentsBuilder
                .fromUriString("/")
                .path(memberDto.getId().toString())
                .buildAndExpand("")
                .toUri();

        return ResponseEntity.created(podcastUri).body(memberDto);
    }

    @PreAuthorize("isAuthenticated()")
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping(produces = "application/json")
    public ResponseEntity<PagedMembersDto> getMembers(
            Authentication authentication,
            @RequestParam("page") int page
    ) {
        return ResponseEntity.ok().body(registrarService.getMembers(authentication, page));
    }

    @PreAuthorize("isAuthenticated()")
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<MemberDto> getMember(
            Authentication authentication,
            @PathVariable(value = "id") Long id
    ) {
        return ResponseEntity.ok().body(registrarService.getMember(authentication, id));
    }

    @PreAuthorize("isAuthenticated()")
    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping(value = "/activate/{id}", produces = "application/json")
    public ResponseEntity<MemberDto> activateMember(
            Authentication authentication,
            @PathVariable(value = "id") Long id,
            @RequestParam("isActive") boolean isActive
    ) {
        return ResponseEntity.ok().body(registrarService.activateMember(authentication, id, isActive));
    }

    @GetMapping(value = "/search", produces = "application/json")
    public ResponseEntity<List<MemberDto>> searchMembers(
            Authentication authentication,
            @RequestParam("query") String query
    ) {
        return ResponseEntity.ok().body(registrarService.searchMembers(authentication, query));
    }

    @PreAuthorize("isAuthenticated()")
    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping(value = "/print", produces = "application/json")
    public ResponseEntity<ExportDto> printMembers(@RequestParam List<Long> members) {
        return ResponseEntity.ok().body(registrarService.printMembers(members));
    }
}
