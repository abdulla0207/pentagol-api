package uz.pentagol.util;

import io.jsonwebtoken.*;
import uz.pentagol.dto.JwtDTO;

import java.util.Date;

public class JwtUtil {
    private static final String secretKey = "pentagol";

    // This static method accepts the admin id and username of the admin who wants to login to the website
    // and creates a Token by encoding this information
    public static String encode(Integer adminId, String username){
        JwtBuilder jwtBuilder = Jwts.builder();
        jwtBuilder.setIssuedAt(new Date());
        jwtBuilder.signWith(SignatureAlgorithm.HS256, secretKey);
        jwtBuilder.claim("id", adminId);
        jwtBuilder.claim("username", username);

        int tokenLifetime = 1000 * 3600 * 24;
        jwtBuilder.setExpiration(new Date(System.currentTimeMillis() + (tokenLifetime)));
        jwtBuilder.setIssuer("pentaGol");

        String generatedToken = jwtBuilder.compact();

        return generatedToken;
    }

    public static JwtDTO decode(String token){
        try{
            JwtParser jwtParser = Jwts.parser();
            jwtParser.setSigningKey(secretKey);

            Jws<Claims> claimsJws = jwtParser.parseClaimsJws(token);

            Claims body = claimsJws.getBody();

            Integer id = (Integer) body.get("id");
            String username = (String) body.get("username");

            return new JwtDTO(id, username);
        }catch (JwtException e){
            e.printStackTrace();
        }
        return null;
    }

    public static String getToken(String header){
        if(!header.startsWith("Bearer"))
            return null;

        String[] s = header.split(" ");
        if(s.length != 2)
            throw new RuntimeException("Wrong header Token");


        return s[1].trim();
    }
}