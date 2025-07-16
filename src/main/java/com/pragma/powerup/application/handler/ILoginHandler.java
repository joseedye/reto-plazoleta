package com.pragma.powerup.application.handler;

import com.pragma.powerup.application.dto.request.LoginRequestDto;
import com.pragma.powerup.application.dto.response.LoginResponseDto;

public interface ILoginHandler {

    public LoginResponseDto login (LoginRequestDto loginRequestDto);

}
