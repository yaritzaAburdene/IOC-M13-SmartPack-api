package com.smartpack.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

/**
 * Classe JwtService
 * Aquesta Classe es carrega de crear, signar, validar i llegir els token JWT.
 */
@Service
public class JwtService {

    @Value("${security.jwt.secret-key}") // obtenir el scret key de propietats
    private String secretKey;

    @Value("${security.jwt.expiration-time}") // obtenir el temps valid (1h) de propietats
    private long jwtExpiration;

    /**
     * extractUsername
     * s'encarrega d'extraer el nom de l'usuari del token
     * 
     * @param token String
     * @return String
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * extractClaim
     * Extrau les dades del token
     * 
     * @param <T>            T
     * @param token          String
     * @param claimsResolver Claims or T
     * @return T
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Generate Token
     * es genera el token
     * 
     * @param userDetails UserDetails
     * @return String
     */
    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    /**
     * Generate Token
     * es genera el token amb la informacio necessaria
     * 
     * @param extraClaims String or Object
     * @param userDetails UserDetails
     * @return String
     */
    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return buildToken(extraClaims, userDetails, jwtExpiration);
    }

    /**
     * Get Expiration Time
     * 
     * @return long
     */
    public long getExpirationTime() {
        return jwtExpiration;
    }

    /**
     * Build Token
     * Utilitza la llibreria JWT per construir i signar el token amb HMAC-SHA256
     * 
     * @param extraClaims String o Object
     * @param userDetails UserDetails
     * @param expiration  long
     * @return String
     */
    private String buildToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails,
            long expiration) {
        return Jwts
                .builder()
                .setClaims(extraClaims) // afegeix claims personalitzats
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256) // signatura amb clau secreta
                .compact(); // converteix tot en un JWT string
    }

    /**
     * is Token Valid
     * Verifica que el token sigui del usuari i que no estigui expirat
     * 
     * @param token       String
     * @param userDetails UserDetails
     * @return boolean
     */
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    /**
     * is Token Expired
     * 
     * @param token String
     * @return boolean
     */
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * Extract Expiration
     * extrau la data del token
     * 
     * @param token String
     * @return Date
     */
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Extract All Claims
     * Llegeix les dades del token
     * 
     * @param token String
     * @return Claims
     */
    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Get Sign In key
     * Converteix la clau secreta en un Key vàlid per a signar/verificar el token
     * amb l'algorisme HMAC-SHA256.
     * 
     * @return Key
     */
    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
