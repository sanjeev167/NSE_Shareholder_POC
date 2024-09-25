/**
 * 
 */
package com.nse.common.sec.service;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.SignatureException;
/**
 * @author sanjeevkumar
 * 16-Mar-2024
 * 7:52:48 pm 
 * Objective : 
 */
@Component
public class JwtUtils {
  private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

  @Value("${nse.api.jwtSecret}")
  private String jwtSecret;

  @Value("${nse.api.jwtExpirationInMin}")
  private int jwtExpirationInMin;

  public String extractUsername(String token) {
      return extractClaim(token, Claims::getSubject);
  }

  public Date extractExpiration(String token) {
      return extractClaim(token, Claims::getExpiration);
  }

  public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
      final Claims claims = extractAllClaims(token);
      return claimsResolver.apply(claims);
  }

  private Claims extractAllClaims(String token) {
      return Jwts
              .parserBuilder()
              .setSigningKey(getSignKey())
              .build()
              .parseClaimsJws(token)
              .getBody();
  }

  private Boolean isTokenExpired(String token) {
      return extractExpiration(token).before(new Date());
  }

  public Boolean validateToken(String token, UserDetails userDetails) throws SignatureException {
	  final String username = extractUsername(token);
      return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
  }



  public String GenerateToken(String username){
      Map<String, Object> claims = new HashMap<>();
      return createToken(claims, username);
  }



  private String createToken(Map<String, Object> claims, String username) {

      return Jwts.builder()
              .setClaims(claims)
              .setSubject(username)
              .setIssuedAt(new Date(System.currentTimeMillis()))
              .setExpiration(new Date(System.currentTimeMillis()+jwtExpirationInMin*60000))
              .signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
  }

  private SecretKey getSignKey() {
      byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
      return Keys.hmacShaKeyFor(keyBytes);
  }
  
  
 
}//End of JwtUtils 
