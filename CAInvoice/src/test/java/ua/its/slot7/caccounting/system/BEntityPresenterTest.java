package ua.its.slot7.caccounting.system;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-config-IT.xml")
public class BEntityPresenterTest extends Assert {

	@Autowired
	private BEntityPresenter entityPresenter;

	@Test
	public void testPresentToHTML() throws Exception {

	}
}