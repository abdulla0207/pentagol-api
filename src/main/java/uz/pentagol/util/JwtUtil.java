package uz.pentagol.util;

import io.jsonwebtoken.*;
import uz.pentagol.dto.JwtDTO;
import uz.pentagol.enums.UserRoleEnum;
import uz.pentagol.enums.UserStatusEnum;
import uz.pentagol.exceptions.TokenNotValidException;

import java.util.Date;

public class JwtUtil {
    private static final String secretKey = "pentagol";

    // This static method accepts the admin id and username of the admin who wants to login to the website
    // and creates a Token by encoding this information
    public static String encode(Integer id, UserStatusEnum statusEnum, String username, UserRoleEnum roleEnum){
        JwtBuilder jwtBuilder = Jwts.builder();
        jwtBuilder.setIssuedAt(new Date());
        jwtBuilder.signWith(SignatureAlgorithm.HS256, secretKey);
        jwtBuilder.claim("id", id);
        jwtBuilder.claim("role", roleEnum);
        jwtBuilder.claim("username", username);
        jwtBuilder.claim("status", statusEnum);

        int tokenLifetime = 1000 * 3600 * 24;
        jwtBuilder.setExpiration(new Date(System.currentTimeMillis() + (tokenLifetime)));
        jwtBuilder.setIssuer("pentaGol");

        String generatedToken = jwtBuilder.compact();

        return generatedToken;
    }
    public static String encodeEmailVerification(Integer id){
        JwtBuilder jwtBuilder = Jwts.builder();
        jwtBuilder.setIssuedAt(new Date());
        jwtBuilder.signWith(SignatureAlgorithm.HS256, secretKey);
        jwtBuilder.claim("id", id);

        int tokenLifetime = 120000;
        jwtBuilder.setExpiration(new Date(System.currentTimeMillis() +(tokenLifetime)));
        jwtBuilder.setIssuer("pentaGol");

        return jwtBuilder.compact();
    }

    public static JwtDTO decode(String token){
        try{
            JwtParser jwtParser = Jwts.parser();
            jwtParser.setSigningKey(secretKey);

            Jws<Claims> claimsJws = jwtParser.parseClaimsJws(token);

            Claims body = claimsJws.getBody();

            Integer id = (Integer) body.get("id");
            String username = (String) body.get("username");
            UserRoleEnum roleEnum = UserRoleEnum.valueOf((String) body.get("role"));

            return new JwtDTO(id, username, roleEnum);
        }catch (JwtException e){
            e.printStackTrace();
        }
        return null;
    }
    public static Integer decodeEmailVerification(String token){
        try{
            JwtParser jwtParser = Jwts.parser();
            jwtParser.setSigningKey(secretKey);
            Jws<Claims> claimsJws;
            try {
                claimsJws = jwtParser.parseClaimsJws(token);
            }catch (ExpiredJwtException e){
                throw new TokenNotValidException("Token has been expired");
            }
            Claims body = claimsJws.getBody();
            if(body.getExpiration().getTime() < System.currentTimeMillis())
                throw new TokenNotValidException("Link is expired");

            Integer id = (Integer) body.get("id");

            return id;
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
