package bg.example.craftyCocktails.service;

import bg.example.craftyCocktails.repository.RoleRepository;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

  private final RoleRepository roleRepository;

  public RoleService(RoleRepository repository) {
    this.roleRepository = repository;
  }
}
