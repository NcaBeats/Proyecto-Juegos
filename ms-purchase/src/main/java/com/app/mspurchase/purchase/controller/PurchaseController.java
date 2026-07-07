package com.app.mspurchase.purchase.controller;

import com.app.mspurchase.purchase.assembler.PurchaseAssembler;
import com.app.mspurchase.purchase.dto.PurchaseRequest;
import com.app.mspurchase.purchase.dto.PurchaseResponse;
import com.app.mspurchase.purchase.service.PurchaseService;
import com.app.mspurchase.purchasegame.dto.PurchaseGameStatsResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/purchases")
@RequiredArgsConstructor
@Slf4j
@Tag(
        name = "Compras",
        description = "Gestión de compras"
)
public class PurchaseController {

    private final PurchaseService purchaseService;
    private final PurchaseAssembler purchaseAssembler;
    private final PagedResourcesAssembler<PurchaseResponse> pagedResourcesAssembler;

    @Operation(summary = "Obtener compras de un usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Compras obtenidas correctamente"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<PagedModel<EntityModel<PurchaseResponse>>> findAllByUserId(
            @Parameter(description = "ID del usuario")
            @PathVariable Long id,
            @ParameterObject Pageable pageable) {

        log.debug("GET /api/v1/purchases/{} - página: {} tamaño: {}", id,
                pageable.getPageNumber(), pageable.getPageSize());

        return ResponseEntity.ok(
                pagedResourcesAssembler.toModel(purchaseService.findAllByUserId(id, pageable), purchaseAssembler)
        );
    }

    @Operation(summary = "Obtener todas las compras")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado obtenido correctamente")
    })
    @GetMapping
    public ResponseEntity<PagedModel<EntityModel<PurchaseResponse>>> findAll(@ParameterObject Pageable pageable) {

        log.debug("GET /api/v1/purchases - página: {} tamaño: {}",
                pageable.getPageNumber(),
                pageable.getPageSize());

        return ResponseEntity.ok(
                pagedResourcesAssembler.toModel(purchaseService.findAll(pageable), purchaseAssembler)
        );
    }

    @Operation(summary = "Crear una nueva compra")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Compra creada correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PostMapping
    public ResponseEntity<EntityModel<PurchaseResponse>> createPurchase(
            @Valid @RequestBody PurchaseRequest request) {

        log.info("POST /api/v1/purchases - creando compra userId={} juegosCount={}",
                request.userId(),
                request.juegos().size());

        PurchaseResponse response = purchaseService.createPurchase(request);

        log.info("Compra creada para userId={}", request.userId());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(purchaseAssembler.toModel(response));
    }

    @Operation(summary = "Obtener estadísticas de compras de un juego")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estadísticas obtenidas correctamente"),
            @ApiResponse(responseCode = "404", description = "Juego no encontrado")
    })
    @GetMapping("/game/{gameId}/stats")
    public ResponseEntity<List<PurchaseGameStatsResponse>> getGameStats(
            @Parameter(description = "ID del juego")
            @PathVariable Long gameId) {

        log.debug("GET /api/v1/purchases/game/{}/stats - obteniendo stats de juego", gameId);

        return ResponseEntity.ok(
                purchaseService.findAllByGameIdForStats(gameId)
        );
    }

    @Operation(summary = "Obtener estadísticas de compras de un usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estadísticas obtenidas correctamente"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @GetMapping("/user/{userId}/stats")
    public ResponseEntity<List<PurchaseGameStatsResponse>> getUserStats(
            @Parameter(description = "ID del usuario")
            @PathVariable Long userId) {

        log.debug("GET /api/v1/purchases/user/{}/stats - obteniendo stats de usuario", userId);

        return ResponseEntity.ok(
                purchaseService.findAllByUserIdForStats(userId)
        );
    }
}
