package ua.its.slot7.caccounting.utils.apps;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author Alex Velichko
 *         07.05.14 : 14:17
 */
public class DummyTaskTestApp {
	public static void main(String[] args) throws Exception {
		new ClassPathXmlApplicationContext("spring-config-IT-Quartz.xml");
	}
}
