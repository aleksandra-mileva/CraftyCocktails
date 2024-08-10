package bg.example.craftyCocktails.model.entity;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "secure_tokens")
public class SecureTokenEntity extends BaseEntity {

  @Column(unique = true)
  private String token;
  @CreationTimestamp
  @Column(updatable = false)
  private Timestamp timestamp;
  @Column(updatable = false)
  @Basic(optional = false)
  private LocalDateTime expireAt;
  @ManyToOne
  private UserEntity user;

  public SecureTokenEntity() {
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public Timestamp getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(Timestamp timestamp) {
    this.timestamp = timestamp;
  }

  public LocalDateTime getExpireAt() {
    return expireAt;
  }

  public void setExpireAt(LocalDateTime expireAt) {
    this.expireAt = expireAt;
  }

  public boolean isExpired() {
    return expireAt.isBefore(LocalDateTime.now());
  }

  public UserEntity getUser() {
    return user;
  }

  public void setUser(UserEntity user) {
    this.user = user;
  }
}
