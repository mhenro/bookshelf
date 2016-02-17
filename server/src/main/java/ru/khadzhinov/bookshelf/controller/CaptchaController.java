package ru.khadzhinov.bookshelf.controller;

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CaptchaController {	
	private final Logger logger = LoggerFactory.getLogger(MainController.class);	
	
	/* generate captcha to jpeg and remember it in user session */
	@RequestMapping(value = "/get_captcha", method = RequestMethod.GET, produces = "image/jpg")
	public @ResponseBody byte[] getFile(HttpServletRequest request)  {
	    try {
	    	int width = 120;
	        int height = 30;
	        
	        char data[] = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'k', 'm', 'n', 'p', 'q', 'r', 's', 't', 'w', 'x', 'y', 'z'};

	        BufferedImage bufferedImage = new BufferedImage(width, height, 
	                      BufferedImage.TYPE_INT_RGB);

	        Graphics2D g2d = bufferedImage.createGraphics();

	        Font font = new Font("Georgia", Font.BOLD, 18);
	        g2d.setFont(font);

	        RenderingHints rh = new RenderingHints(
	               RenderingHints.KEY_ANTIALIASING,
	               RenderingHints.VALUE_ANTIALIAS_ON);

	        rh.put(RenderingHints.KEY_RENDERING, 
	               RenderingHints.VALUE_RENDER_QUALITY);

	        g2d.setRenderingHints(rh);

	        GradientPaint gp = new GradientPaint(0, 0, 
	        Color.red, 0, height/2, Color.black, true);

	        g2d.setPaint(gp);
	        g2d.fillRect(0, 0, width, height);

	        g2d.setColor(new Color(255, 153, 0));

	        Random r = new Random();

	        StringBuffer captcha = new StringBuffer(10);
	        for (int i = 0; i < 5; i++) {
	        	r = new Random();
	        	captcha.append(data[r.nextInt(data.length)]);
	        }
	        
	        /* send captcha to user session */
	        request.getSession().setAttribute("captcha", captcha.toString());

	        int x = 0; 
	        int y = 0;

	        for (int i = 0; i < captcha.length(); i++) {
	        	x += 11 + (Math.abs(r.nextInt()) % 15);
	            y = 15 + Math.abs(r.nextInt()) % 10;
	            g2d.drawChars(captcha.toString().toCharArray(), i, 1, x, y);
	        }

	        g2d.dispose();

	        /* Create a byte array output stream */
	        ByteArrayOutputStream bao = new ByteArrayOutputStream();
	        
	        /* Write to output stream */
	        ImageIO.write(bufferedImage, "jpg", bao);
	        
	        return bao.toByteArray();
	    } catch (IOException e) {
	    	logger.error(e.toString());
	        throw new RuntimeException(e);
	    }
	}
}