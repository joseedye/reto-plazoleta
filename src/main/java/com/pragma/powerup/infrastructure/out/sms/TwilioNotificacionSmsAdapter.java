package com.pragma.powerup.infrastructure.out.sms;

import com.pragma.powerup.application.dto.request.SmsEnviarRequestDto;
import com.pragma.powerup.domain.spi.INotificacionSmsPort;


import org.springframework.web.client.RestTemplate;

import static com.pragma.powerup.infrastructure.util.ExcepcionMessageConstants.URL_SERVICIO_SMS;


public class TwilioNotificacionSmsAdapter implements INotificacionSmsPort {


    private final RestTemplate restTemplate;
    private final String smsServiceUrl = URL_SERVICIO_SMS ;


    public TwilioNotificacionSmsAdapter(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public void enviarSms(String numeroDestino, String mensaje) {
        SmsEnviarRequestDto request = new SmsEnviarRequestDto(numeroDestino,mensaje);
        restTemplate.postForEntity(smsServiceUrl, request, Void.class);
    }
}
