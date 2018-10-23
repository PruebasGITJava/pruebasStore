package com.store.tropifaunia.mail.service;

import java.io.IOException;

import javax.mail.MessagingException;

import org.springframework.mail.SimpleMailMessage;

import com.store.tropifaunia.entity.Contact;

import freemarker.template.TemplateException;

/**
 * The Interface MailService.
 */
public interface MailService {

	/**
	 * Send simple message.
	 *
	 * @param to
	 *            the to
	 * @param subject
	 *            the subject
	 * @param text
	 *            the text
	 */
	void sendSimpleMessage(String to, String subject, String text);

	/**
	 * Send simple message using template.
	 *
	 * @param to
	 *            the to
	 * @param subject
	 *            the subject
	 * @param template
	 *            the template
	 * @param templateArgs
	 *            the template args
	 */
	void sendSimpleMessageUsingTemplate(String to, String subject, SimpleMailMessage template, String... templateArgs);

	/**
	 * Send message with attachment.
	 *
	 * @param to
	 *            the to
	 * @param subject
	 *            the subject
	 * @param text
	 *            the text
	 * @param pathToAttachment
	 *            the path to attachment
	 */
	void sendMessageWithAttachment(String to, String subject, String text, String pathToAttachment);

	/**
	 * Send simple message HTMLP.
	 *
	 * @param to
	 *            the to
	 * @param id
	 *            the id
	 * @throws MessagingException
	 *             the messaging exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws TemplateException
	 *             the template exception
	 */
	void sendSimpleMessageHTMLP(String to, int id) throws MessagingException, IOException, TemplateException;

	/**
	 * Send simple message passwd reset.
	 *
	 * @param contact
	 *            the contact
	 * @param passwd
	 *            the passwd
	 */
	void sendSimpleMessagePasswdReset(Contact contact, String passwd);
}