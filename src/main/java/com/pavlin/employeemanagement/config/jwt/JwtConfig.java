package com.pavlin.employeemanagement.config.jwt;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Jwts.SIG;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtConfig {

  @Value("${jwt.private}")
  private String privateKeyString;

  @Value("${jwt.public}")
  private String publicKeyString;

  @Value("${jwt.expiration}")
  private Long expirationTime;

  @Bean
  public JwtBuilder jwtBuilder() {
    return Jwts
        .builder()
        .signWith(getPrivateKey(), SIG.ES512)
        .issuedAt(new Date(System.currentTimeMillis()))
        .expiration(new Date(System.currentTimeMillis() + expirationTime));
  }

  @Bean
  public JwtParser jwtParser() {
    return Jwts
        .parser()
        .verifyWith(getPublicKey())
        .build();
  }

  private PrivateKey getPrivateKey() {
    try {
      byte[] keyBytes = Base64.getDecoder().decode(privateKeyString);
      PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
      KeyFactory keyFactory = KeyFactory.getInstance("EC");
      return keyFactory.generatePrivate(spec);
    } catch (Exception e) {
      throw new IllegalArgumentException("Failed to load private key", e);
    }
  }

  private PublicKey getPublicKey() {
    try {
      byte[] keyBytes = Base64.getDecoder().decode(publicKeyString);
      X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
      KeyFactory keyFactory = KeyFactory.getInstance("EC");
      return keyFactory.generatePublic(spec);
    } catch (Exception e) {
      throw new IllegalArgumentException("Failed to load public key", e);
    }
  }
}
