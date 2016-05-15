package com.springmvc.entities.tenant;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity
public class Pasaje {
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)	
	private long id_pasaje;
	private String Desde; //coordenadas?
	private String Hasta; //
	private Double costo;
	private int estado;
	@ManyToOne(fetch=FetchType.LAZY)
	private Usuario user_compra; //puede tener un user vende
	@ManyToOne(fetch=FetchType.LAZY)
	private Viaje viaje; //puede tener un user vende
	@ManyToOne(fetch=FetchType.LAZY)
	private Linea linea;
	@ManyToOne(fetch=FetchType.LAZY)	
	private Asiento asiento; //puede tener un user vende
	@ManyToOne(fetch=FetchType.LAZY)
	private Parada parada_sube;
	@ManyToOne(fetch=FetchType.LAZY)
	private Parada parada_baja;
	
	public long getId_pasaje() {
		return id_pasaje;
	}
	public void setId_pasaje(long id_pasaje) {
		this.id_pasaje = id_pasaje;
	}
	public String getDesde() {
		return Desde;
	}
	public void setDesde(String desde) {
		Desde = desde;
	}
	public String getHasta() {
		return Hasta;
	}
	public void setHasta(String hasta) {
		Hasta = hasta;
	}
	public Double getCosto() {
		return costo;
	}
	public void setCosto(Double costo) {
		this.costo = costo;
	}
	public int getEstado() {
		return estado;
	}
	public void setEstado(int estado) {
		this.estado = estado;
	}
	public Usuario getUser_compra() {
		return user_compra;
	}
	public void setUser_compra(Usuario user_compra) {
		this.user_compra = user_compra;
	}
	public Viaje getViaje() {
		return viaje;
	}
	public void setViaje(Viaje viaje) {
		this.viaje = viaje;
	}
	public Linea getLinea() {
		return linea;
	}
	public void setLinea(Linea linea) {
		this.linea = linea;
	}
	public Asiento getAsiento() {
		return asiento;
	}
	public void setAsiento(Asiento asiento) {
		this.asiento = asiento;
	}
	public Parada getParada_sube() {
		return parada_sube;
	}
	public void setParada_sube(Parada parada_sube) {
		this.parada_sube = parada_sube;
	}
	public Parada getParada_baja() {
		return parada_baja;
	}
	public void setParada_baja(Parada parada_baja) {
		this.parada_baja = parada_baja;
	}
}
