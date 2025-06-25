package org.example.userauthenticationservice.controllers;

import org.example.userauthenticationservice.dtos.*;
import org.example.userauthenticationservice.models.User;
import org.example.userauthenticationservice.services.IAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.antlr.v4.runtime.misc.Pair;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
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
        if(requestDto.getEmail() == null || requestDto.getPassword() == null || requestDto.getRole() == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        try{
                User user = authService.signup(requestDto.getEmail(), requestDto.getPassword(), requestDto.getRole());
                responseDto.setResponseStatus(ResponseStatus.SUCCESS);
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
                Pair<Boolean,String> withToken = authService.login(requestDto.getEmail(), requestDto.getPassword());
                responseDto.setResponseStatus(ResponseStatus.SUCCESS);
                MultiValueMap<String,String> headers = new LinkedMultiValueMap<>();
                headers.add(HttpHeaders.COOKIE, withToken.b);
                return new ResponseEntity<>(responseDto, headers, HttpStatus.OK);
        }catch (Exception ex){
            responseDto.setResponseStatus(ResponseStatus.FAILURE);
            return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
        }
    }
}
