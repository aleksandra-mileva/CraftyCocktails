package bg.example.craftyCocktails.web;

import bg.example.craftyCocktails.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FavoritesRestController {

  private final UserService userService;

  public FavoritesRestController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping("/api/cocktails/{id}/addOrRemoveFromFavorites")
  @ResponseBody
  public ResponseEntity<Boolean> addOrRemoveFromFavorites(
      @PathVariable Long id,
      @AuthenticationPrincipal UserDetails principal
  ) {
    if (principal != null) {
      boolean isFavorite = userService.addOrRemoveCocktailFromFavorites(principal.getUsername(),
          id);
      return ResponseEntity.ok(isFavorite);
    }
    return ResponseEntity.status(403).build();
  }
}
