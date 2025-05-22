package org.example.userauthenticationservice.controllers;

import org.example.userauthenticationservice.dtos.*;
import org.example.userauthenticationservice.services.IAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private IAuthService authService;

    @PostMapping("/sign_up")
    public ResponseEntity<SignUpResponseDto> signUp(SignUpRequestDto requestDto){
        SignUpResponseDto responseDto = new SignUpResponseDto();
        try{
            if(authService.signup(requestDto.getEmail(), requestDto.getPassword())){
                responseDto.setResponseStatus(ResponseStatus.SUCCESS);
            }
            else{
                responseDto.setResponseStatus(ResponseStatus.FAILURE);
            }
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        }catch (Exception ex){
            responseDto.setResponseStatus(ResponseStatus.FAILURE);
            return new ResponseEntity<>(responseDto, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(LoginRequestDto requestDto){
        LoginResponseDto responseDto = new LoginResponseDto();
        try{
            if(authService.login(requestDto.getEmail(), requestDto.getPassword())){
                responseDto.setResponseStatus(ResponseStatus.SUCCESS);
            }
            else{
                responseDto.setResponseStatus(ResponseStatus.FAILURE);
            }
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        }catch (Exception ex){
            responseDto.setResponseStatus(ResponseStatus.FAILURE);
            return new ResponseEntity<>(responseDto, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
