package ua.its.slot7.caccounting.view.web.mb;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import ua.its.slot7.caccounting.model.user.User;
import ua.its.slot7.caccounting.service.UserServiceAvatar;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;
import java.io.Serializable;

/**
 * CAccounting
 * 29.07.13 : 12:07
 * Alex Velichko
 * alex.itstudio@gmail.com
 * <p/>
 * <a rel="license" href="http://creativecommons.org/licenses/by-sa/3.0/">
 * <img alt="Creative Commons License" style="border-width:0" src="http://i.creativecommons.org/l/by-sa/3.0/88x31.png" />
 * </a><br />
 * This work is licensed under a
 * <a rel="license" href="http://creativecommons.org/licenses/by-sa/3.0/">Creative Commons Attribution-ShareAlike 3.0 Unported License</a>.
 */
@Component
@ManagedBean(name = "MBUserLogin")
@Scope("request")
@RequestScoped
public class MBUserLogin implements Serializable {
	private final Logger LOGGER = Logger.getLogger(this.getClass());

	private String username = "";
	private String password = "";
	private boolean rememberMe = false;
	private boolean loggedIn = false;

	private User loggedUser = null;

	@Autowired
	private UserServiceAvatar userService;

	public String doLogin() throws IOException, ServletException {
		ExternalContext context = FacesContext.getCurrentInstance()
			.getExternalContext();
		RequestDispatcher dispatcher = ((ServletRequest) context.getRequest())
			.getRequestDispatcher("/j_spring_security_check");
		ServletRequest request = ((ServletRequest) context.getRequest());
		String checkboxValue = request
			.getParameter("_spring_security_remember_me_input");
		dispatcher.forward((ServletRequest) context.getRequest(),
			(ServletResponse) context.getResponse());
		FacesContext.getCurrentInstance().responseComplete();
		return "/pages/index.xhtml?faces-redirect=true";
	}

	public void refreshLoggedUser () {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			UserDetails userDetails =
				(UserDetails)auth.getPrincipal();
			this.setLoggedUser(this.userService.getUserByEMail(userDetails.getUsername()));
		}
	}

	public User getLoggedUser() {
		return loggedUser;
	}

	public void setLoggedUser(User loggedUser) {
		this.loggedUser = loggedUser;
	}

	public boolean isLoggedIn() {
		return loggedIn;
	}

	public void setLoggedIn(boolean loggedIn) {
		this.loggedIn = loggedIn;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isRememberMe() {
		return rememberMe;
	}

	public void setRememberMe(boolean rememberMe) {
		this.rememberMe = rememberMe;
	}
}
