package Security;

import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;

import Account.AccountException;
import Users.CrudUserRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class LoginFormHandlerService {
	
	private final JWTProvider jwtProvider;
	private final CrudUserRepository crudUserRepository;
	BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
	public static final Logger log = com.example.demo.DemoApplication.log;
	
	public LoginResponse validateCredentials(@RequestBody @Validated LoginRequest request) {
		
		var user = crudUserRepository.findByUsername(request.getKeeper());
		
		if(user == null) {	
			throw new AccountException("Account Does Not Exist");
		}else if (encoder.matches(request.getPassword(), user.password())) {
			
			var token = jwtProvider.issue(request.getKeeper(), user);
			log.info("JWT ISSUED TO "+request.getKeeper());

			return LoginResponse.builder().
					accessToken(token)
					.build();
		}else {
			throw new AccountException("Invalid Credentials");
		}
	}
}
