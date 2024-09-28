package com.example.userService.controllers;

import com.example.userService.dtos.*;
import com.example.userService.exceptions.UserAlreadyExistsException;
import com.example.userService.services.AuthService;
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
    private AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/sign_up")
    public ResponseEntity<SignUpResponseDto> signUp(@RequestBody SignUpRequestDto request) throws UserAlreadyExistsException {
        SignUpResponseDto response = new SignUpResponseDto();
        try {

            if (authService.signUp(request.getEmail(), request.getPassword())) {
                response.setRequestStatus(RequestStatus.SUCCESS);
            } else {
                response.setRequestStatus(RequestStatus.FAILURE);
            }
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e){
            response.setRequestStatus(RequestStatus.FAILURE);
            return  new ResponseEntity<>(response, HttpStatus.CONFLICT);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto request) {
        String token = authService.login(request.getEmail(), request.getPassword());
        LoginResponseDto loginDto = new LoginResponseDto();
        loginDto.setRequestStatus(RequestStatus.SUCCESS);
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("AUTH_TOKEN", token);
        ResponseEntity<LoginResponseDto> response = new ResponseEntity<>(
                loginDto, headers, HttpStatus.OK);
        return response;
    }
}
