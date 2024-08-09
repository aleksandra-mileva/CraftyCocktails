package bg.example.craftyCocktails.service;

import bg.example.craftyCocktails.model.email.ForgotPasswordEmailContext;
import bg.example.craftyCocktails.model.entity.SecureTokenEntity;
import bg.example.craftyCocktails.model.entity.UserEntity;
import bg.example.craftyCocktails.repository.SecureTokenRepository;
import bg.example.craftyCocktails.repository.UserRepository;
import bg.example.craftyCocktails.web.exception.InvalidTokenException;
import java.util.Locale;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ForgottenPasswordService {

  private final UserService userService;
  private final SecureTokenService secureTokenService;
  private final SecureTokenRepository secureTokenRepository;
  private final EmailService emailService;
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  @Value("${site.base.url}")
  private String baseURL;

  public ForgottenPasswordService(
      UserService userService,
      SecureTokenService secureTokenService,
      SecureTokenRepository secureTokenRepository,
      EmailService emailService,
      UserRepository userRepository,
      PasswordEncoder passwordEncoder
  ) {
    this.userService = userService;
    this.secureTokenService = secureTokenService;
    this.secureTokenRepository = secureTokenRepository;
    this.emailService = emailService;
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

  public void sendResetPasswordEmail(String email, Locale locale) {
    UserEntity user = this.userService.findByEmail(email);
    SecureTokenEntity secureToken = secureTokenService.createSecureToken(user);

    ForgotPasswordEmailContext emailContext = new ForgotPasswordEmailContext();
    emailContext.setLocale(locale);
    emailContext.setToken(secureToken.getToken());
    emailContext.setBaseUrl(baseURL);
    emailContext.initContext(user);
    emailService.sendEmail(emailContext);
  }

  public void updatePassword(String password, String token) {

    if (invalidToken(token)) {
      throw new InvalidTokenException("This token is invalid.");
    }

    SecureTokenEntity secureToken = secureTokenRepository.findByToken(token).get();
    UserEntity user = secureToken.getUser();

    user.setPassword(passwordEncoder.encode(password));
    userRepository.save(user);
    secureTokenRepository.delete(secureToken);
  }

  public boolean invalidToken(String token) {
    Optional<SecureTokenEntity> tokenOpt = secureTokenRepository.findByToken(token);
    return tokenOpt.isEmpty() || tokenOpt.get().isExpired() || tokenOpt.get().getUser() == null;
  }
}
