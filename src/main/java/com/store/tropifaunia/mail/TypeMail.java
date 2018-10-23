package com.store.tropifaunia.mail;

/**
 * Enum of templates.
 *
 * @author ciber
 */
public enum TypeMail {

	/** The hello. */
	HELLO("email.html"),
	/** The hello pdf. */
	HELLO_PDF("hello.pdf");

	/** The template. */
	private String template;

	/**
	 * Instantiates a new type mail.
	 *
	 * @param template
	 *            the template
	 */
	private TypeMail(String template) {
		this.template = template;
	}

	/**
	 * Gets the template.
	 *
	 * @return the template
	 */
	public String getTemplate() {
		return this.template;
	}
}
