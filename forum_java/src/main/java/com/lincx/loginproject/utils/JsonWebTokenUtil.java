package com.lincx.loginproject.utils;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Date;

/**
 *
 */
public class JsonWebTokenUtil {
    /**
     * jwt密钥 必须大于32个字符
     */
    private static String SECRET_KEY = "54f12kkkc8-1fff56-45chg1-a3fsjc-2c546bc2b";
    /**
     * jwt有效时间
     */
    private static long TOKEN_TIMEOUT = 1000*60*60*24;//过期时间为24小时
    /**
     * jwt生成方
     */
    private static String JWT_ISSUE = "LINCX_JAVA";
    private static String VERSION_ID = "lincx_v001";
    /**
     * 生成token
     * @param id
     * @param name
     * @return
     */
    public static String CreateJWT(int id,String name,String role){
        String token;
        try {
            Date exptime = new Date(System.currentTimeMillis()+TOKEN_TIMEOUT);
            SecretKey key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
            token = Jwts.builder().setId(VERSION_ID)
                    .claim("role",role)
                    .claim("uid",id)
                    .setSubject(name)
                    .setIssuer(JWT_ISSUE)
                    .signWith(key)
                    .setExpiration(exptime).compact();
        }catch (JwtException exception){
            System.out.println(exception.getMessage());
            return null;
        }
        return token;
    }

    /**
     *
     * @param token
     * @return
     */
    public static Jws<Claims> parseJWT(String token){
        Jws<Claims> claimsJws;
        try {
            Key key = new SecretKeySpec(SECRET_KEY.getBytes(),SignatureAlgorithm.HS256.getJcaName());
            claimsJws =  Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
        }catch (JwtException exception){
            System.out.println(exception.getMessage());
            return null;
        }
        return claimsJws;
    }
}
