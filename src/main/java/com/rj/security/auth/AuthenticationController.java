package com.rj.security.auth;

import com.rj.security.dto.LoginDTO;
import com.rj.security.dto.RegisterDTO;
import com.rj.security.dto.TokenDTO;
import com.rj.security.exception.EmailAlreadyExistsException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// import com.rj.security.payload.MessageResponse;
// import com.rj.security.user.UserRepository;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;

    @PostMapping("/register")
    public ResponseEntity<TokenDTO> register(
            @RequestBody RegisterDTO request) throws EmailAlreadyExistsException {
        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<TokenDTO> authenticate(
            @RequestBody LoginDTO request) {
        return ResponseEntity.ok(service.authenticate(request));
    }

}
