/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.adm.entities;

import com.adm.entities.Klantadresadrestype;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Albert
 */
@Entity
@Table(name = "adrestype")
@XmlRootElement
@NamedQueries({
	@NamedQuery(name = "Adrestype.findAll", query = "SELECT a FROM Adrestype a"),
	@NamedQuery(name = "Adrestype.findById", query = "SELECT a FROM Adrestype a WHERE a.id = :id"),
	@NamedQuery(name = "Adrestype.findByAdresType", query = "SELECT a FROM Adrestype a WHERE a.adresType = :adresType")})
public class Adrestype implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
	private Long id;
	
	@Size(max = 255)
    @Column(name = "adres_type")
	private String adresType;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "adresTypeId")
	private Collection<Klantadresadrestype> klantadresadrestypeCollection;
	
	@OneToMany(mappedBy = "typeId")
	private Collection<Adres> adresCollection;

	private static final String[] type = {"harrie.adres.homeAddress", "harrie.adres.workAddress", "harrie.adres.deliveryAddress"};
	
	public Adrestype() {
	}

	public Adrestype(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAdresType() {
		return adresType;
	}

	public void setAdresType(String adresType) {
		this.adresType = adresType;
	}

	@XmlTransient
	public Collection<Klantadresadrestype> getKlantadresadrestypeCollection() {
		return klantadresadrestypeCollection;
	}

	public void setKlantadresadrestypeCollection(Collection<Klantadresadrestype> klantadresadrestypeCollection) {
		this.klantadresadrestypeCollection = klantadresadrestypeCollection;
	}

	@XmlTransient
	public Collection<Adres> getAdresCollection() {
		return adresCollection;
	}

	public void setAdresCollection(Collection<Adres> adresCollection) {
		this.adresCollection = adresCollection;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (id != null ? id.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof Adrestype)) 
			return false;
		else if(!adresType.equals(((Adrestype)object).getAdresType()))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return adresType;
	}
	
}
