package org.example.userauthenticationservice.controllers;

import org.example.userauthenticationservice.dtos.*;
import org.example.userauthenticationservice.services.IAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private IAuthService authService;


    @PostMapping("/sign_up")
    public ResponseEntity<SignUpResponseDto> signUp(@RequestBody SignUpRequestDto requestDto){
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
            return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto requestDto){
        LoginResponseDto responseDto = new LoginResponseDto();
        try{
                String token = authService.login(requestDto.getEmail(), requestDto.getPassword());
                responseDto.setToken(token);
                responseDto.setResponseStatus(ResponseStatus.SUCCESS);
                return new ResponseEntity<>(responseDto, HttpStatus.OK);
        }catch (Exception ex){
            responseDto.setResponseStatus(ResponseStatus.FAILURE);
            return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
        }
    }
}
