package com.app.mswishlist.wishlist.controller;

import com.app.mswishlist.wishlist.assembler.WishlistAssembler;
import com.app.mswishlist.wishlist.dto.WishListResponse;
import com.app.mswishlist.wishlistgame.dto.WishlistGameRequest;
import com.app.mswishlist.wishlistgame.dto.WishlistGameResponse;
import com.app.mswishlist.wishlistgame.service.WishlistGameService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/wishlists")
@Tag(
        name = "Wishlist",
        description = "Gestión de listas de deseos de la plataforma Nico's Games"
)
public class WishlistController {

    private final WishlistGameService wishlistGameService;
    private final WishlistAssembler wishlistAssembler;

    @Operation(summary = "Obtener la wishlist de un usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Wishlist obtenida correctamente"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @GetMapping("/{userId}")
    public ResponseEntity<EntityModel<WishListResponse>> getAllByUserId(
            @Parameter(description = "ID del usuario")
            @PathVariable Long userId) {

        log.debug("GET /api/v1/wishlists/{} - obteniendo wishlist", userId);
        return ResponseEntity.ok(wishlistAssembler.toModel(wishlistGameService.getAllByUserId(userId)));
    }

    @Operation(summary = "Agregar un juego a la wishlist")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Juego agregado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "404", description = "Usuario o juego no encontrado")
    })
    @PostMapping("/{userId}")
    public ResponseEntity<WishlistGameResponse> addGame(
            @Parameter(description = "ID del usuario")
            @PathVariable Long userId,
            @RequestBody @Valid WishlistGameRequest wishlistGameRequest) {

        log.info("POST /api/v1/wishlists/{} - añadiendo juego gameId={}",
                userId,
                wishlistGameRequest.gameId());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(wishlistGameService.addGame(userId, wishlistGameRequest));
    }

    @Operation(summary = "Eliminar un juego de la wishlist")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Juego eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Juego o usuario no encontrado")
    })
    @DeleteMapping("/{userId}/{gameId}")
    public ResponseEntity<Void> deleteGame(
            @Parameter(description = "ID del usuario")
            @PathVariable Long userId,

            @Parameter(description = "ID del juego")
            @PathVariable Long gameId) {

        log.info("DELETE /api/v1/wishlists/{}/{} - eliminando juego", userId, gameId);

        wishlistGameService.deleteGame(userId, gameId);

        return ResponseEntity.noContent().build();
    }
}
