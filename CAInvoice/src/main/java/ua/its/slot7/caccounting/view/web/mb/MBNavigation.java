package ua.its.slot7.caccounting.view.web.mb;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.Map;

/**
 * CAccounting
 * 23.08.13 : 14:26
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
@ManagedBean(name = "MBNavigation")
@Scope("session")
@SessionScoped
public class MBNavigation implements Serializable {
	private final Logger LOGGER = Logger.getLogger(this.getClass());

	public String navigateTo() {

		Map<String,String> params =
			FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();

		String direction = params.get("direction");
		String res = null;
		res=direction+"?faces-redirect=true";
		return res;
	}

	public MBNavigation() {

	}

}
