package Web;


import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.DemoApplication;

import Security.LoginRequest;



@Controller
@RequestMapping("")
public class WebContoller {
	public static final Logger log = DemoApplication.log;
	
	@GetMapping("/login")
	public String loginPage(Model model) {
		LoginRequest loginRequest = new LoginRequest(); // Example default value
	    model.addAttribute("loginRequest", loginRequest);
		log.warn("Login");
		return "login";
		}
	
	@GetMapping("/test")
	public String testPage() {
		log.warn("Test");
		return "test";
		}
}
