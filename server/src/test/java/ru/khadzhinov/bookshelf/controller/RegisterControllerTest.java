package ru.khadzhinov.bookshelf.controller;

import static org.junit.Assert.*;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ru.khadzhinov.bookshelf.TestConfig;

import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.GreenMailUtil;
import com.icegreen.greenmail.util.ServerSetupTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class RegisterControllerTest {	
	@Autowired
	private JavaMailSender javaMailSender;
	
	private GreenMail testSmtp;
	
	@Before
	public void testMailInit() {
		testSmtp = new GreenMail(ServerSetupTest.SMTP);
        testSmtp.start();
	}
	
	@Test
    public void sendEmail() throws MessagingException {
		MimeMessage message = javaMailSender.createMimeMessage();

		/* use the true flag to indicate you need a multipart message */
		MimeMessageHelper helper = new MimeMessageHelper(message, false);
		helper.setTo("test@receiver.com");
		helper.setSubject("test subject");
		helper.setFrom("from@receiver.com");
		
		message.setContent("test message", "text/html; charset=UTF-8");

		javaMailSender.send(message);
         
		/* check message */
        Message[] messages = testSmtp.getReceivedMessages();
        assertEquals(1, messages.length);
        assertEquals("test subject", messages[0].getSubject());
        String body = GreenMailUtil.getBody(messages[0]).replaceAll("=\r?\n", "");
        assertEquals("test message", body);
        
        /* check addresses */
        String addrTo = ((InternetAddress)messages[0].getAllRecipients()[0]).getAddress();
        String addrFrom = ((InternetAddress)messages[0].getFrom()[0]).getAddress();
        assertEquals("test@receiver.com", addrTo);
        assertEquals("from@receiver.com", addrFrom);
    }
 
    @After
    public void cleanup(){
        testSmtp.stop();
    }
}
