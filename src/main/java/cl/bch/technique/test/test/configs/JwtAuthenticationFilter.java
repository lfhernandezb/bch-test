package cl.bch.technique.test.test.configs;

import cl.bch.technique.test.test.service.*;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

/*
authentication middleware

For every request, we want to retrieve the JWT token in the header “Authorization”, and validate it:

    If the token is invalid, reject the request if the token is invalid or continues otherwise.
    If the token is valid, extract the username, find the related user in the database, and set it in the authentication context so you can access it in any application layer.
 */

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private HandlerExceptionResolver handlerExceptionResolver;

    @Autowired
    private JwtService jwtService;


    @Autowired
    private CustomUserDetailsService customUserDetailsService;


    /*
    @Autowired
    private UserDetailsService userDetailsService;

     */

    private final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException, ExpiredJwtException, UnsupportedJwtException, MalformedJwtException,
            SignatureException, IllegalArgumentException {

        logger.info("doFilterInternal invoked");

        final String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {

            logger.info("doFilterInternal before try-catch block");

            final String jwt = authHeader.substring(7);

            logger.info("doFilterInternal jwt: " + jwt);

            final String email = jwtService.extractUsername(jwt);

            logger.info("doFilterInternal userEmail: " + email);

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            //logger.info("doFilterInternal authentication: " + authentication.toString());

            if (email != null && authentication == null) {
                UserDetails userDetails = this.customUserDetailsService.loadUserByUsername(email);

                logger.info("doFilterInternal userDetails: " + userDetails.toString());

                if (jwtService.isTokenValid(jwt, userDetails)) {

                    logger.info("doFilterInternal token is valid");
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );

                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }

        }
        filterChain.doFilter(request, response);


    }
}