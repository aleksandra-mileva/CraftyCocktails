package bg.example.craftyCocktails.service;

import bg.example.craftyCocktails.model.dto.CommentServiceModel;
import bg.example.craftyCocktails.model.entity.CocktailEntity;
import bg.example.craftyCocktails.model.entity.CommentEntity;
import bg.example.craftyCocktails.model.entity.UserEntity;
import bg.example.craftyCocktails.model.entity.enums.RoleNameEnum;
import bg.example.craftyCocktails.model.view.CommentViewModel;
import bg.example.craftyCocktails.repository.CocktailRepository;
import bg.example.craftyCocktails.repository.CommentRepository;
import bg.example.craftyCocktails.repository.UserRepository;
import bg.example.craftyCocktails.web.exception.ObjectNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

  private final CocktailRepository cocktailRepository;
  private final UserRepository userRepository;
  private final CommentRepository commentRepository;

  public CommentService(
      CocktailRepository cocktailRepository,
      UserRepository userRepository,
      CommentRepository commentRepository
  ) {
    this.cocktailRepository = cocktailRepository;
    this.userRepository = userRepository;
    this.commentRepository = commentRepository;
  }

  @Transactional
  public List<CommentViewModel> getComments(Long cocktailId, String principalName) {
    Optional<CocktailEntity> cocktailOptional = this.cocktailRepository.findById(cocktailId);

    if (cocktailOptional.isEmpty()) {
      throw new ObjectNotFoundException("Cocktail with id " + cocktailId + " was not found!");
    }
    return cocktailOptional.get()
        .getComments().stream()
        .map(comment -> mapAsComment(comment, principalName))
        .collect(Collectors.toList());
  }

  private CommentViewModel mapAsComment(CommentEntity commentEntity, String principalName) {
    CommentViewModel commentViewModel = new CommentViewModel();

    commentViewModel.setCommentId(commentEntity.getId())
        .setMessage(commentEntity.getText())
        .setUser(commentEntity.getAuthor().getFirstName() + " " + commentEntity.getAuthor()
            .getLastName())
        .setCreated(commentEntity.getCreated())
        .setCanDelete(
            principalName != null && isAuthorOrAdmin(principalName, commentEntity.getId()));

    return commentViewModel;
  }

  public CommentViewModel createComment(CommentServiceModel commentServiceModel) {
    CocktailEntity cocktail = cocktailRepository.findById(commentServiceModel.getCocktailId())
        .orElseThrow(() -> new UnsupportedOperationException(
            "Cocktail with id " + commentServiceModel.getCocktailId() + " not found!"));

    UserEntity creator = userRepository.findByUsername(commentServiceModel.getCreator())
        .orElseThrow(() -> new UnsupportedOperationException(
            "User with username " + commentServiceModel.getCreator() + " not found!"));

    CommentEntity newComment = new CommentEntity();
    newComment.setText(commentServiceModel.getMessage());
    newComment.setCreated(LocalDateTime.now());
    newComment.setAuthor(creator);
    newComment.setCocktail(cocktail);

    CommentEntity savedComment = commentRepository.save(newComment);
    return mapAsComment(savedComment, creator.getUsername());
  }

  public CommentViewModel deleteComment(Long commentId, String principalName) {
    CommentEntity deleted = commentRepository.findById(commentId)
        .orElseThrow(
            () -> new ObjectNotFoundException("Comment with id " + commentId + " not found!"));

    commentRepository.deleteById(commentId);
    return mapAsComment(deleted, principalName);
  }

  public boolean isAuthorOrAdmin(String userName, Long commentId) {
    boolean isOwner = commentRepository.
        findById(commentId).
        filter(c -> c.getAuthor().getUsername().equals(userName)).
        isPresent();

    if (isOwner) {
      return true;
    }

    return userRepository
        .findByUsername(userName)
        .filter(this::isAdmin)
        .isPresent();
  }

  private boolean isAdmin(UserEntity user) {
    return user.getRoles().
        stream().
        anyMatch(r -> r.getRole() == RoleNameEnum.ADMIN);
  }
}
