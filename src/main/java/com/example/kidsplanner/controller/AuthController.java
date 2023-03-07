package com.example.kidsplanner.controller;

import com.example.kidsplanner.DTO.AuthDTO;
import com.example.kidsplanner.DTO.RegisterDTO;
import com.example.kidsplanner.model.InvalidEmailException;
import com.example.kidsplanner.model.User;
import com.example.kidsplanner.service.JwtTokenService;
import com.example.kidsplanner.service.UserDetailsServiceImpl;
import com.example.kidsplanner.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@Api( tags = "Autentificarea utilizatorilor")
public class AuthController {

    @Autowired
    private JwtTokenService jwtTokenService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private UserService userService;

    @PostMapping("/authenticate")
    @ApiOperation(value = "Introdu userul si parola pentru a obtine un token.")
    public String authenticate(@RequestBody AuthDTO user) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());
        return jwtTokenService.generateToken(userDetails);
    }

    @PostMapping("/register")
    public User register(@Valid @RequestBody RegisterDTO newUser)throws InvalidEmailException {
        return userService.register(newUser);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
