package com.chatr.shared.validators;

import com.chatr.shared.annotations.NoSpace;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NoSpaceValidator implements ConstraintValidator<NoSpace, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value == null || !value.contains(" ");
    }
}
