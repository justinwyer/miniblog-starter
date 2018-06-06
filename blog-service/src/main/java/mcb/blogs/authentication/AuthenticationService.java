package mcb.blogs.authentication;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Date;

@Controller
@RequestMapping("/authenticate")
@Scope("request")
public class AuthenticationService {

    @PostMapping
    public ResponseEntity authenticate(@RequestBody AuthenticationRequest request) throws Exception {
        return ResponseEntity.ok(generateJWT(request.getUsername()));
    }

    private String generateJWT(String username) throws Exception {

            Algorithm algorithm = Algorithm.HMAC256(AuthenticationFilter.SECRET_KEY);
            String token = JWT.create()
                    .withIssuer("auth0")
                    .withSubject(username)
                    .withExpiresAt(new Date(new Date().getTime() + 3600 * 1000))
                    .withClaim("username", username)
                    .sign(algorithm);
            return token;
    }

    @RequestMapping(value = "/validate", method = RequestMethod.POST)
    public ResponseEntity validate(Authentication authentication) throws Exception {
        JwtAuthenticationToken token = (JwtAuthenticationToken)authentication;
        return ResponseEntity.ok(token.getDetails());

    }
}
