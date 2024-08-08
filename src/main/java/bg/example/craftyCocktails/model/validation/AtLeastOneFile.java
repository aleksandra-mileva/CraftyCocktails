package bg.example.craftyCocktails.model.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = AtLeastOneFileValidator.class)
public @interface AtLeastOneFile {

  String message() default "At least one file must be chosen!";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
