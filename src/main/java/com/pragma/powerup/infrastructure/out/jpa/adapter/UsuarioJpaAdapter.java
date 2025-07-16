package com.pragma.powerup.infrastructure.out.jpa.adapter;

import com.pragma.powerup.domain.model.Usuario;
import com.pragma.powerup.domain.spi.IUsuarioPersistencePort;
import com.pragma.powerup.infrastructure.exception.NoDataFoundException;
import com.pragma.powerup.infrastructure.exception.UsuarioAlreadyExistsException;
import com.pragma.powerup.infrastructure.exception.UsuarioNotFoundException;
import com.pragma.powerup.infrastructure.out.jpa.entity.UsuarioEntity;
import com.pragma.powerup.infrastructure.out.jpa.mapper.IUsuarioEntityMapper;
import com.pragma.powerup.infrastructure.out.jpa.repository.IUsuarioRepository;
import lombok.RequiredArgsConstructor;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class UsuarioJpaAdapter implements IUsuarioPersistencePort {

    private final IUsuarioRepository usuarioRepository;
    private final IUsuarioEntityMapper usuarioEntityMapper;

    @Override
    public Usuario saveUsuario(Usuario usuario) {
       return usuarioEntityMapper.toUsuario(usuarioRepository.save(usuarioEntityMapper.toEntity(usuario)));
    }

    @Override
    public List<Usuario> getAllUsuarios() {
        List<UsuarioEntity> usuarioEntityList = usuarioRepository.findAll();
        if(usuarioEntityList.isEmpty()){
            throw new NoDataFoundException();
        }
        return usuarioEntityMapper.toUsuarioList(usuarioEntityList);
    }

    @Override
    public Usuario getUsuario(String documentoIdentidad) {
        return usuarioEntityMapper.toUsuario(usuarioRepository.findBydocumentoIdentidad(documentoIdentidad)
                .orElseThrow(UsuarioNotFoundException::new));
    }

    @Override
    public Usuario getUsuario(Long id) {
        return usuarioEntityMapper.toUsuario(usuarioRepository.findByid(id)
                .orElseThrow(UsuarioNotFoundException::new));
    }


    @Override
    public Usuario findByCorreo(String correo) {

        Optional<UsuarioEntity> usuarioEntity =  usuarioRepository.findFirstByCorreo(correo);

        return usuarioEntityMapper.toUsuario(usuarioEntity
                .orElseThrow(() -> new UsuarioNotFoundException("Usuario no encontrado")));
    }

    @Override
    public boolean existsByCorreo(String correo){
        return usuarioRepository.existsByCorreo(correo);
    }

    @Override
    public boolean existsByDocumento(String documento) {
        return usuarioRepository.existsBydocumentoIdentidad(documento);
    }


    @Override
    public void updateUsuario(Usuario usuario) {
        UsuarioEntity usuarioEntity =  usuarioEntityMapper.toEntity(usuario);
        usuarioRepository.save(usuarioEntity);

    }

    @Override
    public void deleteUsuario(String id) {
        usuarioRepository.deleteBydocumentoIdentidad(id);
    }


}
