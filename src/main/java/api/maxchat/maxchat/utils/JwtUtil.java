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

        /*Yuqoridagi yangi versioni*/
        /*Map<Srting> strList = new LinkedList<>();
        for(ProfileRole role : roleList){
            strList.add(role.name());
        }
        String roleString = String.join(",", strList);*/

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
        Integer id = Integer.valueOf((String) claims.get("id"));
        String strRole = (String) claims.get("roles");
        //"ROLE_USER, ROLE_ADMIN"
        List<ProfileRole> roleLis = new ArrayList<>();
        if (strRole != null && !strRole.isEmpty()) {
            roleLis = Arrays.stream(strRole.split(","))
                    .map(ProfileRole::valueOf)
                    .toList();
        }



        /*ROLE_USER, ROLE_ADMIN*/
        /*String[] roleArray = strRole.split(",");
        List<ProfileRole> roleLis1 = new ArrayList<>();
        for(String role : roleArray){
            roleLis1.add(ProfileRole.valueOf(role));
        }*/

        /*Yuqoridagini Map versiyasi */
        /*List<ProfileRole> roleLis = Arrays.stream(strRole.split(","))
                .map(item -> ProfileRole.valueOf(item))
                .collect(Collectors.toList());*/

        /*Yuqoridagini New updated versiyasi */
//        List<ProfileRole> roleLis = Arrays.stream(strRole.split(","))
//                .map(ProfileRole::valueOf)
//                .toList();

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
