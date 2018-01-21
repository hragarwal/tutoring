package com.tutoring.util;

import java.security.Key;
import java.util.Date;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import com.tutoring.model.Profile;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

/**
 * Created by himanshu.agarwal on 21-02-2017.
 */
@Service
public class JWTGenerators {

    @Autowired
    Environment environment;

    public String encrypt(Profile profile) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        Date date = DateTimeUtil.getCurrentDate();
        byte[] apiSecretKeyBytes = DatatypeConverter.parseBase64Binary(environment.getProperty("aes.secretKey"));
        Key signingKey = new SecretKeySpec(apiSecretKeyBytes,signatureAlgorithm.getJcaName());
        JwtBuilder jwtBuilder = Jwts.builder().setId(profile.getEmail()).setIssuedAt(date)
                .claim("userId", profile.getId())
                .claim("roleId", profile.getRole().getId())
                .signWith(signatureAlgorithm,signingKey);
        return jwtBuilder.compact();
    }

    public int decrypt(String encryptedData){
        int userId = 0;
        try {
            byte[] apiSecretKeyBytes = DatatypeConverter.parseBase64Binary(environment.getProperty("aes.secretKey"));
            Jws<Claims> claims = Jwts.parser().setSigningKey(apiSecretKeyBytes).parseClaimsJws(encryptedData);
            if(claims.getBody().containsKey("userId") && claims.getBody().containsKey("roleId")) {
                userId = (int) claims.getBody().get("userId");
            }
        }catch (JwtException e){
            userId = 0;
        }
        return userId;
    }

}
