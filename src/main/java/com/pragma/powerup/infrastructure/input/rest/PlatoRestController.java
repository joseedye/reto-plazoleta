package com.pragma.powerup.infrastructure.input.rest;


import com.pragma.powerup.application.dto.request.PlatoActualizaRequestDto;
import com.pragma.powerup.application.dto.request.PlatoPaginadoRequestDto;
import com.pragma.powerup.application.dto.request.PlatoRequestDto;
import com.pragma.powerup.application.dto.response.GenericoPaginadoResponseDto;
import com.pragma.powerup.application.dto.response.PlatoResponseDto;
import com.pragma.powerup.application.dto.response.RestauranteResponseDto;
import com.pragma.powerup.application.handler.IPlatoHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/plato")
@RequiredArgsConstructor
public class PlatoRestController {

    private final IPlatoHandler platoHandler;

    @PreAuthorize("hasRole('PROPIETARIO')")
    @PostMapping("/")
    public ResponseEntity<PlatoResponseDto> savePlato(@RequestBody @Valid PlatoRequestDto platoRequestDto) {
        return ResponseEntity.ok(platoHandler.savePlato(platoRequestDto));
    }

    @PreAuthorize("hasRole('PROPIETARIO')")
    @PatchMapping("/")
    public ResponseEntity<PlatoResponseDto> actualizarPlato(@RequestBody @Valid PlatoActualizaRequestDto platoRequestDto){
        return ResponseEntity.ok(platoHandler.updateDescripcionYPrecio(platoRequestDto));
    }

//    @GetMapping("/")
//    public ResponseEntity<List<PlatoResponseDto>> getAllPlatos() {
//        return ResponseEntity.ok(platoHandler.getAllPlatos());
//    }

    @GetMapping()
    public ResponseEntity<GenericoPaginadoResponseDto<PlatoResponseDto>> listarPlatos(@RequestBody @Valid PlatoPaginadoRequestDto platoPaginadoRequestDto ) {
        return ResponseEntity.ok(platoHandler.listarPlatos(platoPaginadoRequestDto));
    }


    @PreAuthorize("hasRole('PROPIETARIO')")
    @PatchMapping("/{id}/cambiar_estado")
    public ResponseEntity<PlatoResponseDto> cambiarEstado(@PathVariable Long id){
        return ResponseEntity.ok(platoHandler.cambiarEstado(id));
    }

//    @PreAuthorize("hasRole('PROPIETARIO')")
//    @PatchMapping("/{id}/habilitar")
//    public ResponseEntity<PlatoResponseDto> habilitarPlato(@PathVariable Long id){
//        return ResponseEntity.ok(platoHandler.habilitarPlato(id));
//    }

//    @PreAuthorize("hasRole('PROPIETARIO')")
//    @PatchMapping("/{id}/deshabilitar")
//    public ResponseEntity<PlatoResponseDto> deshabilitarPlato(@PathVariable Long id){
//        return ResponseEntity.ok(platoHandler.deshabilitarPlato(id));
//    }



}
