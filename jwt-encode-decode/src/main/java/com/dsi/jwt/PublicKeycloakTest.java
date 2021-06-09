package com.dsi.jwt;

import com.auth0.jwk.Jwk;
import com.auth0.jwk.UrlJwkProvider;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;

import java.net.URL;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;

public class PublicKeycloakTest {

    public static void main(String[] args) {
        // verify JWT signature based on Access Identity's JWKS RSA public key (RS256)
        try {

            Jwk jwk = new UrlJwkProvider(new URL("http://localhost:8000/auth/realms/BANBEIS/protocol/openid-connect/certs")).get("kBH2ZUywXZ57r7rmqRxWOnHrHJCxdgA22XVo5F9KEnI");
            final PublicKey publicKey = jwk.getPublicKey();

            if (!(publicKey instanceof RSAPublicKey)) {
                throw new IllegalArgumentException("Key with ID "  + " was found in JWKS but is not a RSA-key.");
            }

            String jwtString= "eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJrQkgyWlV5d1haNTdyN3JtcVJ4V09uSHJISkN4ZGdBMjJYVm81RjlLRW5JIn0.eyJleHAiOjE2MjMyNTE4OTksImlhdCI6MTYyMzIxNTg5OSwiYXV0aF90aW1lIjoxNjIzMjE1ODk5LCJqdGkiOiI4OGMxMDIyYi0yZDU5LTRjYTMtYTdiMy1lNzc1MmM4ZTIwNDYiLCJpc3MiOiJodHRwOi8vbG9jYWxob3N0OjgwMDAvYXV0aC9yZWFsbXMvQkFOQkVJUyIsImF1ZCI6ImFjY291bnQiLCJzdWIiOiI0MDYyZDQ1Yy1iZTY3LTQ2ZWUtYTU1OC0zNGMxNjViY2JjMGMiLCJ0eXAiOiJCZWFyZXIiLCJhenAiOiJuZXh0LWNsaWVudCIsInNlc3Npb25fc3RhdGUiOiI5MDVjMTRkYy1hOTYzLTQ4ZjMtOWMyMS00NDNhNjNmYTJkNjciLCJhY3IiOiIxIiwiYWxsb3dlZC1vcmlnaW5zIjpbImh0dHA6Ly9sb2NhbGhvc3Q6ODA4MC8qIiwiaHR0cDovL2xvY2FsaG9zdDozMDAwIl0sInJlc291cmNlX2FjY2VzcyI6eyJuZXh0LWNsaWVudCI6eyJyb2xlcyI6WyJzdHVkZW50Il19LCJhY2NvdW50Ijp7InJvbGVzIjpbIm1hbmFnZS1hY2NvdW50IiwibWFuYWdlLWFjY291bnQtbGlua3MiLCJ2aWV3LXByb2ZpbGUiXX19LCJzY29wZSI6Im9wZW5pZCBlbWFpbCBwcm9maWxlIiwiZW1haWxfdmVyaWZpZWQiOmZhbHNlLCJuYW1lIjoiU3R1ZGVudCBBcHByZW50aWNlIiwicHJlZmVycmVkX3VzZXJuYW1lIjoic3R1ZGVudCIsImdpdmVuX25hbWUiOiJTdHVkZW50IiwiZmFtaWx5X25hbWUiOiJBcHByZW50aWNlIiwiZW1haWwiOiJzdHVkZW50QHNtZy5jb20ifQ.ceKgceD0zPecpQs47gEdLDsDC8pkscBAzi-RqrL9lfefTR866eXbPBA2iOtFvrTYwk3PLzLZ89iuLuf5JoGs_iJIiO-mcvxkVHuGvo21HIG_0nDus5Y6Ats9JZVEWsoPjXMKTeeQrAk_N8j6FKAhkRB8T35A30uOMeX1CUMKL8pz6Dg4U_EHgJhx1sJnUUwvXpMkRJrZYJlKA2CIPocH6JJPd2aflMGZSTcweiyZzYRS9CSkfyng-bv_MS-lZITgTGf8LNUM3V1lPKqXezcLhuAqME38TdoMcvYdKA5wN2BWRPUyKf-nJoxh9Eqh9kkvmU_C9rO6SONs6KIF-Mfysg";

            //PublicKey publicKey = getPublicKey();

            Jws<Claims> jwt = Jwts.parserBuilder()
                    .setSigningKey(publicKey)
                    .build()
                    .parseClaimsJws(jwtString);

            System.out.println(jwt);

           /* Algorithm algorithm = Algorithm.RSA256((RSAPublicKey) publicKey, null);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(issuer)
                    .build(); //Reusable verifier instance

            verifier.verify(bearerToken);*/

           // LOGGER.info("Token verified!");

        } catch (Exception e) {
            e.printStackTrace();
           // LOGGER.error(e.getMessage());
           // throw new InvalidAccessTokenException("JWTVerificationException - Invalid token signature.");
        }
    }
}
