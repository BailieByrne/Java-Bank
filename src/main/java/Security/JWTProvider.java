package Security;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.List;

import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import Users.User;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JWTProvider {
	
	private final jwtProperties properties;
	
	public String issue(String keeper, User user) {
		
		
		return JWT.create()
				.withSubject(String.valueOf(keeper)) //Extract Subject so keeper in this case
				.withExpiresAt(Instant.now().plus(Duration.of(1, ChronoUnit.DAYS)))
				.withClaim("UUID", user.id())//Set expiration of token
				.withClaim("auth", user.Roles()) //Extract Roles
				.sign(Algorithm.HMAC256(properties.getSecretKey())); //Sign Using HMAC256 envryption on secretkey
		
	}

}
