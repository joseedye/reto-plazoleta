package com.pragma.powerup.infrastructure.input.rest;


import com.pragma.powerup.application.dto.request.PedidoPaginadoRequestDto;
import com.pragma.powerup.application.dto.request.PedidoRequestDto;
import com.pragma.powerup.application.dto.response.GenericoPaginadoResponseDto;
import com.pragma.powerup.application.dto.response.PedidoResponseDto;
import com.pragma.powerup.application.handler.IPedidoHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/pedido")
@RequiredArgsConstructor

public class PedidoRestController {


    private final  IPedidoHandler pedidoHandler;

    @PostMapping()
    public ResponseEntity<PedidoResponseDto> savePedido(@RequestBody @Valid PedidoRequestDto pedidoRequestDto) {
       PedidoResponseDto pedidoResponseDto = pedidoHandler.savePedido(pedidoRequestDto);
        return ResponseEntity.ok(pedidoResponseDto);
    }

    @GetMapping()
    @PreAuthorize("hasRole('EMPLEADO')")
    public ResponseEntity<GenericoPaginadoResponseDto<PedidoResponseDto>> listarPedidos(@RequestBody @Valid PedidoPaginadoRequestDto pedidoPaginadoRequestDto) {
       return ResponseEntity.ok(pedidoHandler.listarPedidos(pedidoPaginadoRequestDto));
    }

    @PatchMapping("/{pedidoId}/asignar")
    @PreAuthorize("hasRole('EMPLEADO')")
    public ResponseEntity<PedidoResponseDto> asignarPedido(@PathVariable  Long pedidoId) {
        return ResponseEntity.ok(pedidoHandler.asignarPedido(pedidoId));
    }

    @PatchMapping("/{id}/listo")
    @PreAuthorize("hasRole('EMPLEADO')")
    public ResponseEntity<PedidoResponseDto> marcarPedidoListo(@PathVariable Long id) {
       return ResponseEntity.ok(pedidoHandler.marcarPedidoComoListo(id));
    }

    @PatchMapping("/{id}/entregar")
    @PreAuthorize("hasRole('EMPLEADO')")
    public ResponseEntity<PedidoResponseDto> entregarPedido(@PathVariable Long id) {
        return ResponseEntity.ok(pedidoHandler.marcarPedidoComoListo(id));
    }

}
