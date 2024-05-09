package com.jm.tfg.Seguridad.JWT;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtUtils {

    private String secret = "buenosdias";

    public String extraerUsuarioNombre(String token){
        return extraerReclamo(token,Claims::getSubject);
    }

    public Date extraerCaducidad(String token){
        return extraerReclamo(token,Claims::getExpiration);
    }

    public <T> T extraerReclamo(String token, Function<Claims,T> claimsResolver){
        final Claims claims = extraerTodosReclamos(token);
        return  claimsResolver.apply(claims);
    }

    public Claims extraerTodosReclamos(String token){
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    public Boolean tokenCaducado(String token){
        return  extraerCaducidad(token).before(new Date());
    }


    public String generarToken(String username,String role){
        Map <String, Object> claims = new HashMap<>();
        claims.put("role",role);
        return crearToken(claims,username);
    }

    private String crearToken (Map<String, Object> claims, String subject){
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis())) //fecha en la cual fue usado el token
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 *60 *10))
                .signWith(SignatureAlgorithm.HS256,secret).compact();
    }

    public Boolean validaToken(String token, UserDetails userDetails){
        final String username = extraerUsuarioNombre(token);
        return (username.equals(userDetails.getUsername()) && tokenCaducado(token));
    }


}
