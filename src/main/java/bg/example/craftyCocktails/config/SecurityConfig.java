package bg.example.craftyCocktails.config;

import bg.example.craftyCocktails.model.entity.enums.RoleNameEnum;
import bg.example.craftyCocktails.repository.UserRepository;
import bg.example.craftyCocktails.service.CustomUserDetailsService;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new Pbkdf2PasswordEncoder();
  }

  @Bean
  public UserDetailsService userDetailsService(UserRepository userRepository) {
    return new CustomUserDetailsService(userRepository);
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.authorizeRequests()
        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
        .antMatchers("/", "/cocktails/**", "/api/**", "/maintenance/**").permitAll()
        .antMatchers("/users/register/**", "/users/login", "/password/**").anonymous()
        .antMatchers("/users/profile", "/cocktails/add", "/users/profile/**", "/fortunes/fortune").authenticated()
        .antMatchers("/statistics", "/fortunes", "/fortunes/**").hasRole(RoleNameEnum.ADMIN.name())
        .anyRequest()
        .authenticated()
        .and()
        .formLogin()
        .loginPage("/users/login")
        .usernameParameter("username")
        .passwordParameter("password")
        .defaultSuccessUrl("/")
        .failureForwardUrl("/users/login-error")
        .and()
        .logout()
        .logoutUrl("/users/logout")
        .logoutSuccessUrl("/")
        .invalidateHttpSession(true)
        .deleteCookies("JSESSIONID");

    return http.build();
  }
}
