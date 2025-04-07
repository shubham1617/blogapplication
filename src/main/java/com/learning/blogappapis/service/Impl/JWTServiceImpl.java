package com.learning.blogappapis.service.Impl;

import com.learning.blogappapis.payloads.UserDTO;
import com.learning.blogappapis.service.JWTService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JWTServiceImpl implements JWTService
{
    private  String secrectKey =null;

    @Override
    public String generateToken(UserDTO userDTO)
    {
        Map<String,Object> claims = new HashMap<>();
        return Jwts
                .builder()
                .claims()
                .add(claims)
                .subject(userDTO.getEmail())
                .issuer("Shubham")
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+ 30*60*1000))
                .and()
                .signWith(generateKey())
                .compact();
    }

    @Override
    public String extractEmail(String token)
    {
        return extractClaims(token,Claims::getSubject) ;
    }

    private <T> T extractClaims(String token, Function<Claims,T> claimResolver)
    {
        Claims claims = extractClaims(token);
        return claimResolver.apply(claims);
    }

    private Claims extractClaims(String token)
    {
       return  Jwts.parser().verifyWith(generateKey()).build().parseSignedClaims(token).getPayload();
    }

    @Override
    public boolean isTokenValid(String jwtToken, UserDetails userDetails)
    {
        final String userName = extractEmail(jwtToken);
        return (userName.equals(userDetails.getUsername()) && !isTokenExpired(jwtToken));
    }

    private boolean isTokenExpired(String jwtToken)
    {
        return extractExpiration(jwtToken).before(new Date());
    }

    private Date extractExpiration(String jwtToken)
    {
        return extractClaims(jwtToken, Claims::getExpiration);
    }

    private SecretKey generateKey()
    {
        byte[] decode = Decoders.BASE64.decode(getSecrectKey());
        return Keys.hmacShaKeyFor(decode);
    }

    public String getSecrectKey()
    { //todo: make this function declare in the environment to it should be hardcoded
        return secrectKey ="86e9e9f271a6deb3d6b72a91fdff73324ca24be07e40562efc10df3df5fc556b6fe4bfdbcfaf41212f82d0720b1022d505eee137eab775a02f85d904e773c99c";
    }
}
