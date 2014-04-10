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

	private static EMailSenderBean eMailSenderBean;

	private static final String nameToTest = "java:global/CAMailer-0.1/EMailSenderBeanEJB!ua.its.slot7.camailer.EMailSenderBeanAvatarLocal";
	// or java:global/CAMailer-0.1/EMailSenderBeanEJB!ua.its.slot7.camailer.EMailSenderBeanAvatarLocal

	@BeforeClass
	public static void setupContainer() throws Exception {
		Map properties = new HashMap();
		properties.put(EJBContainer.MODULES, "EMailSenderBeanEJB");
		properties.put(EJBContainer.PROVIDER, "javax.ejb.spi.EJBContainerProvider");
		ejbContainer = EJBContainer.createEJBContainer(properties);

		ctx = ejbContainer.getContext();

		eMailSenderBean = (EMailSenderBean) ctx.lookup("java:global/CAMailer-0.1/EMailSenderBeanEJB");

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

