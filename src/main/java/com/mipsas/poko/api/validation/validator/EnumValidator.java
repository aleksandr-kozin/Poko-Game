package com.mipsas.poko.api.validation.validator;

import com.mipsas.poko.api.validation.Enum;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import static org.apache.commons.lang3.StringUtils.isBlank;

public class EnumValidator implements ConstraintValidator<Enum, String> {
    private List<String> acceptedValues;

    @Override
    public void initialize(Enum annotation) {
        acceptedValues = Stream.of(annotation.enumClass().getEnumConstants())
                .map(java.lang.Enum::name)
                .collect(Collectors.toList());
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return isBlank(value) || acceptedValues.contains(value);
    }
}
