package com.orderSystem.common;

import com.orderSystem.DTO.UserDTO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.HashMap;

import java.util.Map;

public class JwtUtils {
    private static final String secret = "2564629396qqisme";
    public static String jwtCreate(int userId){
        Map<String,Object> claim = new HashMap<>();
        claim.put("userId", userId);
        return Jwts.builder().setClaims(claim).signWith(SignatureAlgorithm.HS256,secret).compact();
    }
    public static int jwtParse(String token){
        Claims body = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        return Integer.parseInt(body.get("userId").toString());
    }
}
