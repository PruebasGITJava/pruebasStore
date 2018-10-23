package com.store.tropifaunia.mail.config;

import org.springframework.context.annotation.Bean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

/**
 * The Class MailConfig.
 */
public class MailConfig {

	/**
	 * Multipart resolver.
	 *
	 * @return the multipart resolver
	 */
	@Bean
	public MultipartResolver multipartResolver() {
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
		multipartResolver.setMaxUploadSize(5242880);
		return multipartResolver;
	}

	/**
	 * Template simple message.
	 *
	 * @return the simple mail message
	 */
	@Bean
	public SimpleMailMessage templateSimpleMessage() {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setText("Texto de prueba");
		return message;
	}

}
