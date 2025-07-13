package com.cognizant.springlearn.controller;

import com.cognizant.springlearn.jwt.JwtRequest;
import com.cognizant.springlearn.jwt.JwtResponse;
import com.cognizant.springlearn.jwt.JwtUserDetailsService;
import com.cognizant.springlearn.jwt.JwtTokenUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;

@RestController
@CrossOrigin
public class JwtAuthenticationController {

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtAuthenticationController.class);

    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final JwtUserDetailsService userDetailsService;

    public JwtAuthenticationController(AuthenticationConfiguration authenticationConfiguration,
                                       JwtTokenUtil jwtTokenUtil,
                                       JwtUserDetailsService userDetailsService) throws Exception {
        this.authenticationManager = authenticationConfiguration.getAuthenticationManager();
        this.jwtTokenUtil = jwtTokenUtil;
        this.userDetailsService = userDetailsService;
    }

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
        LOGGER.info("Attempting authentication for user: {}", authenticationRequest.getUsername());
        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authenticationRequest.getUsername());

        final String token = jwtTokenUtil.generateToken(userDetails);

        LOGGER.info("Authentication successful. Token generated for user: {}", authenticationRequest.getUsername());
        return ResponseEntity.ok(new JwtResponse(token));
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            LOGGER.info("Credentials verified for user: {}", username);
        } catch (DisabledException e) {
            LOGGER.error("User {} is disabled.", username);
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            LOGGER.error("Invalid credentials for user: {}", username);
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}