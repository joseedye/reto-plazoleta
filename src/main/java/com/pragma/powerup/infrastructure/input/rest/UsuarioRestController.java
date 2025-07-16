package com.pragma.powerup.infrastructure.input.rest;


import com.pragma.powerup.application.dto.request.ClienteRequestDto;
import com.pragma.powerup.application.dto.request.EmpleadoRequestDto;
import com.pragma.powerup.application.dto.request.UsuarioRequestDto;
import com.pragma.powerup.application.dto.response.ClienteResponseDto;
import com.pragma.powerup.application.dto.response.EmpleadoResponseDto;
import com.pragma.powerup.application.dto.response.UsuarioResponseDto;
import com.pragma.powerup.application.handler.IUsuarioHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/usuario")
@RequiredArgsConstructor

public class UsuarioRestController {

    private final IUsuarioHandler usuarioHandler;

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @PostMapping("/")
    public ResponseEntity<UsuarioResponseDto> saveUsuario(@RequestBody @Valid UsuarioRequestDto usuarioRequestDto) {
        return ResponseEntity.ok(usuarioHandler.saveUsuario(usuarioRequestDto));
    }

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @PostMapping("/propietario/")
    public ResponseEntity<UsuarioResponseDto> savePropietario(@RequestBody @Valid UsuarioRequestDto usuarioRequestDto) {
        return ResponseEntity.ok(usuarioHandler.savePropietario(usuarioRequestDto));
    }

    @PreAuthorize("hasRole('PROPIETARIO')")
    @PostMapping("/empleado")
    public ResponseEntity<EmpleadoResponseDto> saveEmpleado(@RequestBody @Valid EmpleadoRequestDto empleadoRequestDto) {
        return ResponseEntity.ok(usuarioHandler.saveEmpleado(empleadoRequestDto));
    }


    @PostMapping("/cliente")
    public ResponseEntity<ClienteResponseDto> saveCliente(@RequestBody @Valid ClienteRequestDto clienteRequestDto) {
        return ResponseEntity.ok(usuarioHandler.saveCliente(clienteRequestDto));
    }

    @GetMapping("/{number}")
    public ResponseEntity<UsuarioResponseDto> getUsuario(@PathVariable (name = "number") int usuarionumero){
        return  ResponseEntity.ok(usuarioHandler.getAllUsuarios().get(usuarionumero));
    }

    @GetMapping("/")
    public ResponseEntity<List<UsuarioResponseDto>> getAllObjects() {
        return ResponseEntity.ok(usuarioHandler.getAllUsuarios());
    }






}
