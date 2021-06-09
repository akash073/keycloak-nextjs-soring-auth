package com.dsi.jwt.security.config;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.*;

@Service
public class JwtUtil {

	private String secret;
	private int jwtExpirationInMs;
	private int refreshExpirationDateInMs;

	@Value("${jwt.secret}")
	public void setSecret(String secret) {
		this.secret = secret;
	}

	@Value("${jwt.expirationDateInMs}")
	public void setJwtExpirationInMs(int jwtExpirationInMs) {
		this.jwtExpirationInMs = jwtExpirationInMs;
	}
	
	@Value("${jwt.refreshExpirationDateInMs}")
	public void setRefreshExpirationDateInMs(int refreshExpirationDateInMs) {
		this.refreshExpirationDateInMs = refreshExpirationDateInMs;
	}

	public String generateToken(UserDetails userDetails) {
		Map<String, Object> claims = new HashMap<>();

		Collection<? extends GrantedAuthority> roles = userDetails.getAuthorities();

		if (roles.contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
			claims.put("isAdmin", true);
		}
		if (roles.contains(new SimpleGrantedAuthority("ROLE_USER"))) {
			claims.put("isUser", true);
		}
		
		return doGenerateToken(claims, userDetails.getUsername());
	}

	private String doGenerateToken(Map<String, Object> claims, String subject) {
		return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + jwtExpirationInMs))
				.signWith(SignatureAlgorithm.HS512, secret).compact();

	}
	
	public String doGenerateRefreshToken(Map<String, Object> claims, String subject) {

		return Jwts.builder().setClaims(claims).setSubject(subject)//.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + refreshExpirationDateInMs))
				.signWith(SignatureAlgorithm.HS512, secret).compact();

	}

	public boolean validateToken(String authToken) {
		try {
			Jws<Claims> claims = parseJwt(authToken);
			return true;
		} catch (SignatureException | MalformedJwtException | UnsupportedJwtException | IllegalArgumentException ex) {
			throw new BadCredentialsException("INVALID_CREDENTIALS", ex);
		} catch (ExpiredJwtException ex) {
			throw ex;
		} catch (InvalidKeySpecException e) {
			throw new BadCredentialsException("INVALID_SPEC", e);
		} catch (NoSuchAlgorithmException e) {
			throw new BadCredentialsException("INVALID_ALGO", e);
		}
	}

	public String getUsernameFromToken(String token) throws InvalidKeySpecException, NoSuchAlgorithmException {
		Jws<Claims> claims = parseJwt(token);
		return claims.getBody().getSubject();

	}

	public List<SimpleGrantedAuthority> getRolesFromToken(String token) throws InvalidKeySpecException, NoSuchAlgorithmException {
		Claims claims = parseJwt(token).getBody();

		List<SimpleGrantedAuthority> roles = new ArrayList<>();


		Map<String,Object> realmRoles = (HashMap<String,Object>)claims.get("realm_access");


		for(String key : realmRoles.keySet()) {
			System.out.println(key);
			List<String> roless =  (ArrayList)realmRoles.get("roles");
			for (String r: roless) {
				roles.add(new SimpleGrantedAuthority("ROLE_"+ r.toUpperCase()));
			}
		}

		Map<String,Object> resourceRoles = (HashMap<String,Object>)claims.get("resource_access");


		for(String key : resourceRoles.keySet()) {
			System.out.println(key);
			Map<String,Object> roless =  (HashMap<String,Object>)resourceRoles.get(key);


			for (String keys : roless.keySet()){




				roles.add(new SimpleGrantedAuthority("ROLE_"+ roless.get("roles").toString().toUpperCase()));
			}


		}

		/*Boolean isAdmin = claims.get("isAdmin", Boolean.class);
		Boolean isUser = claims.get("isUser", Boolean.class);

		if (isAdmin != null && isAdmin) {
			roles = Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN"));
		}else {
			roles = Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
		}*/
		return roles;

	}


	/*Public key validation*/

	public  Jws<Claims> parseJwt(String jwtString) throws InvalidKeySpecException, NoSuchAlgorithmException {

		PublicKey publicKey = getPublicKey();

		Jws<Claims> jwt = Jwts.parserBuilder()
				.setSigningKey(publicKey)
				.build()
				.parseClaimsJws(jwtString);

		return jwt;
	}

	private  PublicKey getPublicKey() throws NoSuchAlgorithmException, InvalidKeySpecException {
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
