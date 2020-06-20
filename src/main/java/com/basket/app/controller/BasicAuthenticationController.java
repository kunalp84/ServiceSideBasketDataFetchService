package com.basket.app.controller;


import com.basket.app.pojo.AuthenticationRequest;
import com.basket.app.pojo.AuthenticationResponse;
import com.basket.app.pojo.BasketUser;
import com.basket.app.util.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

//@CrossOrigin(origins = {"http://localhost:3000","https://moonlit-academy-276018.uc.r.appspot.com/"})
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class BasicAuthenticationController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BasicAuthenticationController.class);

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtil jwtTokenUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @GetMapping(path = "/basicauth")
    public AuthenticationBean authenticate() {
        //throw new RuntimeException("Some Error has Happened! Contact Support at ***-***");

        LOGGER.info("Inside authenticate() method");
        return new AuthenticationBean("OK");
    }

    @GetMapping(path = "/")
    public String homeMethod() {
        //throw new RuntimeException("Some Error has Happened! Contact Support at ***-***");

        LOGGER.info("Inside authenticate() method");
        return "Ok";
    }



    @PostMapping(path = "/loginCheckService")
    public AuthenticationBean authenticate(@RequestBody BasketUser user) {
        //throw new RuntimeException("Some Error has Happened! Contact Support at ***-***");



        return new AuthenticationBean("OK");
    }


    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public AuthenticationBean createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        LOGGER.info("Inside createAuthenticationToken() method");

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
            );
        }
        catch (BadCredentialsException e) {
            throw new Exception("Incorrect username or password", e);
        }


        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authenticationRequest.getUsername());

        final String jwt = jwtTokenUtil.generateToken(userDetails);

        //return ResponseEntity.ok(new AuthenticationBean("OK",jwt));
        LOGGER.info("Back from  createAuthenticationToken() method");

        return new AuthenticationBean("OK",jwt);
    }


}
