package ua.its.slot7.caccounting.rest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ua.its.slot7.caccounting.model.person.Person;
import ua.its.slot7.caccounting.model.user.User;
import ua.its.slot7.caccounting.service.BLServiceAvatar;
import ua.its.slot7.caccounting.service.PersonServiceAvatar;
import ua.its.slot7.caccounting.service.UserServiceAvatar;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * CAccounting
 * 13.08.13 : 17:04
 * Alex Velichko
 * alex.itstudio@gmail.com
 * <p/>
 * <a rel="license" href="http://creativecommons.org/licenses/by-sa/3.0/">
 * <img alt="Creative Commons License" style="border-width:0" src="http://i.creativecommons.org/l/by-sa/3.0/88x31.png" />
 * </a><br />
 * This work is licensed under a
 * <a rel="license" href="http://creativecommons.org/licenses/by-sa/3.0/">Creative Commons Attribution-ShareAlike 3.0 Unported License</a>.
 */
@Controller
@Path("/persons")
public class RSPersons {

	private final Logger LOGGER = Logger.getLogger(RSPersons.class);

	@Autowired
	private PersonServiceAvatar personService;

	@Autowired
	private UserServiceAvatar userService;

	@Autowired
	private BLServiceAvatar blService;

	@GET
	@Path("/{key}/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Person getPersonByIdForUser(@PathParam("key") String key,
						@PathParam("id") Long id) {
		Person person = blService.getPersonForUserKey(key,id);
		if (person==null) {
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
		return person;
	}

	@GET
	@Path("/{key}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Long> getPersonForUserByKey(@PathParam("key") String key) {
		User user = userService.getUserByAPICode(key);
		if (user==null) {
			return null;
		}
		return personService.getPersonsIdsByTheUser(user);
	}

	@GET
	@Path("/isok/{key}/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public boolean getPersonIsOk(@PathParam("key") String key,
					 @PathParam("id") Long id) {
		boolean res = false;
		User user = userService.getUserByAPICode(key);
		if (user==null) {
			return res;
		}
		Person person = blService.getPersonForUserKey(key,id);
		return blService.personGetOkStatus(person);
	}

	public RSPersons () {

	}
}
