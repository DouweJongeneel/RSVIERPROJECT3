/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.adm.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Albert
 */
@Entity
@Table(name = "prijs")
@XmlRootElement
@NamedQueries({
	@NamedQuery(name = "Prijs.findAll", query = "SELECT p FROM Prijs p"),
	@NamedQuery(name = "Prijs.findById", query = "SELECT p FROM Prijs p WHERE p.id = :id"),
	@NamedQuery(name = "Prijs.findByDatumAanmaak", query = "SELECT p FROM Prijs p WHERE p.datumAanmaak = :datumAanmaak"),
	@NamedQuery(name = "Prijs.findByPrijs", query = "SELECT p FROM Prijs p WHERE p.prijs = :prijs")})
public class Prijs implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Basic(optional = false)
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id")
	private Long id;

	@Basic(optional = false)
	@NotNull
	@Column(name = "datumAanmaak")
	@Temporal(TemporalType.DATE)
	private Date datumAanmaak;

	// @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
	@Basic(optional = false)
	@NotNull
	@Column(name = "prijs")
	private BigDecimal prijs;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "prijsId")
	private Collection<Bestelartikel> bestelartikelCollection;

	@ManyToOne(cascade = CascadeType.ALL)
	private Artikel artikel;
	
	public Prijs() {
		this.datumAanmaak = new Date(System.currentTimeMillis());
	}

	public Prijs(BigDecimal prijs) {
		this.prijs = prijs;
		this.datumAanmaak = new Date(System.currentTimeMillis());
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDatumAanmaak() {
		return datumAanmaak;
	}

	public void setDatumAanmaak(Date datumAanmaak) {
		this.datumAanmaak = datumAanmaak;
	}

	public BigDecimal getPrijs() {
		return prijs;
	}

	public void setPrijs(BigDecimal prijs) {
		this.prijs = prijs;
	}

	@XmlTransient
	public Collection<Bestelartikel> getBestelartikelCollection() {
		return bestelartikelCollection;
	}

	public void setBestelartikelCollection(Collection<Bestelartikel> bestelartikelCollection) {
		this.bestelartikelCollection = bestelartikelCollection;
	}

	public Artikel getArtikel() {
		return artikel;
	}

	public void setArtikel(Artikel artikel) {
		this.artikel = artikel;
	}

	
	
	@Override
	public int hashCode() {
		int hash = 0;
		hash += (id != null ? id.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof Prijs)) {
			return false;
		}
		Prijs other = (Prijs) object;
		if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return prijs.toString();
	}

}
