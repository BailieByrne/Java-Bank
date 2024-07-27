package Security;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
	private final LoginFormHandlerService loginFormHandlerService;
	public static final Logger log = LogManager.getLogger(AuthController.class);
	
	
	@PostMapping(value = "/login",consumes = "application/json")
	public ResponseEntity<String> loginrequestJson(@RequestBody @Validated LoginRequest request) {
		log.info("HANDLING JSON LOGIN");
		return ResponseEntity.ok(loginFormHandlerService.validateCredentials(request));
		
	
	}
	
	@PostMapping(value = "/login",consumes = "application/x-www-form-urlencoded")
	public ResponseEntity<String> loginrequestHTML(@ModelAttribute LoginRequest request) {
		log.info("HANDLING HTML LOGIN");
		return ResponseEntity.ok(loginFormHandlerService.validateCredentials(request));
		
	
	}

}
