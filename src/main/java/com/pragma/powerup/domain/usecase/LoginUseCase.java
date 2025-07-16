package com.pragma.powerup.domain.usecase;

import com.pragma.powerup.domain.api.IEncriptarContrasenaServicePort;
import com.pragma.powerup.domain.api.ILoginServicePort;
import com.pragma.powerup.domain.exception.LoginFailException;
import com.pragma.powerup.domain.model.Usuario;
import com.pragma.powerup.domain.spi.IUsuarioPersistencePort;

public class LoginUseCase implements ILoginServicePort {


   private final IUsuarioPersistencePort usuarioPersistencePort;
   private final IEncriptarContrasenaServicePort encriptarContrasenaServicePort;

    public LoginUseCase(IUsuarioPersistencePort usuarioPersistencePort, IEncriptarContrasenaServicePort encriptarContrasenaServicePort) {
        this.usuarioPersistencePort = usuarioPersistencePort;
        this.encriptarContrasenaServicePort = encriptarContrasenaServicePort;
    }

    @Override
    public Usuario login(String correo,String  contrasena) {

        Usuario usuario = usuarioPersistencePort.findByCorreo(correo);

        if(usuario.getIntentos()==3){
            throw new LoginFailException("Intentos permitidos superados");
        }

        if(!encriptarContrasenaServicePort.compara(contrasena,usuario.getClave())){
            usuario.setIntentos(usuario.getIntentos()+1);
            usuarioPersistencePort.updateUsuario(usuario);
            throw new LoginFailException("Contraseña incorrecta");
        }

        usuario.setIntentos(0);
        usuarioPersistencePort.updateUsuario(usuario);

        return usuario;

    }


}
