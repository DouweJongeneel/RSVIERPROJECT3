package com.adm.validators;

import com.adm.session.KlantFacade;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
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
@ManagedBean
public class EmailValidator implements Validator {

	@EJB
	KlantFacade klantFacade;
	
	@Override
	public void validate(FacesContext facesContext,
			UIComponent component, Object value)
			throws ValidatorException {

		System.out.println(klantFacade == null);
		
		String email = value.toString();
		
		if (!email.matches("(^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$)")) {
			FacesMessage msg = new FacesMessage("E-mail validatie gefaald", "Ongeldig e-mail adres");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(msg);
		}
	}
}
