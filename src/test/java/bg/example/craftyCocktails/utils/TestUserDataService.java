package bg.example.craftyCocktails.utils;

import bg.example.craftyCocktails.model.user.CustomUserDetails;
import java.util.List;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class TestUserDataService implements UserDetailsService {
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    if (!username.equals("admin")) {
      return new CustomUserDetails(1L,
          "user",
          "User Userov",
          "12345",
          List.of(new SimpleGrantedAuthority("ROLE_USER")),
          true);
    }

    return new CustomUserDetails(1L,
        "admin",
        "Admin Adminov",
        "12345",
        List.of(new SimpleGrantedAuthority("ROLE_ADMIN")),
        true);
  }
}
