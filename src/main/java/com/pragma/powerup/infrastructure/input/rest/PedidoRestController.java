package com.pragma.powerup.infrastructure.input.rest;


import com.pragma.powerup.application.dto.request.PedidoRequestDto;
import com.pragma.powerup.application.dto.response.PedidoResponseDto;
import com.pragma.powerup.application.handler.IPedidoHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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
    public ResponseEntity<List<PedidoResponseDto>> listarPedidos(@RequestParam String estado, @RequestParam int pagina, @RequestParam int tamanio) {
       return ResponseEntity.ok(pedidoHandler.listarPedidos( estado, pagina, tamanio));
    }

}
