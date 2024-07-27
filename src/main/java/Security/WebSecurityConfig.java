package Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
	
	@Bean
	public SecurityFilterChain appsecurity(HttpSecurity http) throws Exception{
		
		http.cors().and()
		.csrf().disable()                  //Diables CORS/CSRF Above my paygrade
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)//AS A REST API SHOULD BE STATELESS
		.and()
		.formLogin().disable()  //Disables Default Login Form
		.securityMatcher("/api/**") //Blocks API requests from unauthed users
		.authorizeHttpRequests(registry -> registry
				.requestMatchers("/").permitAll()
				.requestMatchers("/auth/login").permitAll()//Allows any requests with default root like login/test
				.anyRequest().authenticated()
				);  //Requires anything else to be authed
		
		
		return http.build(); //Returns the newly built Filterchain
		
	}
	
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000")
                .allowedOrigins("http://localhost:5173")// Allow requests from your React frontend
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true);
			}
		};
	}


}
