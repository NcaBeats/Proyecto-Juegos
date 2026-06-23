package com.app.msnotification.controller;

import com.app.msnotification.dto.NotificationRequest;
import com.app.msnotification.dto.NotificationResponse;
import com.app.msnotification.dto.PurchaseNotificationRequest;
import com.app.msnotification.service.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@RequestMapping("/api/v1/notifications")
@Tag(
        name = "Notificaciones",
        description = "Gestión de notificaciones de la plataforma Nico's Games"
)
public class NotificationController {

    private final NotificationService notificationService;

    @Operation(summary = "Obtener todas las notificaciones de un usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Notificaciones obtenidas correctamente"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @GetMapping("/{userId}")
    public ResponseEntity<Page<NotificationResponse>> getAllByUserId(
            @Parameter(description = "ID del usuario")
            @PathVariable Long userId,
            Pageable pageable) {

        log.debug("GET /api/v1/notifications/{} - página: {} tamaño: {}",
                userId,
                pageable.getPageNumber(),
                pageable.getPageSize());

        return ResponseEntity.ok(
                notificationService.getAllByUserId(userId, pageable)
        );
    }

    @Operation(summary = "Crear una notificación manual")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Notificación creada correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PostMapping
    public ResponseEntity<NotificationResponse> save(
            @Valid @RequestBody NotificationRequest notificationRequest) {

        log.info("POST /api/v1/notifications - creando notificación para userId={}",
                notificationRequest.userId());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(notificationService.save(notificationRequest));
    }

    @Operation(summary = "Crear notificaciones automáticas por compra")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Notificaciones de compra creadas correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PostMapping("/purchase")
    public ResponseEntity<Void> savePurchaseNotification(
            @Valid @RequestBody PurchaseNotificationRequest request) {

        log.info("POST /api/v1/notifications/purchase - userId={} juegosCount={}",
                request.userId(),
                request.juegos().size());

        notificationService.savePurchaseNotification(request);

        log.info("Notificaciones de compra creadas para userId={}",
                request.userId());

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
