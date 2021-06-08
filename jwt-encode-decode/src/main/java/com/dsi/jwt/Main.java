package com.dsi.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

/*
 * Thanks
 * https://www.viralpatel.net/java-create-validate-jwt-token/
 */
public class Main {


    public static String createJwtSignedHMAC() throws InvalidKeySpecException, NoSuchAlgorithmException {

        PrivateKey privateKey = getPrivateKey();

        //Instant now = Instant.now();
        String jwtToken = Jwts.builder()
                .claim("name", "Jane Doe")
                .claim("email", "jane@example.com")
                .setSubject("jane")
                .setId(UUID.randomUUID().toString())
                //.setIssuedAt(Date.from(now))
                //.setExpiration(Date.from(now.plus(5l, ChronoUnit.MINUTES)))
                .signWith(privateKey)
                .compact();

        return jwtToken;
    }

    private static PrivateKey getPrivateKey() throws NoSuchAlgorithmException, InvalidKeySpecException {
        String rsaPrivateKey = "-----BEGIN PRIVATE KEY-----" +
                "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCGgF1kY4RF5Tv2b7qQ3LZSWLivLPv68BgiSTGsYZeV3PNUALFHOtm0/Zoydfr04R0q3DX25VgIAqkzsVAzE515AcWlf8/bAtJyA0ovfTHd7Pjg9pXeDiq0E9ki96+8W1bpMSip3NkWHqRjJSKDJkxFF67V8Et95bPaIfGPgrXUrh4sEhTwbMn1gGlywjg9BRAWz09f+wUko3Io+UxO8+k9yfeSVsGc9VhKprYjM7PdZL7raGvsUEGiPeCksnvDv6kiQqgiNkFSXhJx2LajWNXhjWhPyMftO2blK+akK9NWVvT+DKsARuL0inFw6FNsPtSxOUgMtHLxUzW4kJSgpWTbAgMBAAECggEAYccRnbhG4XULSVTiAQuis2xyHk8Kg9tlAA/Lv7BWvYiZkBjMx6Z9u/icz1A7dyfrx0u9I1aIz5H3pW+iiO8ajGyAXZbz6Rg9GstPdASEavwp1YkNXBdabfz73lLHe5u/K9kj4ZB+dHkkMjs9wq/lxFi6Lib5CnYhcFM6w4RXYVuAm6p0vG4NCMl+Rzh/eOSgrlB8W5MsLBafrHkutTbLbBfb9fDUrWBU8vVG9YW5cSSnBpewq1Lfg/XCBvoer6BdQ37oj6jizUFHP3SGBxwkfIqMS2rfNxXaUOndE+S6itAzL+pF4Pq6N1qKUIyei8W3+DH93xXIObzAR5K4GPNwcQKBgQDFhGLfiVcOJ3urqJui964nQtL0sBgv08rG+rFeRVT3NmC+KynidqyS+Q7tatp+VP8+NJ8HmY6I2kaOE0R5n/7GF+M7aESCugrYAsFJibvkcjAcvUeSiuPkEfZq9/Na9nyfp8JSm9d2Jz0/OX2KWhc+33howj65hAql9Mt3KXMN4wKBgQCuU3Jm93HueG9oBcpG4LJ05kUraDbUe0ZnRyTc22Wm461V+rNFSLi/oHjA8478OjAJnnJ6NskqKJkXUXL234toM8tpXA9GD3pAdtdN1FWPnwfgdK6ZR4M53L7cu15zozxC/fTQclEMeBO1gzPVC7+MgKTJICcCXHnUSeSSeI7+qQKBgG81CYI4ToaOnmfY58AK2cgLLMFNwGB1S49aqLCeMqd5u2B25v81uBaMGeZ8qgZFDuzULdHWmFTfKKhnmr8r4QNhgbP6EdDUtLRIYNFptht6WzJjM8ADaSoJHwe9EQxfCW3Ow29FhTtl/mrxkIhsDvh8U8wPyccj2+bye/hcowXnAoGAKtP7hkqCpijWjgLmKDWqAkhUdA6dceR7boIvD0ejmLXU7wuxoXL1WLbvE9CCr3sINETGMTpmf8ILoovzJ63SDR60ecURR5Aj49SOnuNsxeU3RTDFeIj2N3ABQC+US+qtsnO8Ar4jJWIWzYBXap8BR16PnY4Vp2Ha2Iny6v1NfEkCgYAhzqG7DMpRlSm92aWTiRKIa2AaSpXeTgZ67zDBYymH5Yra1zVtPhuc4v80iQna4Zbp3tmaLZWEnlGHKJmYyDOo2XwhcGKN42hhSmiAEYwEDnVWqmDDvbrl0vfwaEOzYTyo/QHUqYSL5/wEM0XFXhMhoe4iQ+kxi1N2FesrgTzKJg==" +
                "-----END PRIVATE KEY-----";

        rsaPrivateKey = rsaPrivateKey.replace("-----BEGIN PRIVATE KEY-----", "");
        rsaPrivateKey = rsaPrivateKey.replace("-----END PRIVATE KEY-----", "");

        System.out.println("rsaPrivateKey: " + rsaPrivateKey);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(rsaPrivateKey));
        KeyFactory kf = KeyFactory.getInstance("RSA");
        PrivateKey privKey = kf.generatePrivate(keySpec);
        return privKey;
    }


    public static void main(String[] args) throws InvalidKeySpecException, NoSuchAlgorithmException {
	// write your code here


        String jwtToken = createJwtSignedHMAC();
        System.out.println(jwtToken);

        jwtToken = "eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJrQkgyWlV5d1haNTdyN3JtcVJ4V09uSHJISkN4ZGdBMjJYVm81RjlLRW5JIn0.eyJleHAiOjE2MjMxNzE1NTYsImlhdCI6MTYyMzEzNTU1NiwiYXV0aF90aW1lIjoxNjIzMTM1NTU2LCJqdGkiOiJkOWU1ZTc3My1kMzdlLTRiOGUtODZkYS03Y2NiOTM3ZmMxYWYiLCJpc3MiOiJodHRwOi8vbG9jYWxob3N0OjgwMDAvYXV0aC9yZWFsbXMvQkFOQkVJUyIsImF1ZCI6ImFjY291bnQiLCJzdWIiOiI2M2JjMTc5Ny05ZGUyLTRjNzYtYjI0OS04ZGU4MGY4MGMyN2QiLCJ0eXAiOiJCZWFyZXIiLCJhenAiOiJuZXh0LWNsaWVudCIsInNlc3Npb25fc3RhdGUiOiJlYzhkOTQxNy1jNzAyLTQwZjktOTBkMS1mNGQwZGY2NDc3ODMiLCJhY3IiOiIxIiwiYWxsb3dlZC1vcmlnaW5zIjpbImh0dHA6Ly9sb2NhbGhvc3Q6ODA4MC8qIiwiaHR0cDovL2xvY2FsaG9zdDozMDAwIl0sInJlc291cmNlX2FjY2VzcyI6eyJuZXh0LWNsaWVudCI6eyJyb2xlcyI6WyJzdHVkZW50Il19LCJhY2NvdW50Ijp7InJvbGVzIjpbIm1hbmFnZS1hY2NvdW50IiwibWFuYWdlLWFjY291bnQtbGlua3MiLCJ2aWV3LXByb2ZpbGUiXX19LCJzY29wZSI6Im9wZW5pZCBwcm9maWxlIGVtYWlsIiwiZW1haWxfdmVyaWZpZWQiOmZhbHNlLCJuYW1lIjoiU3R1ZGVudCBBcHByZW50aWNlIiwicHJlZmVycmVkX3VzZXJuYW1lIjoic3R1ZGVudCIsImdpdmVuX25hbWUiOiJTdHVkZW50IiwiZmFtaWx5X25hbWUiOiJBcHByZW50aWNlIiwiZW1haWwiOiJzdHVkZW50QHNtZy5jb20ifQ.fivav54zhC3KBiZV3A4RJi4_cJkMBTEK2WWbGbDOJFRw-XuSyibIf86Ua6IQEK7YGfm29zGzAH1DCs37Y4nx0c7Qf6xsWpJNpg8HNqyGBCAzi-HQ7WZJZlVhB18OHlCW14-RnNg3cv67uTsRm6HcRNc5sMayxh798G6K2cHxS3aeUENVI9Cq0AhEWEoES0VcjNcxy_GAmiR_egcmGEE_XOvvcbsPLt385NriD-6rnqY9lKd4mxz3JnX-jc50qagXx7hzsI9_cCXeNEytwljxoR2wYOghwvztDhPqkpAtzvRt9u1wSbqq836uF7SyzR9IHBxBju6lHnsP1tx_xKQ2wA";

        Jws<Claims> claimsJws = parseJwt(jwtToken);
        System.out.println(claimsJws);

    }



    public static Jws<Claims> parseJwt(String jwtString) throws InvalidKeySpecException, NoSuchAlgorithmException {

        PublicKey publicKey = getPublicKey();

        Jws<Claims> jwt = Jwts.parserBuilder()
                .setSigningKey(publicKey)
                .build()
                .parseClaimsJws(jwtString);

        return jwt;
    }

    private static PublicKey getPublicKey() throws NoSuchAlgorithmException, InvalidKeySpecException {
        String rsaPublicKey = "-----BEGIN PUBLIC KEY-----" +
                "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAhoBdZGOEReU79m+6kNy2Uli4ryz7+vAYIkkxrGGXldzzVACxRzrZtP2aMnX69OEdKtw19uVYCAKpM7FQMxOdeQHFpX/P2wLScgNKL30x3ez44PaV3g4qtBPZIvevvFtW6TEoqdzZFh6kYyUigyZMRReu1fBLfeWz2iHxj4K11K4eLBIU8GzJ9YBpcsI4PQUQFs9PX/sFJKNyKPlMTvPpPcn3klbBnPVYSqa2IzOz3WS+62hr7FBBoj3gpLJ7w7+pIkKoIjZBUl4Scdi2o1jV4Y1oT8jH7Ttm5SvmpCvTVlb0/gyrAEbi9IpxcOhTbD7UsTlIDLRy8VM1uJCUoKVk2wIDAQAB"+
                "-----END PUBLIC KEY-----";
        rsaPublicKey = rsaPublicKey.replace("-----BEGIN PUBLIC KEY-----", "");
        rsaPublicKey = rsaPublicKey.replace("-----END PUBLIC KEY-----", "");


        System.out.println("rsaPublicKey: " + rsaPublicKey);

        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(rsaPublicKey));
        KeyFactory kf = KeyFactory.getInstance("RSA");
        PublicKey publicKey = kf.generatePublic(keySpec);
        return publicKey;
    }
}
