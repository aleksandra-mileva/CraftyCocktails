package bg.example.craftyCocktails.model.validation;

import java.util.List;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;

public class AtLeastOneFileValidator implements
    ConstraintValidator<AtLeastOneFile, List<MultipartFile>> {

  @Override
  public boolean isValid(List<MultipartFile> files,
      ConstraintValidatorContext constraintValidatorContext) {
    return files != null && !files.isEmpty() && files.stream().anyMatch(file -> !file.isEmpty());
  }
}
