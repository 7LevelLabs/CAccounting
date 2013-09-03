package ua.its.slot7.caccounting.view.web.utils;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * CAccounting
 * 08.08.13 : 16:27
 * Alex Velichko
 * alex.itstudio@gmail.com
 * <p/>
 * <a rel="license" href="http://creativecommons.org/licenses/by-sa/3.0/">
 * <img alt="Creative Commons License" style="border-width:0" src="http://i.creativecommons.org/l/by-sa/3.0/88x31.png" />
 * </a><br />
 * This work is licensed under a
 * <a rel="license" href="http://creativecommons.org/licenses/by-sa/3.0/">Creative Commons Attribution-ShareAlike 3.0 Unported License</a>.
 */
public class ApplicationEventsListener implements ApplicationListener {
	private final Logger LOGGER = Logger.getLogger(this.getClass());

	@Override
	public void onApplicationEvent(ApplicationEvent event) {
		if (event instanceof AuthenticationSuccessEvent) {
			AuthenticationSuccessEvent authenticationSuccessEvent = (AuthenticationSuccessEvent) event;
			UserDetails userDetails = (UserDetails) authenticationSuccessEvent.getAuthentication().getPrincipal();
		}
	}

	public ApplicationEventsListener () {

	}
}
