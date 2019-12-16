package com.bomltsportal.web;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.Locale;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.octo.captcha.service.multitype.GenericManageableCaptchaService;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

public class CaptchaController extends SimpleFormController{
	
	private GenericManageableCaptchaService captchaService;
	
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {

		byte[] captchaChallengeAsJpeg = null;

		ByteArrayOutputStream jpegOutputStream = new ByteArrayOutputStream();
		String captchaId = request.getSession().getId();

		BufferedImage challenge = captchaService.getImageChallengeForID(captchaId,request.getLocale());
		
		JPEGImageEncoder jpegEncoder =

		JPEGCodec.createJPEGEncoder(jpegOutputStream);

		jpegEncoder.encode(challenge);
		
		//imageIO.createImageOutputStream(output)
		captchaChallengeAsJpeg = jpegOutputStream.toByteArray();
		response.setHeader("Cache-Control", "no-store");
		response.setHeader("Pragma", "no-cache");
		response.setDateHeader("Expires", 0);
		response.setContentType("image/jpeg");
		ServletOutputStream responseOutputStream =
		response.getOutputStream();
		responseOutputStream.write(captchaChallengeAsJpeg);
		responseOutputStream.flush();
		responseOutputStream.close();

		return
		null;
		}

	public GenericManageableCaptchaService getCaptchaService() {
		return captchaService;
	}

	public void setCaptchaService(GenericManageableCaptchaService captchaService) {
		this.captchaService = captchaService;
	}
}
