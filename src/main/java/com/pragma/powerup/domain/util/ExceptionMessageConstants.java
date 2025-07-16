package com.pragma.powerup.domain.util;

public class ExceptionMessageConstants {

    public static final String CORREO_YA_REGISTRADO = "Ya existe este correo registrado";
    public static final String DOCUMENTO_YA_REGISTRADO = "Ya existe este documento registrado";
    public static final String ROL_NO_EXISTE = "El rol con ese ID no existe";
    public static final String USUARIO_NO_AUTENTICADO = "No se pudo obtener el ID del usuario autenticado";
    public static final String USUARIO_NO_ENCONTRADO = "Usuario no encontrado";
    public static final String USUARIO_NO_MAYOR_EDAD = "El usuario debe ser mayor de edad";
    public static final String USUARIO_SIN_PERMISOS = "El usuario no cuenta con los permisos suficientes";
    public static final String ROL_NO_ADMIN = "El usuario no es un Administrador";
    public static final String ROL_NO_PROPIETARIO = "El usuario no es un Propietario";
    public static final String NO_PROPIETARIO_RESTAURANTE = "No eres propietario de este restaurante ";
    public static final String MODIFICA_PLATO_NO_PERMITIDA = "No puedes modificar platos de otro restaurante.";
    public static final String PLATO_NO_EXISTE = "Plato no existe ";
    public static final String PEDIDO_EN_PROGRESO = "Ya tienes un pedido en proceso.";
    public static final String MIN_CANT_PLATOS =    "Debes hacer el pedido con al menos un plato.";
    public static final String PLATOS_DIFERENTE_RESTAURANTE = "Todos los platos deben ser del mismo restaurante.";

    private ExceptionMessageConstants() {

    }

}
