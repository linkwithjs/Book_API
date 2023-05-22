package com.rj.security.auth;

import com.rj.security.dto.LoginDTO;
import com.rj.security.dto.RegisterDTO;
import com.rj.security.dto.TokenDTO;
import com.rj.security.exception.EmailAlreadyExistsException;
import com.rj.security.jwt.JwtService;
import com.rj.security.token.Token;
import com.rj.security.token.TokenRepository;
import com.rj.security.token.TokenType;
import com.rj.security.user.Role;
import com.rj.security.user.RoleRepository;
import com.rj.security.user.User;
import com.rj.security.user.UserRepository;
import lombok.RequiredArgsConstructor;

import org.hibernate.annotations.SQLUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    @Autowired
    UserRepository repository;

    @Autowired
    RoleRepository roleRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public TokenDTO register(RegisterDTO request) throws EmailAlreadyExistsException {

        var emailEntry = repository.findByEmail(request.getEmail());

        if (emailEntry.isPresent()){
            throw new EmailAlreadyExistsException( "Error: Email is already in use!");
        }

        Set<String> strRoles = request.getRole();
        Set<Role> roles = new HashSet<>();
        strRoles.forEach(role -> {
            String roleName = role.trim().toUpperCase();
            Role verifiedRole = roleRepository.findByName(roleName)
                    .orElseThrow(() -> new RuntimeException("Error: " + roleName + " is not found."));
            roles.add(verifiedRole);
        });


        var newUser = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles(roles)
                .build();

        var savedUser = repository.save(newUser);
        var jwtToken = jwtService.generateToken(newUser);
        saveUserToken(savedUser, jwtToken);
        return TokenDTO.builder()
                .token(jwtToken)
                .build();
    }

    public TokenDTO authenticate(LoginDTO request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()));

        var user = repository.findByEmail(request.getEmail())
                .orElseThrow(()-> new UsernameNotFoundException("User not found"));

        var jwtToken = jwtService.generateToken((UserDetails) user);

        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);
        return TokenDTO.builder()
                .token(jwtToken)
                .build();
    }

    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }
}
