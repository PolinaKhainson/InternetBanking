package ua.nure.khainson.SummaryTask4.db.entity;

import java.io.Serializable;

/**
 * Root of all entities which have identifier field.
 * 
 * @author P.Khainson
 * 
 */
public abstract class Entity implements Serializable {

	public Entity() {
		super();
	}

	private static final long serialVersionUID = -3242138571428243748L;
	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
