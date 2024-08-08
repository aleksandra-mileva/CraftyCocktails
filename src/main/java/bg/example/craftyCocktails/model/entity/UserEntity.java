package bg.example.craftyCocktails.model.entity;

import java.sql.Timestamp;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "users")
public class UserEntity extends BaseEntity {

  @Column(nullable = false, unique = true)
  private String username;
  @Column(nullable = false)
  private String firstName;
  @Column(nullable = false)
  private String lastName;
  @Column(nullable = false)
  private String password;
  @Column(nullable = false, unique = true)
  private String email;
  @ManyToMany(fetch = FetchType.EAGER)
  private List<RoleEntity> roles;
  @OneToMany(mappedBy = "author")
  private List<CocktailEntity> addedCocktails;
  @Column
  private boolean accountVerified;
  @CreationTimestamp
  @Column(updatable = false)
  private Timestamp createdOn;

  @ManyToMany()
  @JoinTable(
      name = "user_favorite_cocktails",
      joinColumns = @JoinColumn(name = "user_id"),
      inverseJoinColumns = @JoinColumn(name = "cocktail_id"))
  private List<CocktailEntity> favorites;

  @OneToMany(mappedBy = "author")
  private List<PictureEntity> addedPictures;

  public List<CocktailEntity> getAddedCocktails() {
    return addedCocktails;
  }

  public UserEntity setAddedCocktails(List<CocktailEntity> addedCocktails) {
    this.addedCocktails = addedCocktails;
    return this;
  }

  public List<CocktailEntity> getFavorites() {
    return favorites;
  }

  public UserEntity setFavorites(List<CocktailEntity> favorites) {
    this.favorites = favorites;
    return this;
  }

  public List<PictureEntity> getAddedPictures() {
    return addedPictures;
  }

  public UserEntity setAddedPictures(List<PictureEntity> addedPictures) {
    this.addedPictures = addedPictures;
    return this;
  }

  public String getUsername() {
    return username;
  }

  public UserEntity setUsername(String username) {
    this.username = username;
    return this;
  }

  public String getFirstName() {
    return firstName;
  }

  public UserEntity setFirstName(String firstName) {
    this.firstName = firstName;
    return this;
  }

  public String getLastName() {
    return lastName;
  }

  public UserEntity setLastName(String lastName) {
    this.lastName = lastName;
    return this;
  }

  public String getPassword() {
    return password;
  }

  public UserEntity setPassword(String password) {
    this.password = password;
    return this;
  }

  public String getEmail() {
    return email;
  }

  public UserEntity setEmail(String email) {
    this.email = email;
    return this;
  }

  public List<RoleEntity> getRoles() {
    return roles;
  }

  public UserEntity setRoles(List<RoleEntity> roles) {
    this.roles = roles;
    return this;
  }

  public boolean isAccountVerified() {
    return accountVerified;
  }

  public UserEntity setAccountVerified(boolean accountVerified) {
    this.accountVerified = accountVerified;
    return this;
  }

  public Timestamp getCreatedOn() {
    return createdOn;
  }

  public UserEntity setCreatedOn(Timestamp createdOn) {
    this.createdOn = createdOn;
    return this;
  }
}
