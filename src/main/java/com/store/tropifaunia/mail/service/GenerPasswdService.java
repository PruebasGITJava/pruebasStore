package com.store.tropifaunia.mail.service;

/**
 * The Class GenerPasswdService.
 */
public class GenerPasswdService {

	/** The Constant NUMEROS. */
	public static final String NUMEROS = "0123456789";

	/** The Constant MAYUSCULAS. */
	public static final String MAYUSCULAS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

	/** The Constant MINUSCULAS. */
	public static final String MINUSCULAS = "abcdefghijklmnopqrstuvwxyz";

	/** The Constant ESPECIALES. */
	public static final String ESPECIALES = "ñÑ";

	private GenerPasswdService() {

	}

	/**
	 * Gets the pin number.
	 *
	 * @return the pin number
	 */
	public static String getPinNumber() {
		return getPassword(NUMEROS, 4);
	}

	/**
	 * Gets the password.
	 *
	 * @return the password
	 */
	public static String getPassword() {
		return getPassword(8);
	}

	/**
	 * Gets the password.
	 *
	 * @param length
	 *            the length
	 * @return the password
	 */
	public static String getPassword(int length) {
		return getPassword(NUMEROS + MAYUSCULAS + MINUSCULAS, length);
	}

	/**
	 * Gets the password.
	 *
	 * @param key
	 *            the key
	 * @param length
	 *            the length
	 * @return the password
	 */
	public static String getPassword(String key, int length) {
		String pswd = "";

		for (int i = 0; i < length; i++) {
			pswd += (key.charAt((int) (Math.random() * key.length())));
		}

		return pswd;
	}

}
