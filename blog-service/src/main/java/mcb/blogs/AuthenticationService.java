package mcb.blogs;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@Controller
@RequestMapping("/authenticate")
@Scope("request")
public class AuthenticationService {
    private BlogUser user;

    @Autowired
    public AuthenticationService(BlogUser user) {
        this.user = user;
    }

    @PostMapping
    public ResponseEntity authenticate(@RequestBody AuthenticationRequest request) throws Exception {
        // generate JWT

        return ResponseEntity.ok(generateJWT(request.getUsername()));
    }

    public String generateJWT(String username) throws Exception {

            Algorithm algorithm = Algorithm.HMAC256(AuthenticationFilter.SECRET_KEY);
            String token = JWT.create()
                    .withIssuer("auth0")
                    .withSubject(username)
                    .withExpiresAt(new Date(2342342342344L))
                    .withClaim("username", username)
                    .sign(algorithm);
            return token;
    }

    @RequestMapping(value = "/validate", method = RequestMethod.POST)
    public ResponseEntity validate(@RequestBody ValidateTokenRequest request) throws Exception {

        String token = request.getToken();
        Algorithm algorithm = Algorithm.HMAC256(AuthenticationFilter.SECRET_KEY);
        JWTVerifier jwtVerifier = JWT.require(algorithm)
                .withIssuer("auth0")
                .build();
        DecodedJWT decodedJWT = jwtVerifier.verify(token);
        return ResponseEntity.ok(decodedJWT);

    }
}
