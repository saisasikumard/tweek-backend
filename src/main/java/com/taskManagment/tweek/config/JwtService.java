package com.taskManagment.tweek.config;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import io.jsonwebtoken.Claims;

@Service
public class JwtService {
    private static final String SECRET =
            "78042298be2787cbb33a85f9a765dbbb36afd7e0b5c62fe67fa4054b6edae2f7a99321808bb367f85f8ff4434a8d2fe7843bbc52771a6da0b4636e3bd15f82c785b0ffd313cc1043f8c3a5bd688dfbd80f28d7cf20862bb973eae28995f3c7ad2ba0ab75a58cc93d78ac22b2f0f2b89878dc548309ede835a21bf4a2a63a54e8d0e848a7a39789e2e50bbb080a560c1d7187c5399ae2d071df1c3b0be2f94e4e7da022211914ac2a5a4baa6093c8acef9690f83b83ca55fe2f54e1171e6d40b33f56368f9bec64e507b804d5273c4afc5bba8826fc0d3c784257134dbcbe4e7037add9ce6ac427503093c49d926bc88348b4fd3bce60a34c2250c47dfe054164";


    public String extractUserName(String token){
        return extractClaim(token, Claims::getSubject);
    }
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUserName(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    public String generateToken(String userName){
        Map<String,Object> claims=new HashMap<>();
        return createToken(claims,userName);
    }
    private Claims extractAllClaims(String token) {
        byte[] secret= SECRET.getBytes();
        return Jwts.parser()
                .setSigningKey(getSignKey())
                .build().parseClaimsJws(token)
                .getBody();
    }

    private String createToken(Map<String, Object> claims, String userName) {
        return Jwts.builder().setClaims(claims)
                .setSubject(userName)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+1000*60*30))
                .signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
    }

    private Key getSignKey() {
        byte[] keyBytes= Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
