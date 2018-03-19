package com.leave.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

@MappedSuperclass
public class AbstractEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected Long id;

	@JsonIgnore
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public final Long getId() {
		return id;
	}

	public final void setId(Long id) {
		this.id = id;
	}

	/**
	 * Méthode equals
	 * 
	 * @param o
	 * @return boolean
	 */
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null) {
			return false;
		}
		// Class oClass = o.getClass();
		// if (o instanceof HibernateProxy) {
		// oClass = HibernateProxyHelper.getClassWithoutInitializingProxy(o);
		// }
		//
		// if (realClass() != oClass) {
		// return false;
		// }
		AbstractEntity that = (AbstractEntity) o;
		return this.getId() != null && this.getId().equals(that.getId());

	}

	// /**
	// * Getter : retourne la vrai classe (dans le cas d'un proxy Hibernate)
	// *
	// * @return the realClass
	// */
	// public Class realClass() {
	// Class clazz = getClass();
	// if (this instanceof HibernateProxy) {
	// clazz = HibernateProxyHelper.getClassWithoutInitializingProxy(this);
	// }
	// return clazz;
	// }

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(this.getId()).toHashCode();
	}
}
