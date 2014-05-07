package ua.its.slot7.caccounting.schedule.tasks;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * @author Alex Velichko
 *         06.05.14 : 18:19
 */
public class DummyTask extends QuartzJobBean {
	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		System.out.println("Action! :)");
	}
}
