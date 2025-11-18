package com.sky.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtil {
    /**
     * 生成jwt
     * 使用Hs256算法, 私匙使用固定秘钥
     *
     * @param secretKey jwt秘钥
     * @param ttlMillis jwt过期时间(毫秒)
     * @param claims    设置的信息
     *
     * @return
     */
    public static String createJWT(String secretKey, long ttlMillis, Map<String, Object> claims) {
        // 指定签名的时候使用的签名算法，也就是header那部分
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        // 生成JWT的时间
        long expMillis = System.currentTimeMillis() + ttlMillis;
        Date exp = new Date(expMillis);

        // 设置jwt的body
        JwtBuilder builder = Jwts.builder()
                // 如果有私有声明，一定要先设置这个自己创建的私有的声明，这个是给builder的claim赋值，一旦写在标准的声明赋值之后，就是覆盖了那些标准的声明的
                .setClaims(claims)
                // 设置签名使用的签名算法和签名使用的秘钥
                .signWith(signatureAlgorithm, secretKey.getBytes(StandardCharsets.UTF_8))
                // 设置过期时间
                .setExpiration(exp);

        return builder.compact();
    }

    /**
     * 生成jwt
     * 使用 HS256 算法, 私钥使用固定秘钥
     *
     * @param secretKey jwt秘钥
     * @param ttlMillis jwt过期时间(毫秒)
     * @param claims    自定义的负载（相当于原来的 setClaims）
     *
     * @return token 字符串
     */
    public static String createJWT1(String secretKey, long ttlMillis, Map<String, Object> claims) {
        long expMillis = System.currentTimeMillis() + ttlMillis;
        Date exp = new Date(expMillis);

        Algorithm algorithm = Algorithm.HMAC256(secretKey); // 用 String 即可

        com.auth0.jwt.JWTCreator.Builder builder = JWT.create()
                .withExpiresAt(exp);

        if (claims != null) {
            claims.forEach((k, v) -> {
                if (v instanceof Boolean) {
                    builder.withClaim(k, (Boolean) v);
                } else if (v instanceof Integer) {
                    builder.withClaim(k, (Integer) v);
                } else if (v instanceof Long) {
                    builder.withClaim(k, (Long) v);
                } else if (v instanceof Double) {
                    builder.withClaim(k, (Double) v);
                } else if (v instanceof String) {
                    builder.withClaim(k, (String) v);
                } else if (v != null) {
                    builder.withClaim(k, v.toString());
                }
            });
        }

        return builder.sign(algorithm);
    }

    /**
     * Token解密
     *
     * @param secretKey jwt秘钥 此秘钥一定要保留好在服务端, 不能暴露出去, 否则sign就可以被伪造, 如果对接多个客户端建议改造成多个
     * @param token     加密后的token
     *
     * @return
     */
    public static Claims parseJWT(String secretKey, String token) {
        // 得到DefaultJwtParser
        Claims claims = Jwts.parser()
                // 设置签名的秘钥
                .setSigningKey(secretKey.getBytes(StandardCharsets.UTF_8))
                // 设置需要解析的jwt
                .parseClaimsJws(token).getBody();
        return claims;
    }

    /**
     * Token 解密与验证
     *
     * @param secretKey jwt秘钥
     * @param token     加密后的token
     *
     * @return 解析后的 claims（用 Map<String, Object> 包一下，方便调用方使用）
     */
    public static Map<String, Object> parseJWT1(String secretKey, String token) {
        Algorithm algorithm = Algorithm.HMAC256(secretKey);

        // 如果 token 前面有 "Bearer " 之类的，先去掉
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT decodedJWT = null;  // 这里会抛 SignatureVerificationException
        try {
            decodedJWT = verifier.verify(token);
        } catch (JWTVerificationException e) {
            throw new RuntimeException(e);
        }

        Map<String, Object> result = new HashMap<>();
        decodedJWT.getClaims().forEach((k, v) -> {
            if (v.asBoolean() != null) {
                result.put(k, v.asBoolean());
            } else if (v.asInt() != null) {
                result.put(k, v.asInt());
            } else if (v.asLong() != null) {
                result.put(k, v.asLong());
            } else if (v.asDouble() != null) {
                result.put(k, v.asDouble());
            } else if (v.asString() != null) {
                result.put(k, v.asString());
            } else {
                result.put(k, v.as(Object.class));
            }
        });

        return result;
    }
}
