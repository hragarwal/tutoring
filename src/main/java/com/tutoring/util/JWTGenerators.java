package com.tutoring.util;

import java.security.Key;
import java.util.Date;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * Created by himanshu.agarwal on 21-02-2017.
 */
@Service
public class JWTGenerators {

    @Autowired
    Environment environment;

    public String encrypt(String textData){
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        Date date = DateTimeUtil.getCurrentDate();
        byte[] apiSecretKeyBytes = DatatypeConverter.parseBase64Binary(environment.getProperty("aes.secretKey"));
        Key signingKey = new SecretKeySpec(apiSecretKeyBytes,signatureAlgorithm.getJcaName());
        JwtBuilder jwtBuilder = Jwts.builder().setId(textData).setIssuedAt(date).signWith(signatureAlgorithm,signingKey);
        return jwtBuilder.compact();
    }

    public boolean decrypt(String encryptedData){
        boolean flag=true;
        try {
            byte[] apiSecretKeyBytes = DatatypeConverter.parseBase64Binary(environment.getProperty("aes.secretKey"));
            Jwts.parser().setSigningKey(apiSecretKeyBytes).parseClaimsJws(encryptedData).getBody();
        }catch (JwtException e){
            flag = false;

        }
        return flag;
    }

}
