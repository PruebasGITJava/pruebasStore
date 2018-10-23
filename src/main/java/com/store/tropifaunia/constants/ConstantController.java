package com.store.tropifaunia.constants;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.store.tropifaunia.controllers.ContactController;
import com.store.tropifaunia.controllers.LoginController;

public class ConstantController {
	public static final Log LOG = LogFactory.getLog(LoginController.class);
	public static final Log LOG2 = LogFactory.getLog(ContactController.class);
	public static final String REDIRECT = "redirect:/login";
	public static final String RAIZ = "/";
	public static final String LOGIN = "/login";
	public static final String LOGIN_CHECK = "/logincheck";
	public static final String REDIRECT_ERROR = "redirect:/login?error";
	public static final String ADDCONTACT = "/addcontact";
	public static final String ADDCONTACT_CHECK = "/addcontactcheck";
	public static final String EDITPERFIL = "/editperfil";
	public static final String RESET = "/resetpasswd";
	public static final String RESET_CHECK = "/resetpasswdcheck";

}