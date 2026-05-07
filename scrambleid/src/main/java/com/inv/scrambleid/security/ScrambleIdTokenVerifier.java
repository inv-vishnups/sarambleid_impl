package com.inv.scrambleid.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Jwk;
import io.jsonwebtoken.security.JwkSet;
import io.jsonwebtoken.security.Jwks;
import io.jsonwebtoken.security.PublicJwk;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.PublicKey;

@Component
@RequiredArgsConstructor
public class ScrambleIdTokenVerifier {

    private final WebClient webClient;

    @Value("${scramble.oidc.jwks-uri}")
    private String jwksUri;

    @Value("${scramble.oidc.issuer}")
    private String expectedIssuer;

    @Value("${application.client-id}")
    private String expectedAudience;

    public Claims parseAndVerify(String idToken) {
        if (idToken == null || idToken.isBlank()) {
            throw new IllegalArgumentException("id_token is missing");
        }
        String kid = extractKeyId(idToken);
        String jwksJson = webClient.get()
                .uri(jwksUri)
                .retrieve()
                .bodyToMono(String.class)
                .block();
        if (jwksJson == null || jwksJson.isBlank()) {
            throw new IllegalStateException("Empty JWKS response");
        }
        JwkSet jwkSet = Jwks.setParser().build().parse(jwksJson);
        PublicKey verificationKey = resolvePublicKey(jwkSet, kid);

        return Jwts.parser()
                .verifyWith(verificationKey)
                .requireIssuer(expectedIssuer)
                .requireAudience(expectedAudience)
                .build()
                .parseSignedClaims(idToken)
                .getPayload();
    }

    private static String extractKeyId(String idToken) {
        String[] parts = idToken.split("\\.");
        if (parts.length < 2) {
            throw new IllegalArgumentException("Invalid JWT format");
        }
        try {
            String headerJson = new String(Decoders.BASE64URL.decode(parts[0]), StandardCharsets.UTF_8);
            return extractJsonStringField(headerJson, "kid");
        } catch (IllegalArgumentException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new IllegalArgumentException("Failed to read JWT header", ex);
        }
    }

    /** Minimal parser for {@code "kid":"..."} in the JWT header (no JSON library). */
    private static String extractJsonStringField(String json, String field) {
        String needle = "\"" + field + "\"";
        int keyIdx = json.indexOf(needle);
        if (keyIdx < 0) {
            throw new IllegalArgumentException("JWT header missing \"" + field + "\"");
        }
        int colon = json.indexOf(':', keyIdx + needle.length());
        if (colon < 0) {
            throw new IllegalArgumentException("Invalid JWT header JSON");
        }
        int i = colon + 1;
        while (i < json.length() && Character.isWhitespace(json.charAt(i))) {
            i++;
        }
        if (i >= json.length() || json.charAt(i) != '"') {
            throw new IllegalArgumentException("JWT header field \"" + field + "\" must be a string");
        }
        int start = i + 1;
        int end = json.indexOf('"', start);
        if (end < 0) {
            throw new IllegalArgumentException("Unclosed string for \"" + field + "\" in JWT header");
        }
        return json.substring(start, end);
    }

    private static PublicKey resolvePublicKey(JwkSet jwkSet, String kid) {
        for (Jwk<?> jwk : jwkSet.getKeys()) {
            if (jwk == null || !kid.equals(jwk.getId())) {
                continue;
            }
            if (!(jwk instanceof PublicJwk<?> publicJwk)) {
                continue;
            }
            Key key = publicJwk.toKey();
            if (!(key instanceof PublicKey publicKey)) {
                throw new IllegalStateException("Expected a public key for kid " + kid);
            }
            return publicKey;
        }
        throw new IllegalArgumentException("No signing key in JWKS for kid: " + kid);
    }
}
