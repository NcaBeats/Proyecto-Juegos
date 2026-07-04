package com.app.msjuego.unit.juego.controller;

import com.app.msjuego.juego.assembler.JuegoAssembler;
import com.app.msjuego.juego.controller.JuegoController;
import com.app.msjuego.juego.dto.JuegoResponse;
import com.app.msjuego.juego.service.JuegoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;

import static com.app.msjuego.support.JuegoFactory.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class JuegoControllerTest {

    private static final Pageable PAGEABLE = PageRequest.of(0,10);

    @Mock
    JuegoService juegoService;

    @Mock
    JuegoAssembler juegoAssembler;

    @Mock
    PagedResourcesAssembler<JuegoResponse> pagedResourcesAssembler;

    @InjectMocks
    JuegoController juegoController;

    @Test
    void findAll_ReturnsOk() {
        var page = Page.<JuegoResponse>empty();
        when(juegoService.findAll(PAGEABLE)).thenReturn(page);
        when(pagedResourcesAssembler.toModel(page, juegoAssembler)).thenReturn(PagedModel.empty());

        var result = juegoController.findAll(PAGEABLE);

        assertEquals(200, result.getStatusCode().value());
        assertNotNull(result.getBody());
    }

    @Test
    void findById_ReturnsOk() {
        when(juegoService.findById(ID)).thenReturn(JUEGO_RESPONSE);
        when(juegoAssembler.toModel(JUEGO_RESPONSE)).thenReturn(EntityModel.of(JUEGO_RESPONSE));

        var result = juegoController.findById(ID);

        assertEquals(200, result.getStatusCode().value());
        assertEquals(JUEGO_RESPONSE, result.getBody().getContent());
    }

    @Test
    void save_ReturnsCreated() {
        when(juegoService.save(JUEGO_REQUEST)).thenReturn(JUEGO_RESPONSE);
        when(juegoAssembler.toModel(JUEGO_RESPONSE)).thenReturn(EntityModel.of(JUEGO_RESPONSE));

        var result = juegoController.save(JUEGO_REQUEST);

        assertEquals(201, result.getStatusCode().value());
        assertEquals(JUEGO_RESPONSE, result.getBody().getContent());
    }

    @Test
    void getAllByEstudioId_ReturnsOk() {
        var page = Page.<JuegoResponse>empty();
        when(juegoService.getAllByEstudioId(ESTUDIO_ID, PAGEABLE)).thenReturn(page);
        when(pagedResourcesAssembler.toModel(page, juegoAssembler)).thenReturn(PagedModel.empty());

        var result = juegoController.getAllByEstudioId(ESTUDIO_ID, PAGEABLE);

        assertEquals(200, result.getStatusCode().value());
        assertNotNull(result.getBody());
    }

    @Test
    void getAllByGenerosId_ReturnsOk() {
        var page = Page.<JuegoResponse>empty();
        when(juegoService.getAllByGenerosId(GENERO_ID, PAGEABLE)).thenReturn(page);
        when(pagedResourcesAssembler.toModel(page, juegoAssembler)).thenReturn(PagedModel.empty());

        var result = juegoController.getAllByGenerosId(GENERO_ID, PAGEABLE);

        assertEquals(200, result.getStatusCode().value());
        assertNotNull(result.getBody());
    }

    @Test
    void getAllByPlataformasId_ReturnsOk() {
        var page = Page.<JuegoResponse>empty();
        when(juegoService.getAllByPlataformasId(PLATAFORMA_ID, PAGEABLE)).thenReturn(page);
        when(pagedResourcesAssembler.toModel(page, juegoAssembler)).thenReturn(PagedModel.empty());

        var result = juegoController.getAllByPlataformasId(PLATAFORMA_ID, PAGEABLE);

        assertEquals(200, result.getStatusCode().value());
        assertNotNull(result.getBody());
    }

    @Test
    void buscarPorNombre_ReturnsOk() {
        String nombre = JUEGO_REQUEST.nombre();
        when(juegoService.findByNombre(nombre)).thenReturn(JUEGO_RESPONSE);
        when(juegoAssembler.toModel(JUEGO_RESPONSE)).thenReturn(EntityModel.of(JUEGO_RESPONSE));

        var result = juegoController.buscarPorNombre(nombre);

        assertEquals(200, result.getStatusCode().value());
        assertEquals(JUEGO_RESPONSE, result.getBody().getContent());
    }

    @Test
    void update_ReturnsOk() {
        when(juegoService.update(ID, JUEGO_REQUEST)).thenReturn(JUEGO_RESPONSE);
        when(juegoAssembler.toModel(JUEGO_RESPONSE)).thenReturn(EntityModel.of(JUEGO_RESPONSE));

        var result = juegoController.update(ID, JUEGO_REQUEST);

        assertEquals(200, result.getStatusCode().value());
        assertEquals(JUEGO_RESPONSE, result.getBody().getContent());
    }

    @Test
    void delete_ReturnsNoContent() {
        doNothing().when(juegoService).delete(ID);

        var result = juegoController.delete(ID);

        assertEquals(204, result.getStatusCode().value());
        assertNull(result.getBody());
    }

}
