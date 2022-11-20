package com.episen.basket.security;

import com.episen.basket.setting.InfraSettings;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jwt.SignedJWT;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.interfaces.RSAPublicKey;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@Slf4j
public class Authorization {
    public static boolean checkAuthorization(Boolean isAdmin, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        Boolean authorizationChecked = false;
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            try {
                String token = authorizationHeader.substring("Bearer ".length());
                SignedJWT signedJWT;
                signedJWT = SignedJWT.parse(token);
                if (!isSignatureValid(signedJWT)) {
                    throw new RuntimeException("Token invalid");
                }
                List<String> roles;
                try {
                    roles = (List<String>) signedJWT.getJWTClaimsSet().getClaim("ROLES");
                } catch (Exception e) {
                    throw new RuntimeException("Cannot cast the list");
                }
                if (isAdmin) {
                    authorizationChecked = roles.stream().anyMatch(role -> "ADMIN".equals(role));
                } else {
                    authorizationChecked = roles.stream().anyMatch(
                            role -> "CLIENT".equals(role)
                                    || "MANAGER".equals(role)
                                    || "VIP".equals(role)
                    );
                }
                if (!authorizationChecked) {
                    return false;
                }
            } catch (Exception exception) {
                log.error("Error logging in: {}", exception.getMessage());
                response.setHeader("error", exception.getMessage());
                response.setStatus(FORBIDDEN.value());
                Map<String, String> error = new HashMap<>();
                error.put("error_message", exception.getMessage());
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), error);
            }
        }
        return authorizationChecked;
    }


    public static boolean isSignatureValid(SignedJWT signedJWT) {
        try {
            JWSVerifier verifier = new RSASSAVerifier((RSAPublicKey) InfraSettings.keypairLoader("password").getPublic());
            return signedJWT.verify(verifier);
        } catch (JOSEException e) {
            return false;
        }
    }
}
