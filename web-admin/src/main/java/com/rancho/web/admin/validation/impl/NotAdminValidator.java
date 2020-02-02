package com.rancho.web.admin.validation.impl;

import com.rancho.web.admin.validation.NotAdmin;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NotAdminValidator implements ConstraintValidator<NotAdmin,String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if("admin".equals(value)){
            return false;
        }
        return true;
    }
}
