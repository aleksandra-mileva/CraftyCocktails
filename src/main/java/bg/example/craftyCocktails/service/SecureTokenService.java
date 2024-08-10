package bg.example.craftyCocktails.service;

import bg.example.craftyCocktails.model.entity.SecureTokenEntity;
import bg.example.craftyCocktails.model.entity.UserEntity;
import bg.example.craftyCocktails.repository.SecureTokenRepository;
import java.time.LocalDateTime;
import java.util.List;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.security.crypto.keygen.BytesKeyGenerator;
import org.springframework.security.crypto.keygen.KeyGenerators;
import org.springframework.stereotype.Service;


@Service
public class SecureTokenService {

  private static final BytesKeyGenerator DEFAULT_TOKEN_GENERATOR = KeyGenerators.secureRandom(15);

  private final SecureTokenRepository secureTokenRepository;

  public SecureTokenService(SecureTokenRepository secureTokenRepository) {
    this.secureTokenRepository = secureTokenRepository;
  }

  public SecureTokenEntity createSecureToken(UserEntity user) {
    String tokenValue = new String(
        Base64.encodeBase64URLSafeString(DEFAULT_TOKEN_GENERATOR.generateKey()));
    SecureTokenEntity secureTokenEntity = new SecureTokenEntity();
    secureTokenEntity.setToken(tokenValue);
    secureTokenEntity.setExpireAt(LocalDateTime.now().plusMinutes(30));
    secureTokenEntity.setUser(user);
    return this.secureTokenRepository.save(secureTokenEntity);
  }

  public void cleanUpSecureTokens() {
    List<SecureTokenEntity> tokensToDelete = secureTokenRepository.findAllByExpireAtBefore(
        LocalDateTime.now());
    secureTokenRepository.deleteAll(tokensToDelete);
  }
}
