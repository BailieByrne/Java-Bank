package Security;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
	public static final Logger log = com.example.demo.DemoApplication.log;
	
	private final JWTProvider jwtProvider;
	
	
	@PostMapping(value = "/login",consumes = "application/json")
	public LoginResponse loginrequestJson(@RequestBody @Validated LoginRequest request) {
		log.info("HANDLING JSON LOGIN");

		return loginFormHandlerService.validateCredentials(request);
		
	
	}
	
	@PostMapping(value = "/login",consumes = "application/x-www-form-urlencoded")
	public LoginResponse loginrequestHTML(@ModelAttribute LoginRequest request,Model model) {
		log.info("HANDLING HTML LOGIN");
		model.addAttribute("loginRequest", request);

		return loginFormHandlerService.validateCredentials(request);
		
	
	}

}
