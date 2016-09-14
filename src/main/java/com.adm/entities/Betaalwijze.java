/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.adm.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Albert
 */
@Entity
@Table(name = "betaalwijze")
@XmlRootElement
@NamedQueries({
	@NamedQuery(name = "Betaalwijze.findAll", query = "SELECT b FROM Betaalwijze b"),
	@NamedQuery(name = "Betaalwijze.findById", query = "SELECT b FROM Betaalwijze b WHERE b.id = :id"),
	@NamedQuery(name = "Betaalwijze.findByBetaalWijze", query = "SELECT b FROM Betaalwijze b WHERE b.betaalWijze = :betaalWijze")})
public class Betaalwijze implements Serializable {

	private static final long serialVersionUID = 1L;

	@Transient
	private final String[] type = {"harrie.betaling.creditcard",
		"harrie.betaling.cash",
		"harrie.betaling.ideal",
		"harrie.betaling.paypal",
		"harrie.betaling.inkind"};

	@Transient
	String value;

	@Id
	@Basic(optional = false)
	@NotNull
	@Column(name = "id")
	private Long id;

	@Size(max = 255)
	@Column(name = "betaalWijze")
	private String betaalWijze;

	@OneToOne(cascade = CascadeType.ALL, mappedBy = "betaalwijzeId")
	private Betaling betaling;

	public Betaalwijze() {
	}

	public Betaalwijze(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBetaalWijze() {
		return betaalWijze;
	}

	public void setBetaalWijze(String betaalWijze) {
		this.betaalWijze = betaalWijze;
	}

	public Betaling getBetaling() {
		return betaling;
	}

	public void setBetaling(Betaling betaling) {
		this.betaling = betaling;
	}

	public void setValue(String value) {
		this.value = value;
		setBetaalWijzeType();
	}

	public void setBetaalWijzeType() {
		betaalWijze = type[Integer.parseInt(value)];
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (id != null ? id.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof Betaalwijze)) {
			return false;
		}
		Betaalwijze other = (Betaalwijze) object;
		if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "com.mycompany.rsvierproject3.Betaalwijze[ id=" + id + " ]";
	}

}
