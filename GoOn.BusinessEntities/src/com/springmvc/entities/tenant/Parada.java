package com.springmvc.entities.tenant;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class Parada {
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id_parada;
	private String descripcion;
	private String direccion;
	private double latitud;
	private double longitud;
	private Boolean es_terminal = false;
	private Boolean es_peaje = false;
	private float reajuste;
	
	@OneToOne(mappedBy="terminal", cascade=CascadeType.ALL, fetch = FetchType.EAGER)
	@JsonManagedReference
	private Sucursal sucursal;
	
	public Sucursal getSucursal() {
		return sucursal;
	}
	public void setSucursal(Sucursal sucursal) {
		this.sucursal = sucursal;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	
	public long getId_parada() {
		return id_parada;
	}
	public void setId_parada(long id_parada) {
		this.id_parada = id_parada;
	}
	
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public double getLatitud() {
		return latitud;
	}
	public void setLatitud(double latitud) {
		this.latitud = latitud;
	}
	
	public double getLongitud() {
		return longitud;
	}
	public void setLongitud(double longitud) {
		this.longitud = longitud;
	}
	
	public Boolean getEs_terminal() {
		return es_terminal;
	}
	public void setEs_terminal(Boolean es_terminal) {
		this.es_terminal = es_terminal;
	}
	
	public Boolean getEs_peaje() {
		return es_peaje;
	}
	public void setEs_peaje(Boolean es_peaje) {
		this.es_peaje = es_peaje;
	}
	
	public void setReajuste(float reajuste) {
		this.reajuste = reajuste;
	}
	public float getReajuste() {
		return reajuste;
	}
}
