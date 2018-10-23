package com.store.tropifaunia.mail.service.impl;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import com.store.tropifaunia.mail.TypeMail;

import freemarker.template.Configuration;

/**
 * 
 * @author icozar
 *
 */
@Component("freeMarkerService")
public class FreeMarkerService {

	private static final Logger LOGGER = LoggerFactory.getLogger(FreeMarkerService.class);

	@Autowired
	private Configuration freemarkerConfiguration;

	/**
	 * Process the html template
	 *
	 * @param model
	 * @return
	 */
	public String geFreeMarkerTemplate(Map<String, Object> model, TypeMail type) {
		LOGGER.info("Method: geFreeMarkerTemplate");
		if (model.isEmpty()) {
			return geFreeMarkerTemplateSimple(type);
		}
		return geFreeMarkerTemplateContent(model, type);

	}

	/**
	 * Gets a simple template processed
	 * 
	 * @param type
	 * @return
	 */
	public String geFreeMarkerTemplateSimple(TypeMail type) {
		LOGGER.info("Method: geFreeMarkerTemplateContent");

		StringBuilder content = new StringBuilder();
		try {
			content.append(FreeMarkerTemplateUtils
					.processTemplateIntoString(freemarkerConfiguration.getTemplate(type.getTemplate()), null));

			LOGGER.info("Content: {}", content);

			return content.toString();
		} catch (Exception e) {
			LOGGER.info("Exception occured while processing fmtemplate: {}", e.getMessage());
		}
		return null;
	}

	/**
	 * Gets a template with content processed
	 * 
	 * @param model
	 * @param type
	 * @return
	 */
	public String geFreeMarkerTemplateContent(Map<String, Object> model, TypeMail type) {
		LOGGER.info("Method: geFreeMarkerTemplateContent");
		StringBuilder content = new StringBuilder();
		try {
			content.append(FreeMarkerTemplateUtils
					.processTemplateIntoString(freemarkerConfiguration.getTemplate(type.getTemplate()), null));

			LOGGER.info("Content: {}", content);

			return content.toString();
		} catch (Exception e) {
			LOGGER.info("Exception occured while processing fmtemplate: {}", e.getMessage());
		}
		return null;
	}
}
