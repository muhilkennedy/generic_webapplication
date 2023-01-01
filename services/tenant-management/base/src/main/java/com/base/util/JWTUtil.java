package com.base.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.apache.commons.lang3.StringUtils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JWTUtil {
	
	// 2hours validity
	public static final long JWT_TOKEN_VALIDITY = 2 * 60 * 60;
	// 1month validity incase of remember me action
	public static final long JWT_TOKEN_VALIDITY_REMEMBER_ME = 744 * 60 * 60;
	
	private static final String CLAIM_USER_TYPE = "UserType";
	public static final String USER_TYPE_EMPLOYEE = "Employee";
	public static final String USER_TYPE_CUSTOMER = "Customer";


	// retrieve email from jwt token
	public static String getUserIdFromToken(String token){
		return getClaimFromToken(token, Claims::getSubject);
	}

	
	public static boolean isEmployeeUser(String token) {
		Claims claims = getAllClaimsFromToken(token);
		String type = (String) claims.get(CLAIM_USER_TYPE);
		if (type.equalsIgnoreCase(USER_TYPE_EMPLOYEE)) {
			return true;
		}
		return false;
	}
	 

	// retrieve expiration date from jwt token
	public static Date getExpirationDateFromToken(String token) {
		return getClaimFromToken(token, Claims::getExpiration);
	}

	public static <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) throws ExpiredJwtException {
		final Claims claims = getAllClaimsFromToken(token);
		return claimsResolver.apply(claims);
	}

	// for retrieveing any information from token we will need the secret key
	private static Claims getAllClaimsFromToken(String token) {
		return Jwts.parser().setSigningKey(PropertiesUtil.getJWTSecret()).parseClaimsJws(token).getBody();
	}

	// check if the token has expired
	private static Boolean isTokenExpired(String token) {
		final Date expiration = getExpirationDateFromToken(token);
		return expiration.before(new Date());
	}

	/**
	 * @param userDetails user object
	 * @return JWT token for users
	 */
	// implement later when user entity is created.
	public static String generateToken(String userRootId, String userType, boolean rememberMe) {
		Map<String, Object> claims = new HashMap<>();
		claims.put(CLAIM_USER_TYPE, userType);
		//we can add more details about user in future in Claims map if needed.
		return doGenerateToken(claims, userRootId, rememberMe);
	}

	/**
	 * @param claims  map for additional data
	 * @param subject user email
	 * @return JWT
	 */
	private static String doGenerateToken(Map<String, Object> claims, String subject, boolean rememberMe) {
		return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis()
						+ (rememberMe ? JWT_TOKEN_VALIDITY_REMEMBER_ME : JWT_TOKEN_VALIDITY) * 1000))
				.signWith(SignatureAlgorithm.HS512, PropertiesUtil.getJWTSecret()).compact();
	}

	/**
	 * @param token
	 * @return true if token is valid.
	 */
	public static Boolean validateToken(String token) throws ExpiredJwtException{
		String rootId = getUserIdFromToken(token);
		return (!(StringUtils.isEmpty(rootId)) && !(isTokenExpired(token)));
	}

	public static String extractToken(String token) {
		return token.replace("Bearer", "").trim();
	}

}
