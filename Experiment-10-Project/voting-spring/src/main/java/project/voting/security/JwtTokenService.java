package project.voting.security;

import project.voting.entity.Voter;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtTokenService {

    private static final String SECRET_KEY = System.getenv("JWT_SECRET") != null ? System.getenv("JWT_SECRET") : "defaultsecretkeyfordemothatislongenoughforjwtsecurityrequirements";
    static {
        // No check needed, using default if not set
    }
    private static final SecretKey key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    private static final long EXPIRATION_TIME_MS = 3600000; // 1 hour

    // Generate JWT Token
    public String generateToken(Voter voter) {
        return Jwts.builder()
                .setSubject(voter.getEmail())
                .claim("id", voter.getId()) // Include id attribute in token
                .claim("isAdmin", voter.getIsAdmin()) // Include isAdmin attribute in token
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME_MS))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    // Extract Voter Information from Token
    public Voter getVoterFromToken(String token) {
        try {
            var claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            String email = claims.getSubject();
            Integer id = claims.get("id", Integer.class);
            boolean isAdmin = claims.get("isAdmin", Boolean.class); // Extract isAdmin attribute

            // Construct and return a Voter object with extracted details
            Voter voter = new Voter();
            voter.setId(id == null ? 0 : id);
            voter.setEmail(email);
            voter.setIsAdmin(isAdmin);
            return voter;
        } catch (Exception e) {
            // Log the exception (optional)
            System.err.println("Invalid token: " + e.getMessage());
            return null;  // Return null if the token is invalid or parsing fails
        }
    }

    // Validate the token
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            // Log the exception (optional)
            System.err.println("Token validation failed: " + e.getMessage());
            return false;
        }
    }
}
