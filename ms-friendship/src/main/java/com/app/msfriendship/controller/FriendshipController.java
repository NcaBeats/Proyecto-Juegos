package com.app.msfriendship.controller;

import com.app.msfriendship.dto.FriendshipRequest;
import com.app.msfriendship.dto.FriendshipResponse;
import com.app.msfriendship.service.FriendshipService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/friendships")
@Tag(
        name = "Amistades",
        description = "Gestión de solicitudes y relaciones de amistad entre usuarios"
)
public class FriendshipController {

    private final FriendshipService friendshipService;

    @Operation(summary = "Enviar una solicitud de amistad")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Solicitud enviada correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @PostMapping("/request")
    public ResponseEntity<FriendshipResponse> sendRequest(

            @Parameter(description = "ID del usuario que envía la solicitud")
            @RequestParam Long userId,

            @Valid @RequestBody FriendshipRequest request) {

        log.info("POST /api/v1/friendships/request - userId={} friendId={}",
                userId,
                request.friendId());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(friendshipService.sendRequest(userId, request));
    }

    @Operation(summary = "Aceptar una solicitud de amistad")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Solicitud aceptada correctamente"),
            @ApiResponse(responseCode = "404", description = "Solicitud no encontrada")
    })
    @PutMapping("/{id}/accept")
    public ResponseEntity<FriendshipResponse> acceptRequest(

            @Parameter(description = "ID de la solicitud de amistad")
            @PathVariable Long id,

            @Parameter(description = "ID del usuario que acepta la solicitud")
            @RequestParam Long userId) {

        log.info("PUT /api/v1/friendships/{}/accept - userId={} friendshipId={}",
                id,
                userId,
                id);

        return ResponseEntity.ok(
                friendshipService.acceptRequest(userId, id)
        );
    }

    @Operation(summary = "Rechazar una solicitud de amistad")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Solicitud rechazada correctamente"),
            @ApiResponse(responseCode = "404", description = "Solicitud no encontrada")
    })
    @PutMapping("/{id}/reject")
    public ResponseEntity<FriendshipResponse> rejectRequest(

            @Parameter(description = "ID de la solicitud de amistad")
            @PathVariable Long id,

            @Parameter(description = "ID del usuario que rechaza la solicitud")
            @RequestParam Long userId) {

        log.info("PUT /api/v1/friendships/{}/reject - userId={} friendshipId={}",
                id,
                userId,
                id);

        return ResponseEntity.ok(
                friendshipService.rejectRequest(userId, id)
        );
    }

    @Operation(summary = "Obtener lista de amigos de un usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente")
    })
    @GetMapping("/{userId}/friends")
    public ResponseEntity<List<FriendshipResponse>> getFriends(

            @Parameter(description = "ID del usuario")
            @PathVariable Long userId) {

        log.debug("GET /api/v1/friendships/{}/friends - userId={}",
                userId,
                userId);

        return ResponseEntity.ok(
                friendshipService.getFriends(userId)
        );
    }

    @Operation(summary = "Obtener solicitudes pendientes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Solicitudes obtenidas correctamente")
    })
    @GetMapping("/{userId}/pending")
    public ResponseEntity<List<FriendshipResponse>> getPendingRequests(

            @Parameter(description = "ID del usuario")
            @PathVariable Long userId) {

        log.debug("GET /api/v1/friendships/{}/pending - userId={}",
                userId,
                userId);

        return ResponseEntity.ok(
                friendshipService.getPendingRequests(userId)
        );
    }

    @Operation(summary = "Eliminar una amistad")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Amistad eliminada correctamente"),
            @ApiResponse(responseCode = "404", description = "Amistad no encontrada")
    })
    @DeleteMapping("/{friendShipId}/delete")
    public ResponseEntity<Void> deleteFriendship(

            @Parameter(description = "ID de la amistad")
            @PathVariable Long friendShipId,

            @Parameter(description = "ID del usuario")
            @RequestParam Long userId) {

        log.info("DELETE /api/v1/friendships/{}/delete - userId={} friendshipId={}",
                friendShipId,
                userId,
                friendShipId);

        friendshipService.deleteFriendship(userId, friendShipId);

        return ResponseEntity.noContent().build();
    }
}
