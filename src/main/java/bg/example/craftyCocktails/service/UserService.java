package bg.example.craftyCocktails.service;

import bg.example.craftyCocktails.model.dto.UserEditDto;
import bg.example.craftyCocktails.model.dto.UserRegisterDto;
import bg.example.craftyCocktails.model.email.AccountVerificationEmailContext;
import bg.example.craftyCocktails.model.entity.CocktailEntity;
import bg.example.craftyCocktails.model.entity.SecureTokenEntity;
import bg.example.craftyCocktails.model.entity.UserEntity;
import bg.example.craftyCocktails.model.entity.enums.RoleNameEnum;
import bg.example.craftyCocktails.model.view.UserView;
import bg.example.craftyCocktails.repository.CocktailRepository;
import bg.example.craftyCocktails.repository.RoleRepository;
import bg.example.craftyCocktails.repository.SecureTokenRepository;
import bg.example.craftyCocktails.repository.UserRepository;
import bg.example.craftyCocktails.web.exception.InvalidTokenException;
import bg.example.craftyCocktails.web.exception.ObjectNotFoundException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  private final UserRepository userRepository;
  private final ModelMapper modelMapper;
  private final PasswordEncoder passwordEncoder;
  private final RoleRepository roleRepository;
  private final EmailService emailService;
  private final CocktailRepository cocktailRepository;
  private final SecureTokenRepository secureTokenRepository;
  private final SecureTokenService secureTokenService;
  @Value("${site.base.url}")
  private String baseURL;

  public UserService(
      UserRepository userRepository,
      ModelMapper modelMapper,
      PasswordEncoder passwordEncoder,
      RoleRepository roleRepository,
      EmailService emailService,
      CocktailRepository cocktailRepository,
      SecureTokenRepository secureTokenRepository,
      SecureTokenService secureTokenService
  ) {
    this.userRepository = userRepository;
    this.modelMapper = modelMapper;
    this.passwordEncoder = passwordEncoder;
    this.roleRepository = roleRepository;
    this.emailService = emailService;
    this.cocktailRepository = cocktailRepository;
    this.secureTokenRepository = secureTokenRepository;
    this.secureTokenService = secureTokenService;
  }

  public void register(UserRegisterDto userRegisterDto, Locale preferedLocale) {
    UserEntity newUser = modelMapper.map(userRegisterDto, UserEntity.class);
    newUser.setPassword(passwordEncoder.encode(userRegisterDto.getPassword()));

    newUser.setRoles(roleRepository.findAll()
        .stream()
        .filter(r -> r.getRole() == (RoleNameEnum.USER))
        .collect(Collectors.toList()));

    newUser.setAccountVerified(false);
    sendVerificationMail(this.userRepository.save(newUser), preferedLocale);
  }

  public void sendVerificationMail(UserEntity newUser, Locale preferedLocale) {
    AccountVerificationEmailContext emailContext = new AccountVerificationEmailContext();
    SecureTokenEntity token = this.secureTokenService.createSecureToken(newUser);

    emailContext.setToken(token.getToken());
    emailContext.setLocale(preferedLocale);
    emailContext.setBaseUrl(baseURL);
    emailContext.initContext(newUser);

    emailService.sendEmail(emailContext);
  }

  public UserView findById(Long id) {
    return this.userRepository.findById(id)
        .map(userEntity -> modelMapper.map(userEntity, UserView.class))
        .orElseThrow(() -> new ObjectNotFoundException("User with id " + id + " not found!"));
  }

  public void updateUserProfile(UserEditDto userEditDto) {
    UserEntity user = this.userRepository.findById(userEditDto.getId())
        .orElseThrow(() -> new ObjectNotFoundException(
            "User with id " + userEditDto.getId() + " was not found!"));

    user.setFirstName(userEditDto.getFirstName())
        .setLastName(userEditDto.getLastName())
        .setUsername(userEditDto.getUsername())
        .setEmail(userEditDto.getEmail());

    this.userRepository.save(user);
  }

  public UserEditDto getUserEditDetails(Long id) {
    return this.userRepository.findById(id)
        .map(userEntity -> modelMapper.map(userEntity, UserEditDto.class))
        .orElseThrow(() -> new ObjectNotFoundException("User with ID " + id + " not found!"));
  }

  public boolean usernameExists(String username) {
    return this.userRepository.existsByUsername(username);
  }

  public boolean emailExists(String email) {
    return this.userRepository.existsByEmail(email);
  }

  @Transactional
  public boolean addOrRemoveCocktailFromFavorites(String username, Long id) {
    UserEntity user = this.userRepository.findByUsername(username)
        .orElseThrow(() -> new ObjectNotFoundException(
            "User with username " + username + " was not found!"));

    CocktailEntity cocktail = cocktailRepository.findById(id)
        .orElseThrow(() -> new ObjectNotFoundException("Cocktail with id " + id + " not found!"));

    if (!user.getFavorites().contains(cocktail)) {
      user.getFavorites().add(cocktail);
    } else {
      user.getFavorites().remove(cocktail);
    }
    userRepository.save(user);
    return user.getFavorites().contains(cocktail);
  }

  public List<String> getAdminsEmails() {
    return this.userRepository.findByRole(RoleNameEnum.ADMIN).stream().map(UserEntity::getEmail)
        .collect(Collectors.toList());
  }

  public long getCountRegisteredUsers() {
    return this.userRepository.count();
  }

  public UserEntity findByEmail(String email) {
    return this.userRepository.findByEmail(email)
        .orElseThrow(() -> new ObjectNotFoundException("User with email " + email + "not found!"));
  }

  public void verifyAccount(String token) {
    Optional<SecureTokenEntity> tokenOpt = secureTokenRepository.findByToken(token);

    if (tokenOpt.isEmpty() || tokenOpt.get().isExpired() || tokenOpt.get().getUser() == null) {
      throw new InvalidTokenException("Token is invalid.");
    }

    SecureTokenEntity secureToken = tokenOpt.get();
    UserEntity user = secureToken.getUser();

    user.setAccountVerified(true);
    userRepository.save(user);
    secureTokenRepository.delete(secureToken);
  }

  public boolean notVerifiedProfile(String username) {
    UserEntity user = userRepository.findByUsername(username).orElse(null);
    return user != null && !user.isAccountVerified();
  }

  public UserEntity findByUsername(String username) {
    return userRepository.findByUsername(username)
        .orElseThrow(
            () -> new ObjectNotFoundException("User with username " + username + "not found!"));
  }

  public void cleanUpNotVerifiedUsers() {
    List<UserEntity> usersToDelete = userRepository
        .findAllByAccountVerifiedEqualsAndCreatedOnBefore(false,
            Timestamp.valueOf(LocalDateTime.now().minusMinutes(25)));

    userRepository.deleteAll(usersToDelete);
  }
}
