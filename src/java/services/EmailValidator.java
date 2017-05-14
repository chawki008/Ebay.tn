package services;

import javax.faces.application.FacesMessage;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.inject.Named;

@FacesValidator( value ="emailValidator")
@SessionScoped
public class EmailValidator implements Validator{
        @Override
	public void validate(FacesContext context, UIComponent component, Object value)
			throws ValidatorException {
		
		String email = (String) value;
		
		if(!email.contains("@")) {
			FacesMessage message = new FacesMessage();
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
			message.setSummary("Email is not valid.");
			message.setDetail("Email is not valid.");
			context.addMessage("userForm:Email", message);
			throw new ValidatorException(message);
		}
	}
}