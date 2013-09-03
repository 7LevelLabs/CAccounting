package ua.its.slot7.caccounting.view.web.validators;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * CAccounting
 * 08.07.13 : 16:49
 * Alex Velichko
 * alex.itstudio@gmail.com
 * <p/>
 * <a rel="license" href="http://creativecommons.org/licenses/by-sa/3.0/">
 * <img alt="Creative Commons License" style="border-width:0" src="http://i.creativecommons.org/l/by-sa/3.0/88x31.png" />
 * </a><br />
 * This work is licensed under a
 * <a rel="license" href="http://creativecommons.org/licenses/by-sa/3.0/">Creative Commons Attribution-ShareAlike 3.0 Unported License</a>.
 */
@Component("validatorEMail")
@Scope("request")
public class VEMail implements Validator {

	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\." +
		"[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*" +
		"(\\.[A-Za-z]{2,})$";

	private Pattern pattern;
	private Matcher matcher;

	private final Logger LOGGER = Logger.getLogger(this.getClass());

	public VEMail() {
		pattern = Pattern.compile(EMAIL_PATTERN);
	}

	@Override
	public void validate(FacesContext facesContext, UIComponent uiComponent, Object o) throws ValidatorException {
		String email = o.toString();
		matcher = pattern.matcher(email);
		if(!matcher.matches()){
			FacesMessage msg =
				new FacesMessage("Invalid EMail format.");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(msg);
		}
	}
}
