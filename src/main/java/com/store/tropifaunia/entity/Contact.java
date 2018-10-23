package com.store.tropifaunia.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;

/**
 * The Class Contact.
 */
@Entity
@Table(name = "contact")
public class Contact implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name = "id")
	private int id;

	@Column(name = "nombre")
	private String nombre;
	@Column(name = "appellidos")
	private String appellidos;
	@Column(name = "edad")
	private int edad;
	@Column(name = "localidad")
	private String localidad;
	@Email
	@Column(name = "email")
	private String email;
	@Column(name = "passwd")
	private String passwd;
	@Column(name = "activation")
	private int activation;

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre
	 *            the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * @return the appellidos
	 */
	public String getAppellidos() {
		return appellidos;
	}

	/**
	 * @param appellidos
	 *            the appellidos to set
	 */
	public void setAppellidos(String appellidos) {
		this.appellidos = appellidos;
	}

	/**
	 * @return the edad
	 */
	public int getEdad() {
		return edad;
	}

	/**
	 * @param edad
	 *            the edad to set
	 */
	public void setEdad(int edad) {
		this.edad = edad;
	}

	/**
	 * @return the localidad
	 */
	public String getLocalidad() {
		return localidad;
	}

	/**
	 * @param localidad
	 *            the localidad to set
	 */
	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the passwd
	 */
	public String getPasswd() {
		return passwd;
	}

	/**
	 * @param passwd
	 *            the passwd to set
	 */
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public Contact(int id, String nombre, String appellidos, int edad, String localidad, String email, String passwd) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.appellidos = appellidos;
		this.edad = edad;
		this.localidad = localidad;
		this.email = email;
		this.passwd = passwd;
	}

	public Contact() {

	}

	/**
	 * @return the activation
	 */
	public int getActivation() {
		return activation;
	}

	/**
	 * @param activation
	 *            the activation to set
	 */
	public void setActivation(int activation) {
		this.activation = activation;
	}
}
