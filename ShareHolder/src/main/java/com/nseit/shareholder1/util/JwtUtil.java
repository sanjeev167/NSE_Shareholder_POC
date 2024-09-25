package com.nseit.shareholder1.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Component
@RequestScope
public class JwtUtil {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    // private String SECRET_KEY = "secret";
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String extractUsername() {
        String token = this.getToken();
        if (token != null) {
            final Claims claims = extractAllClaims(token);
            return claims.get("preferred_username").toString();
        } else
            return "";
        // return extractClaim(token, Claims::getSubject);
    }
    
    public List<String> extractRolename() {
    	List<String> roleList=new ArrayList<>();
        String token = this.getToken();
        if (token != null) {
            final Claims claims = extractAllClaims(token);
            String resource = claims.get("resource_access").toString();
            String[] resourceList=resource.split("=");
            String sampleList = resourceList[2];
            String s=sampleList.substring((sampleList.indexOf("[")+1), sampleList.indexOf("]"));
            System.out.println("values-----111111111 "+s.split(","));
            String[] resourceList1=s.split(",");
            for(String x:resourceList1) {
            	roleList.add(x);
           }
            System.out.println("jwt roe------- "+roleList);
            return roleList;

            
            
        } else
            return roleList;
        // return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        String token1 = token.substring(0, token.lastIndexOf(".") + 1);
        return (Claims) Jwts.parser().parseClaimsJwt(token1).getBody();
        // setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    // private Claims extractAllClaims(String token) {
    // System.out.println("Jwts.parser().parseClaimsJwt(token).getBody()--->"+Jwts.parser().parseClaimsJwt(token).getBody());
    // return Jwts.parser().parseClaimsJwt(token).getBody();
    // }

    // private Boolean isTokenExpired(String token) {
    // return extractExpiration(token).before(new Date());
    // }
    //
    // public String generateToken(UserMaster user) {
    // Map<String, Object> claims = new HashMap<>();
    // return createToken(claims, user.getUsername());
    // }

    // private String createToken(Map<String, Object> claims, String subject) {
    //
    // return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new
    // Date(System.currentTimeMillis()))
    // .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
    //// .setExpiration(new Date(System.currentTimeMillis()+1000*60*2))
    // .signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
    // }

    // public Boolean validateToken(String token,boolean isTokenExistInDb ) {
    // final String username = extractUsername(token);
    // logger.info("token in validate token : "+token);
    // logger.info("username in validate token : "+username);
    // logger.info("userDetails.getUsername() in validate token :
    // "+isTokenExistInDb);
    // return isTokenExistInDb && !isTokenExpired(token);
    // }
}
