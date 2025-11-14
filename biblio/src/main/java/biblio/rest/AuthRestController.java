package biblio.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import biblio.config.JwtUtils;
import biblio.dto.request.AuthUserRequest;
import biblio.dto.response.AuthResponse;

@RestController
@RequestMapping("/api")
public class AuthRestController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/auth")
    public AuthResponse auth(@RequestBody AuthUserRequest request) {
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword());

        // On demande à Spring Security si le user / password sont OK
        this.authenticationManager.authenticate(auth);

        return new AuthResponse(JwtUtils.generate(auth));
    }
}

