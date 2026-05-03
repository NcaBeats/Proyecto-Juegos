package com.app.msprofile.controller;

import com.app.msprofile.dto.ProfileRequest;
import com.app.msprofile.dto.ProfileResponse;
import com.app.msprofile.service.ProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/profiles")
public class ProfileController {
    private final ProfileService profileService;

    @GetMapping
    public ResponseEntity<Page<ProfileResponse>> findAll (Pageable pageable) {
        return ResponseEntity.ok(profileService.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfileResponse> findById (@PathVariable long id) {
        return ResponseEntity.ok(profileService.findById(id));
    }

    @GetMapping("/buscar")
    public ResponseEntity<Page<ProfileResponse>> buscar(
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) String nickname,
            Pageable pageable
    )
    {
        return ResponseEntity.ok(profileService.findByFiltros(userId,nickname,pageable));
    }

    @PostMapping
    public ResponseEntity<ProfileResponse> save (@Valid @RequestBody ProfileRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(profileService.save(request));
    }

    @PutMapping
    public ResponseEntity<ProfileResponse> update (@Valid @RequestBody ProfileRequest request) {
        return ResponseEntity.ok(profileService.update(request));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> delete (@PathVariable Long userId){
        profileService.delete(userId);
        return ResponseEntity.noContent().build();
    }
}
