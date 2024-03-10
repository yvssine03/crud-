package com.employeedao.Validators;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.validator.FacesValidator;
import jakarta.faces.validator.Validator;
import jakarta.faces.validator.ValidatorException;

import java.util.ResourceBundle;

public class EmailValidator implements Validator {

    private static final String EMAIL_PATTERN = "^[A-Za-z0-9+_.-]+@(.+)$";

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        ResourceBundle bundle =  ResourceBundle.getBundle("i18n.messages" , FacesContext.getCurrentInstance().getViewRoot().getLocale());
        if (value == null || value.toString().isEmpty()) {
            return; // Consider empty fields as valid.
        }

        if (!value.toString().matches(EMAIL_PATTERN)) {
            throw new ValidatorException(new FacesMessage(bundle.getString("invalid_format"))); // TODO:i18n
        }
    }
}