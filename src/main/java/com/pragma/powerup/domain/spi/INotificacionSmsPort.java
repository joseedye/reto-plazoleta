package com.pragma.powerup.domain.spi;

public interface INotificacionSmsPort {

    void enviarSms(String numeroDestino, String mensaje);

}
