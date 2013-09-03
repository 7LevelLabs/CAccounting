package ua.its.slot7.caccounting.rest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ua.its.slot7.caccounting.model.person.Person;
import ua.its.slot7.caccounting.model.user.User;
import ua.its.slot7.caccounting.service.BLServiceAvatar;
import ua.its.slot7.caccounting.service.PersonServiceAvatar;
import ua.its.slot7.caccounting.service.UserServiceAvatar;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
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

	private final Logger LOGGER = Logger.getLogger("RSPersons");

	@Autowired
	private PersonServiceAvatar personService;

	@Autowired
	private UserServiceAvatar userService;

	@Autowired
	private BLServiceAvatar blService;

	@GET
	@Path("/get/{key}/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Person getPersonByIdForUser(@PathParam("key") String key,
						@PathParam("id") Long id) {
		LOGGER.info("RSPersons.getPersonByIdForUser");

		Person person = blService.getPersonForUserKey(key,id);

		if (person==null) {
			LOGGER.info("Person : NULL");
		} else {
			LOGGER.info("Person : "+person.toString());
		}
		return person;
	}

	@GET
	@Path("/get/{key}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Long> getPersonForUserByKey(@PathParam("key") String key) {
		LOGGER.info("RSPersons.getPersonForUserByKey");
		User user = userService.getUserByPass(key);
		if (user==null) {
			LOGGER.info("User for the key : NULL");
			return null;
		}
		List<Long> personIdList = personService.getPersonsIdsByTheUser(user);
		if (personIdList==null) {
			LOGGER.info("Persons id list : NULL");
		} else {
			LOGGER.info("Persons id list : "+personIdList.toString());
		}
		return personIdList;
	}

	@GET
	@Path("/get/isok/{key}/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public boolean getPersonIsOk(@PathParam("key") String key,
					 @PathParam("id") Long id) {
		LOGGER.info("RSPersons.getPersonIsOk");
		boolean res = false;
		User user = userService.getUserByPass(key);
		if (user==null) {
			LOGGER.info("User for the key : NULL");
			return res;
		}
		Person person = blService.getPersonForUserKey(key,id);
		if (person==null) {
			LOGGER.info("Person : NULL");
		}
		res = blService.personGetOkStatus(person);
		return res;
	}

	public RSPersons () {
		LOGGER.info("RSPersons.constructor");
	}
}
