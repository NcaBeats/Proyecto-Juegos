package com.app.msprofile.controller;

import com.app.msprofile.dto.ProfileRequest;
import com.app.msprofile.dto.ProfileResponse;
import com.app.msprofile.service.ProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("api/v1/profiles")
public class ProfileController {
    private final ProfileService profileService;

    @GetMapping
    public ResponseEntity<Page<ProfileResponse>> findAll (Pageable pageable) {
        log.debug("GET /api/v1/profiles - página: {} tamaño: {}", pageable.getPageNumber(), pageable.getPageSize());
        return ResponseEntity.ok(profileService.findAll(pageable));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ProfileResponse> findById (@PathVariable long userId) {
        log.debug("GET /api/v1/profiles/{} - obteniendo perfil", userId);
        return ResponseEntity.ok(profileService.findById(userId));
    }

    @GetMapping("/nickname/{nickname}")
    public ResponseEntity<ProfileResponse> buscar(@PathVariable String nickname) {
        log.debug("GET /api/v1/profiles/nickname/{} - buscando perfil por nickname", nickname);
        return ResponseEntity.ok(profileService.findByNickname(nickname));
    }

    @PostMapping
    public ResponseEntity<ProfileResponse> save (@Valid @RequestBody ProfileRequest request) {
        log.info("POST /api/v1/profiles - creando perfil userId={}", request.userId());
        return ResponseEntity.status(HttpStatus.CREATED).body(profileService.save(request));
    }

    @PutMapping
    public ResponseEntity<ProfileResponse> update (@Valid @RequestBody ProfileRequest request) {
        log.info("PUT /api/v1/profiles - actualizando perfil userId={}", request.userId());
        return ResponseEntity.ok(profileService.update(request));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> delete (@PathVariable Long userId){
        log.info("DELETE /api/v1/profiles/{} - eliminando perfil", userId);
        profileService.delete(userId);
        return ResponseEntity.noContent().build();
    }
}
