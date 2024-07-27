package Security;



import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;

import com.auth0.jwt.interfaces.DecodedJWT;

import Account.AccountException;
import Users.CrudUserRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class LoginFormHandlerService {
	
	private final JWTProvider jwtProvider;
	private final CrudUserRepository crudUserRepository;
	BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
	public static final Logger log = LogManager.getLogger(LoginFormHandlerService.class);
	
	public String validateCredentials(@RequestBody @Validated LoginRequest request) {
		
		var user = crudUserRepository.findByUsername(request.getKeeper());
		
		if(user == null) {	
			throw new AccountException("Account Does Not Exist");
		}else if (encoder.matches(request.getPassword(), user.password())) {
			
			var token = jwtProvider.issue(request.getKeeper(), user);
			log.info("JWT ISSUED TO "+request.getKeeper());
			return token;
		}else {
			throw new AccountException("Invalid Credentials");
		}
	}
}
