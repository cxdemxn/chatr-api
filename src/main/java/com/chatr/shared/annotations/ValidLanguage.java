package com.chatr.shared.annotations;

import com.chatr.shared.validators.ValidLanguageValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target(ElementType.FIELD) // tells java where the annotation can be used
@Retention(RetentionPolicy.RUNTIME) // means this annotation is kept at runtime and not discarded by the compiler
@Constraint(validatedBy = ValidLanguageValidator.class) // links to my custom validator class
@Documented
public @interface ValidLanguage {
    String message() default "Invalid language code"; // default error message when validation fails
    Class<?>[] groups() default {}; // allows me organize validations into different logical groups
    Class<? extends Payload>[] payload() default {}; // allows attaching metadata or severity levels to the constraint
}
