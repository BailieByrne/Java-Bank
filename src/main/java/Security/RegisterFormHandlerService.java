package Security;



import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import Users.CrudUserRepository;
import Users.UserMapper;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RegisterFormHandlerService {
    private final CrudUserRepository crudUserRepository;
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    public static final Logger log = LogManager.getLogger(RegisterFormHandlerService.class);

    public String createUser(@Validated RegisterRequest request, Model model) {
        var user = crudUserRepository.findByUsername(request.getKeeper());
        if (user != null) {
            log.info("Username Already Exists");
            model.addAttribute("error", "Username Already Exists");
            return "redirect:/login?error=User+Already+Exists";
        }
        if (request.getKeeper().isBlank() || request.getPassword().isBlank()) {
            log.info("Invalid Credentials");
            model.addAttribute("error", "Invalid Credentials");
            return "redirect:/login?error=Invalid+credentials";
        } else {
            crudUserRepository.save(UserMapper.mapToUser(request));
            log.info("User Created");
            model.addAttribute("status", "User Created");
            return "redirect:/login?status=Success";
        }
    }
}