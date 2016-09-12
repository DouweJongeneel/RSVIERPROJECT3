/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.adm.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Albert
 */
@Embeddable
public class KlantadresadrestypePK implements Serializable {

	@Basic(optional = false)
    @NotNull
    @Column(name = "klantId")
	private long klantId;
	@Basic(optional = false)
    @NotNull
    @Column(name = "adresId")
	private long adresId;

	public KlantadresadrestypePK() {
	}

	public KlantadresadrestypePK(long klantId, long adresId) {
		this.klantId = klantId;
		this.adresId = adresId;
	}

	public long getKlantId() {
		return klantId;
	}

	public void setKlantId(long klantId) {
		this.klantId = klantId;
	}

	public long getAdresId() {
		return adresId;
	}

	public void setAdresId(long adresId) {
		this.adresId = adresId;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (int) klantId;
		hash += (int) adresId;
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof KlantadresadrestypePK)) {
			return false;
		}
		KlantadresadrestypePK other = (KlantadresadrestypePK) object;
		if (this.klantId != other.klantId) {
			return false;
		}
		if (this.adresId != other.adresId) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "com.mycompany.rsvierproject3.KlantadresadrestypePK[ klantId=" + klantId + ", adresId=" + adresId + " ]";
	}
	
}
