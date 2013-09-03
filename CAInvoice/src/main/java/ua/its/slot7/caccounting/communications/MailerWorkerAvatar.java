package ua.its.slot7.caccounting.communications;

import ua.its.slot7.camailtask.model.MailTask;

import javax.jms.JMSException;

/**
 * CAccounting
 * 28.08.13 : 15:16
 * Alex Velichko
 * alex.itstudio@gmail.com
 * <p/>
 * <a rel="license" href="http://creativecommons.org/licenses/by-sa/3.0/">
 * <img alt="Creative Commons License" style="border-width:0" src="http://i.creativecommons.org/l/by-sa/3.0/88x31.png" />
 * </a><br />
 * This work is licensed under a
 * <a rel="license" href="http://creativecommons.org/licenses/by-sa/3.0/">Creative Commons Attribution-ShareAlike 3.0 Unported License</a>.
 */
public interface MailerWorkerAvatar {
	public void sendOutboundMailTaskQMessage(MailTask mailTask) throws JMSException;
	public void setDestination (String destinationString);
}
