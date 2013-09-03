package ua.its.slot7.caccounting.service;

/**
 * CAccounting
 * 06.08.13 : 13:26
 * Alex Velichko
 * alex.itstudio@gmail.com
 */

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * The main part of Spring Security process, yes. <br/>
 * A custom {@link org.springframework.security.core.userdetails.UserDetailsService} where user information
 * is retrieved from a JPA repository
 */
@Service("UserDetails")
public class UserDetails implements UserDetailsService {

	private final Logger LOGGER = Logger.getLogger(this.getClass());

	@Autowired
	private UserServiceAvatar userService;

	/**
	 * Returns a populated {@link org.springframework.security.core.userdetails.UserDetails} object.<br/>
	 * The username is first retrieved from the database and then mapped to
	 * a {@link org.springframework.security.core.userdetails.UserDetails} object.
	 */
	@Override
	public org.springframework.security.core.userdetails.UserDetails loadUserByUsername(String s)
		throws UsernameNotFoundException {

		org.springframework.security.core.userdetails.UserDetails userDetails;
		ua.its.slot7.caccounting.model.user.User domainUser =
			userService.getUserByEMail(s);
		if (domainUser==null) {
			throw new UsernameNotFoundException(s);
		}
		boolean enabled = true;
		boolean accountNonExpired = true;
		boolean credentialsNonExpired = true;
		boolean accountNonLocked = true;
		userDetails = new org.springframework.security.core.userdetails.User (
			domainUser.getEmail(),
			domainUser.getPass(),
			enabled,
			accountNonExpired,
			credentialsNonExpired,
			accountNonLocked,
			getAuthorities(domainUser.getUserRole().getRole()));
		return userDetails;
	}

	/**
	 * Retrieves a collection of {@link org.springframework.security.core.GrantedAuthority} based on a numerical userrole
	 * @param role the numerical userrole
	 * @return a collection of {@link org.springframework.security.core.GrantedAuthority
	 */
	public List<? extends GrantedAuthority> getAuthorities(int role) {
		return getGrantedAuthorities(getRoles(role));
	}

	/**
	 * Converts a numerical userrole to an equivalent list of roles
	 * @param role the numerical userrole
	 * @return list of roles as as a list of {@link String}
	 */
	public List<String> getRoles(int role) {
		List<String> roles = new ArrayList<String>();
		if (role == 1) {
			roles.add("ROLE_USER");
		} else if (role == 2) {
			roles.add("ROLE_USER");
			roles.add("ROLE_ADMIN");
		}
		return roles;
	}

	/**
	 * Wraps {@link String} roles to {@link org.springframework.security.core.authority.SimpleGrantedAuthority} objects
	 * @param roles {@link String} of roles
	 * @return list of granted authorities
	 */
	public static List<GrantedAuthority> getGrantedAuthorities(List<String> roles) {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		for (String role : roles) {
			authorities.add(new SimpleGrantedAuthority(role));
		}
		return authorities;
	}
}
