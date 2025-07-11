package api.maxchat.maxchat.utils;

import api.maxchat.maxchat.dto.JwtDTO;
import api.maxchat.maxchat.enums.ProfileRole;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.*;
import java.util.stream.Collectors;

public class JwtUtil {
    private static final int tokenLiveTime = 1000 * 3600 * 24; // 1-day
    private static final String secretKey = "veryLongSecretmazgillattayevlasharaaxmojonjinnijonsurbetbekkiydirhonuxlatdibekloxovdangasabekochkozjonduxovmashaynikmaydagapchishularnioqiganbolsangizgapyoqaniqsizmazgi";

    public static String encode(Integer id, String username, List<ProfileRole> roleList) {
        /*ROLE_USER, ROLE_ADMIN*/
        String strRoles = roleList.stream()
                .map(Enum::name)
                .collect(Collectors.joining(","));

        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", strRoles);
        claims.put("id", String.valueOf(id));


        return Jwts
                .builder()
                .claims(claims)
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + tokenLiveTime))
                .signWith(getSignInKey())
                .compact();
    }

    public static JwtDTO decode(String token) {
        Claims claims = Jwts
                .parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

        String username = claims.getSubject();

        Object idObj = claims.get("id");
        Integer id = null;
        if (idObj == null) {
            throw new RuntimeException("JWT token ichida 'id' mavjud emas");
        }
        try {
            id = Integer.valueOf(idObj.toString());
        } catch (NumberFormatException e) {
            throw new RuntimeException("JWT token ichidagi 'id' butun son formatida emas: " + idObj, e);
        }

        String strRole = (String) claims.get("roles");
        if (strRole == null || strRole.isBlank()) {
            throw new RuntimeException("JWT token ichida 'roles' mavjud emas yoki bo‘sh");
        }

        List<ProfileRole> roleLis = Arrays.stream(strRole.split(","))
                .map(ProfileRole::valueOf)
                .toList();

        return new JwtDTO(id, username, roleLis);

    }

    public static String encode(Integer id) {
        return Jwts
                .builder()
                .subject(String.valueOf(id))
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + tokenLiveTime))
                .signWith(getSignInKey())
                .compact();
    }

    public static Integer decodeRegVerToken(String token) {
        Claims claims = Jwts
                .parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return Integer.valueOf(claims.getSubject());

    }

    private static SecretKey getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
