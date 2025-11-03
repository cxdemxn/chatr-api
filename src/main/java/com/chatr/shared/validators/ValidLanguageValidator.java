package com.chatr.shared.validators;

import com.chatr.shared.annotations.ValidLanguage;
import com.chatr.shared.enums.PreferredLanguage;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidLanguageValidator implements ConstraintValidator<ValidLanguage, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
//        context allows customization of validation messages or reporting
        if (value == null)
            return true;

        return PreferredLanguage.isValid(value);
    }
}
