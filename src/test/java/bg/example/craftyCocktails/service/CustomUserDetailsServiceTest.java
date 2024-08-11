package bg.example.craftyCocktails.service;

import bg.example.craftyCocktails.model.entity.RoleEntity;
import bg.example.craftyCocktails.model.entity.UserEntity;
import bg.example.craftyCocktails.model.entity.enums.RoleNameEnum;
import bg.example.craftyCocktails.model.user.CustomUserDetails;
import bg.example.craftyCocktails.repository.UserRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@ExtendWith(MockitoExtension.class)
class CustomUserDetailsServiceTest {

  private UserEntity testUser;
  private RoleEntity adminRole, userRole;

  private CustomUserDetailsService serviceToTest;

  @Mock
  private UserRepository mockUserRepository;

  @BeforeEach
  void init() {
    serviceToTest = new CustomUserDetailsService(mockUserRepository);

    adminRole = new RoleEntity();
    adminRole.setRole(RoleNameEnum.ADMIN);

    userRole = new RoleEntity();
    userRole.setRole(RoleNameEnum.USER);

    testUser = new UserEntity();
    testUser.setUsername("alex");
    testUser.setFirstName("Aleksandra");
    testUser.setLastName("Mileva");
    testUser.setEmail("aleksandramileva96@gmail.com");
    testUser.setPassword("12345");
    testUser.setRoles(List.of(adminRole, userRole));
  }

  @Test
  void testUserNotFound() {
    Assertions.assertThrows(
        UsernameNotFoundException.class,
        () -> serviceToTest.loadUserByUsername("invalid_username")
    );
  }

  @Test
  void testUserFound() {
    Mockito.when(mockUserRepository.findByUsername(testUser.getUsername())).
        thenReturn(Optional.of(testUser));

    CustomUserDetails userDetails = (CustomUserDetails) serviceToTest.loadUserByUsername(testUser.getUsername());

    Assertions.assertEquals(userDetails.getUsername(), testUser.getUsername());
    Assertions.assertEquals(userDetails.getFullName(), testUser.getFirstName() + " " + testUser.getLastName());
    Assertions.assertEquals(userDetails.getId(), testUser.getId());
    Assertions.assertEquals(userDetails.getPassword(), testUser.getPassword());
    Assertions.assertEquals(2, userDetails.getAuthorities().size());

    String expectedRoles = "ROLE_ADMIN, ROLE_USER";
    String actualRoles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(
        Collectors.joining(", "));

    Assertions.assertEquals(expectedRoles, actualRoles);
  }
}