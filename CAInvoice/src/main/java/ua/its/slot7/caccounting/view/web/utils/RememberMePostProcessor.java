package ua.its.slot7.caccounting.view.web.utils;

import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.security.web.authentication.rememberme.AbstractRememberMeServices;

/**
 * CAccounting
 * 29.07.13 : 14:36
 * Alex Velichko
 * alex.itstudio@gmail.com
 * <p/>
 * <a rel="license" href="http://creativecommons.org/licenses/by-sa/3.0/">
 * <img alt="Creative Commons License" style="border-width:0" src="http://i.creativecommons.org/l/by-sa/3.0/88x31.png" />
 * </a><br />
 * This work is licensed under a
 * <a rel="license" href="http://creativecommons.org/licenses/by-sa/3.0/">Creative Commons Attribution-ShareAlike 3.0 Unported License</a>.
 */
public class RememberMePostProcessor implements BeanPostProcessor {
	public Object postProcessAfterInitialization(Object bean, String name) {
		if (bean instanceof AbstractRememberMeServices) {
			AbstractRememberMeServices rememberMe = (AbstractRememberMeServices) bean;
			rememberMe.setParameter("_spring_security_remember_me_input");
		}
		return bean;
	}

	public Object postProcessBeforeInitialization(Object bean, String name) {
		return bean;
	}

}
