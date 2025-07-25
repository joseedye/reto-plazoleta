package com.pragma.powerup.infrastructure.input.rest;


import com.pragma.powerup.application.dto.request.RestaurantePaginadoRequestDto;
import com.pragma.powerup.application.dto.request.RestauranteRequestDto;
import com.pragma.powerup.application.dto.response.GenericoPaginadoResponseDto;
import com.pragma.powerup.application.dto.response.RestauranteResponseDto;
import com.pragma.powerup.application.handler.IRestauranteHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/restaurante")
@RequiredArgsConstructor

public class RestauranteRestController {


    private final IRestauranteHandler restauranteHandler;

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @PostMapping("/")
    public ResponseEntity<RestauranteResponseDto> saveRestaurante(@RequestBody @Valid RestauranteRequestDto restauranteRequestDto) {
        return ResponseEntity.ok(restauranteHandler.saveRestaurante(restauranteRequestDto));
    }

//REQ 9
    @GetMapping()
    public ResponseEntity<GenericoPaginadoResponseDto<RestauranteResponseDto>> listarRestaurantes(RestaurantePaginadoRequestDto restaurantePaginadoRequestDto) {
        return ResponseEntity.ok(restauranteHandler.listarRestaurantes(restaurantePaginadoRequestDto));
    }


}
