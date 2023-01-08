package org.zerock.jpaweb.security.util;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.log4j.Log4j2;
import io.jsonwebtoken.impl.DefaultJws;
import io.jsonwebtoken.impl.DefaultClaims;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.ZonedDateTime;
import java.util.Date;

@Log4j2
public class JWTUtil {
    private String secretKey = "zerock123456789zerock123456789zerock123456789";
    Key key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    //1month
    private long expire = 60*24*30;

    public String generateToken(String content) throws Exception{

        return Jwts.builder()
                .setIssuedAt(new Date())
                .setExpiration(Date.from(ZonedDateTime.now().
                        plusMinutes(expire).toInstant()))
                .claim("sub",content)
                .signWith(key,SignatureAlgorithm.HS256)
                .compact();

    }
    public String validateAndExtract(String tokenStr)throws Exception{
        String contentValue = null;
        try{
            DefaultJws defaultJws = (DefaultJws) Jwts.parserBuilder()
                    .setSigningKey(key).build()
                    .parseClaimsJws(tokenStr);

            log.info(defaultJws);
            log.info(defaultJws.getBody().getClass());

            DefaultClaims claims = (DefaultClaims) defaultJws.getBody();

            log.info("------------------------");
            contentValue = claims.getSubject();
        }catch (Exception e){
            e.printStackTrace();
            log.error(e.getMessage());
            contentValue = null;
        }

        return contentValue;
    }
}
