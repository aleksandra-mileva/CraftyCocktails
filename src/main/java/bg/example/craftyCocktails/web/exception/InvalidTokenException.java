package bg.example.craftyCocktails.web.exception;

public class InvalidTokenException extends RuntimeException {

  public InvalidTokenException() {
  }

  public InvalidTokenException(String message) {
    super(message);
  }
}
