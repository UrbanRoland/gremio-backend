package com.gremio.service;

import com.gremio.model.dto.response.AuthResponse;
import com.gremio.persistence.entity.User;
import com.gremio.service.interfaces.JwtService;
import com.gremio.service.interfaces.UserService;
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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.ConversionService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
@Service
@RequiredArgsConstructor
@Slf4j
public class JwtServiceImpl implements JwtService {
    @Value("${jwt.secret.key}")
    private String jwtSecretKey;
    @Value("${jwt.token.expiration}")
    private long jwtExpirationInMinutes;
    @Value("${jwt.refresh.expiration}")
    private long refreshExpirationInMinutes;
    @Value("${jwt.refresh.secret.key}")
    private String jwtSecretRefreshKey;

    private final UserService userService;
    private final ConversionService conversionService;

    /**
     * {@inheritDoc}
     */
    @Override
    public String extractUsername(final String token) {
        return extractClaim(token, Claims::getSubject, getSignInKey());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String generateToken(final UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String generateRefreshToken(final UserDetails userDetails) {
        return generateRefreshToken(new HashMap<>(), userDetails);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isTokenValid(final String token, final  UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AuthResponse refreshAuthToken(final String token) {
        final User user = userService.findUserByEmail(extractUsernameForRefreshToken(token));

        final AuthResponse authResponse = conversionService.convert(user, AuthResponse.class);

        if (authResponse != null) {
            authResponse.setAccessToken(this.generateToken(user));
        }
        return authResponse;
    }

    private String buildToken(
            final Map<String, Object> extraClaims,
            final UserDetails userDetails,
            final long expiration,
            final Key signingKey
    ) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(signingKey, SignatureAlgorithm.HS256)
                .compact();
    }

    private <T> T extractClaim(final String token, final  Function<Claims, T> claimsResolver, final Key key) {
        final Claims claims = extractAllClaims(token, key);
        return claimsResolver.apply(claims);
    }

    private String generateToken(
        final Map<String, Object> extraClaims,
        final UserDetails userDetails
    ) {
        return buildToken(extraClaims, userDetails, jwtExpirationInMinutes, getSignInKey());
    }

    private String generateRefreshToken(
        final Map<String, Object> extraClaims,
        final UserDetails userDetails
    ) {
        return buildToken(extraClaims, userDetails, refreshExpirationInMinutes, getRefreshKey());
    }

    private boolean isTokenExpired(final String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(final String token) {
        return extractClaim(token, Claims::getExpiration, getSignInKey());
    }
    private String extractUsernameForRefreshToken(final String token) {
        return extractClaim(token, Claims::getSubject, getRefreshKey());
    }
    private Claims extractAllClaims(final String token, final Key key) {
        return Jwts
                .parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey() {
        final byte[] keyBytes = Decoders.BASE64.decode(jwtSecretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
    private Key getRefreshKey() {
        final byte[] keyBytes = Decoders.BASE64.decode(jwtSecretRefreshKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}