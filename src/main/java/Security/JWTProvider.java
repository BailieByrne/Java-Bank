package Security;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import Account.AccountException;
import Users.User;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JWTProvider {

    private static final Logger log = LogManager.getLogger(JWTProvider.class);
    private static final long TOKEN_EXPIRATION_DAYS = 1L;

    private final JWTProperties properties;
    private final List<String> loggedInUsers = new CopyOnWriteArrayList<>();
    private final List<String> validTokens = new CopyOnWriteArrayList<>();

    public String issue(String keeper, User user) {
        try {
            if (keeper == null || user == null) {
                throw new IllegalArgumentException("Keeper or user cannot be null");
            }

            String token = createToken(keeper, user);

            if (loggedInUsers.contains(keeper)) {
                int index = loggedInUsers.indexOf(keeper);
                validTokens.set(index, token);
                log.warn("REVOKED EXISTING JWT FOR {}", keeper);
            } else {
                loggedInUsers.add(keeper);
                validTokens.add(token);
            }

            return token;
        } catch (Exception e) {
            log.error("Error issuing token for {}: {}", keeper, e.getMessage(), e);
            throw new AccountException("Error issuing token");
        }
    }

    private String createToken(String keeper, User user) {
        return JWT.create()
                .withSubject(keeper)
                .withExpiresAt(Instant.now().plus(Duration.of(TOKEN_EXPIRATION_DAYS, ChronoUnit.DAYS)))
                .withClaim("UUID", user.id())
                .withClaim("auth", user.Roles())
                .sign(Algorithm.HMAC256(properties.getSecretKey()));
    }

    private Boolean validateToken(JWT token) {
    	if(validTokens.contains(token)) {
    		return true;
    	}
    	return false;
    }
}

