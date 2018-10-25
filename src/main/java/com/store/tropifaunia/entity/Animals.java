package com.store.tropifaunia.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "animals")
public class Animals implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name = "id")
	private int id;

	@Column(name = "nombreRaza")
	private String nombreRaza;
	@Column(name = "tipo")
	private String tipo;
	@Column(name = "numero")
	private int numero;
	@Column(name = "euros")
	private double euros;

	public Animals() {
	}

	public Animals(int id, String nombreRaza, String tipo, int numero, double euros) {
		super();
		this.id = id;
		this.nombreRaza = nombreRaza;
		this.tipo = tipo;
		this.numero = numero;
		this.euros = euros;
	}

	/**
	 * @return the nombreRaza
	 */
	public String getNombreRaza() {
		return nombreRaza;
	}

	/**
	 * @param nombreRaza
	 *            the nombreRaza to set
	 */
	public void setNombreRaza(String nombreRaza) {
		this.nombreRaza = nombreRaza;
	}

	/**
	 * @return the tipo
	 */
	public String getTipo() {
		return tipo;
	}

	/**
	 * @param tipo
	 *            the tipo to set
	 */
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	/**
	 * @return the numero
	 */
	public int getNumero() {
		return numero;
	}

	/**
	 * @param numero
	 *            the numero to set
	 */
	public void setNumero(int numero) {
		this.numero = numero;
	}

	/**
	 * @return the €
	 */
	public double getEuros() {
		return euros;
	}

	/**
	 * @param €
	 *            the € to set
	 */
	public void setEuros(double euros) {
		this.euros = euros;
	}

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

}
