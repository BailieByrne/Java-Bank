package Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
	
	@Bean
	public SecurityFilterChain appsecurity(HttpSecurity http) throws Exception{
		
		http.cors().disable()
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

}
