package Security;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;


@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class RegisterController {
	private final RegisterFormHandlerService registerFormHandlerService;
	public static final Logger log = LogManager.getLogger(AuthController.class);
	
	@PostMapping(value = "/register",consumes = "application/x-www-form-urlencoded")
	public String registerrequestHTMl(@ModelAttribute RegisterRequest request,Model model) {
		log.info("HANDLING HTML REGISTRATION");
		
		return registerFormHandlerService.createUser(request,model);
	}
}
