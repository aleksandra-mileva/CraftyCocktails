package bg.example.craftyCocktails.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionsHandler {

  @ExceptionHandler(InvalidFileException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public String handleInvalidFileException(InvalidFileException ex, Model model) {

    ex.printStackTrace();
    model.addAttribute("errorMessage", ex.getMessage());
    return "error/invalidPictureUploadError";
  }

  @ExceptionHandler(InvalidTokenException.class)
  public String handleInvalidTokenException(InvalidTokenException ex, Model model) {

    ex.printStackTrace();
    model.addAttribute("message", ex.getMessage());
    return "message";
  }

  @ExceptionHandler(ObjectNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public String handleObjectNotFoundException(ObjectNotFoundException ex, Model model) {

    ex.printStackTrace();
    model.addAttribute("errorMessage", ex.getMessage());
    return "error/objectNotFoundError";
  }
}
