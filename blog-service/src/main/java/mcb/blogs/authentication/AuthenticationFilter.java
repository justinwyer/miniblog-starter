package mcb.blogs.authentication;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
@Configuration
public class AuthenticationFilter extends GenericFilterBean {

    static final String SECRET_KEY = "some-secret";

    @Override
    protected void initFilterBean() throws ServletException {
        super.initFilterBean();
    }

    @Override
    public void doFilter(ServletRequest r, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        var blogUser = getBlogUserBeanFromSpringWebContext();

        HttpServletRequest request = (HttpServletRequest) r;
        String authorization = request.getHeader("Authorization");
        if (StringUtils.isEmpty(authorization)) {
            blogUser.setUsername(null);
            chain.doFilter(r, response);
        } else {
            String token = authorization.split("Bearer ")[1];

            Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
            JWTVerifier jwtVerifier = JWT.require(algorithm)
                    .withIssuer("auth0")
                    .build();
            DecodedJWT decodedJWT = jwtVerifier.verify(token);

            blogUser.setUsername(decodedJWT.getSubject());

            chain.doFilter(r, response);
        }
    }

    private BlogUser getBlogUserBeanFromSpringWebContext() {
        return WebApplicationContextUtils
                .getRequiredWebApplicationContext(getFilterConfig().getServletContext())
                .getAutowireCapableBeanFactory().getBean(BlogUser.class);
    }
}
