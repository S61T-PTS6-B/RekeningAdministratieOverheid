/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.validation.constraints.NotNull;

/**
 *
 * @author koenv
 */
@Entity
public class CarOwner implements Serializable {

	private static final long serialVersionUID = 1L;

	@TableGenerator(
		name = "tableGen",
		allocationSize = 1,
		initialValue = 1)
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE,
		generator = "tableGen")
	private Long id;

	private CarTracker carid;

	private NAW nawid;

	@NotNull
	@Temporal(javax.persistence.TemporalType.TIMESTAMP)
	private Date startOwnership;

	@Temporal(javax.persistence.TemporalType.TIMESTAMP)
	private Date endOwnership;

	private boolean active;

	public CarOwner() {
	}

	public CarOwner(CarTracker ct, NAW n, Date startOwnership) {
		this.carid = ct;
		this.nawid = n;
		this.startOwnership = startOwnership;

		active = true;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public CarTracker getCarid() {
		return carid;
	}

	public void setCarid(CarTracker carid) {
		this.carid = carid;
	}

	public NAW getNawid() {
		return nawid;
	}

	public void setNawid(NAW nawid) {
		this.nawid = nawid;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Date getStartOwnership() {
		return startOwnership;
	}

	public void setStartOwnership(Date startOwnership) {
		this.startOwnership = startOwnership;
	}

	public Date getEndOwnership() {
		return endOwnership;
	}

	public void setEndOwnership(Date endOwnership) {
		this.endOwnership = endOwnership;
		this.active = false;
	}

	
}
