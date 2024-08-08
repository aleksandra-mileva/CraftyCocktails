package bg.example.craftyCocktails.model.entity;

import bg.example.craftyCocktails.model.entity.enums.RoleNameEnum;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

@Entity
@Table(name = "roles")
public class RoleEntity extends BaseEntity {

  @Enumerated(EnumType.STRING)
  private RoleNameEnum role;

  public RoleEntity() {
  }

  public RoleNameEnum getRole() {
    return role;
  }

  public RoleEntity setRole(RoleNameEnum role) {
    this.role = role;
    return this;
  }
}
