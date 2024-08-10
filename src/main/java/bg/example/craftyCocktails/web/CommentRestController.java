package bg.example.craftyCocktails.web;

import bg.example.craftyCocktails.model.dto.AddCommentDto;
import bg.example.craftyCocktails.model.dto.CommentServiceModel;
import bg.example.craftyCocktails.model.validation.ApiError;
import bg.example.craftyCocktails.model.view.CommentViewModel;
import bg.example.craftyCocktails.service.CommentService;
import java.net.URI;
import java.util.List;
import javax.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommentRestController {

  private final CommentService commentService;
  private final ModelMapper modelMapper;

  public CommentRestController(CommentService commentService, ModelMapper modelMapper) {
    this.commentService = commentService;
    this.modelMapper = modelMapper;
  }

  @GetMapping("/api/{cocktailId}/comments")
  public ResponseEntity<List<CommentViewModel>> getComments(
      @PathVariable Long cocktailId,
      @AuthenticationPrincipal UserDetails principal
  ) {
    return ResponseEntity.ok(commentService.getComments(
        cocktailId, principal != null ? principal.getUsername() : null));
  }

  @PostMapping("/api/{cocktailId}/comments")
  public ResponseEntity<CommentViewModel> newComment(
      @AuthenticationPrincipal UserDetails principal,
      @PathVariable Long cocktailId,
      @RequestBody @Valid AddCommentDto addCommentDto) {

    if (principal == null) {
      return ResponseEntity.status(403).build();
    }

    CommentServiceModel commentServiceModel = modelMapper.map(addCommentDto,
        CommentServiceModel.class);
    commentServiceModel.setCreator(principal.getUsername());
    commentServiceModel.setCocktailId(cocktailId);

    CommentViewModel newComment = commentService.createComment(commentServiceModel);
    URI locationOfNewComment =
        URI.create(String.format("/api/%s/comments/%s", cocktailId, newComment.getCommentId()));

    return ResponseEntity
        .created(locationOfNewComment)
        .body(newComment);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<ApiError> onValidationFailure(MethodArgumentNotValidException exc) {

    ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST);
    exc.getFieldErrors().forEach(fe -> apiError.addFieldWithError(fe.getField()));

    return ResponseEntity.badRequest().body(apiError);
  }

  @DeleteMapping("/api/{cocktailId}/comments/{commentId}")
  public ResponseEntity<CommentViewModel> deleteComment(
      @PathVariable("commentId") Long commentId, @PathVariable("cocktailId") Long cocktailId,
      @AuthenticationPrincipal UserDetails principal
  ) {

    if (principal != null && commentService.isAuthorOrAdmin(principal.getUsername(), commentId)) {
      CommentViewModel deleted = commentService.deleteComment(commentId, principal.getUsername());
      return ResponseEntity.ok(deleted);
    }
    return ResponseEntity.status(403).build();
  }
}
