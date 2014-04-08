package ua.its.slot7.camailer;

import org.junit.*;

import javax.ejb.embeddable.EJBContainer;
import javax.naming.Context;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Alex Velichko
 *         08.04.14 : 15:45
 */
public class EMailSenderBeanIT extends Assert {

	private static EJBContainer ejbContainer;
	private static Context ctx;

	private EMailSenderBean eMailSenderBean;

	private static final String nameToTest = "java:global/CAMailer-0.1/EMailSenderBeanEJB!ua.its.slot7.camailer.EMailSenderBeanAvatarLocal";
	// or java:global/CAMailer-0.1/EMailSenderBeanEJB!ua.its.slot7.camailer.EMailSenderBeanAvatarLocal

	@BeforeClass
	public static void setupContainer() throws Exception {
		Map properties = new HashMap();
		properties.put(EJBContainer.PROVIDER, "weblogic.ejb.embeddable.EJBContainerProvider");
		ejbContainer = EJBContainer.createEJBContainer(properties);
		ctx = ejbContainer.getContext();
	}

	@Before
	public void setUp() throws Exception {
		eMailSenderBean = (EMailSenderBean) ctx.lookup(nameToTest);
	}

	@AfterClass
	public static void shutdownContainer() throws Exception {
		if (ejbContainer != null) {
			ejbContainer.close();
		}
	}

	@Test
	public void testSendEmail() throws Exception {
		System.out.println(eMailSenderBean.getClass().getCanonicalName());

	}

}
