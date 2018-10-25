package com.store.tropifaunia.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The Class Animals.
 */
@Entity
@Table(name = "animals")
public class Animals implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The id. */
	@Id
	@GeneratedValue
	@Column(name = "id")
	private int id;

	/** The nombre raza. */
	@Column(name = "nombreRaza")
	private String nombreRaza;

	/** The tipo. */
	@Column(name = "tipo")
	private String tipo;

	/** The numero. */
	@Column(name = "numero")
	private int numero;

	/** The euros. */
	@Column(name = "euros")
	private double euros;

	/**
	 * Instantiates a new animals.
	 */
	public Animals() {
	}

	/**
	 * Instantiates a new animals.
	 *
	 * @param id
	 *            the id
	 * @param nombreRaza
	 *            the nombre raza
	 * @param tipo
	 *            the tipo
	 * @param numero
	 *            the numero
	 * @param euros
	 *            the euros
	 */
	public Animals(int id, String nombreRaza, String tipo, int numero, double euros) {
		super();
		this.id = id;
		this.nombreRaza = nombreRaza;
		this.tipo = tipo;
		this.numero = numero;
		this.euros = euros;
	}

	/**
	 * Gets the nombre raza.
	 *
	 * @return the nombreRaza
	 */
	public String getNombreRaza() {
		return nombreRaza;
	}

	/**
	 * Sets the nombre raza.
	 *
	 * @param nombreRaza
	 *            the nombreRaza to set
	 */
	public void setNombreRaza(String nombreRaza) {
		this.nombreRaza = nombreRaza;
	}

	/**
	 * Gets the tipo.
	 *
	 * @return the tipo
	 */
	public String getTipo() {
		return tipo;
	}

	/**
	 * Sets the tipo.
	 *
	 * @param tipo
	 *            the tipo to set
	 */
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	/**
	 * Gets the numero.
	 *
	 * @return the numero
	 */
	public int getNumero() {
		return numero;
	}

	/**
	 * Sets the numero.
	 *
	 * @param numero
	 *            the numero to set
	 */
	public void setNumero(int numero) {
		this.numero = numero;
	}

	/**
	 * Gets the euros.
	 *
	 * @return the €
	 */
	public double getEuros() {
		return euros;
	}

	/**
	 * Sets the euros.
	 *
	 * @param euros
	 *            the new euros
	 */
	public void setEuros(double euros) {
		this.euros = euros;
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id
	 *            the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

}
