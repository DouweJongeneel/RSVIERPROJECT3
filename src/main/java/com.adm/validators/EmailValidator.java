package com.adm.validators;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 *
 * @author Albert
 */
@FacesValidator("com.adm.validators.EmailValidator")
public class EmailValidator implements Validator {

	@Override
	public void validate(FacesContext facesContext,
			UIComponent component, Object value)
			throws ValidatorException {

		String email = value.toString();
		
		if (!email.matches("(^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$)")) {
			FacesMessage msg = new FacesMessage("E-mail validatie gefaald", "Ongeldig e-mail adres");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(msg);
		}
	}
}
