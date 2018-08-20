package com.polozhaev.business.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.lang.NonNull;

@Entity
@Table(name = "customer")
public class Customer implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@NotNull
	private String name;

	@NotNull
	private String surname;

	@NotNull
	@Column(name = "idcustomer", unique = true)
	private String idcustomer;

	private String photo;

	@NonNull
	@ManyToOne
	private User createby;

	@ManyToOne
	private User modifyby;

	public User getCreateby() {
		return createby;
	}

	public void setCreateby(User createby) {
		this.createby = createby;
	}

	public User getModifyby() {
		return modifyby;
	}

	public void setModifyby(User modifyby) {
		this.modifyby = modifyby;
	}

	public Customer() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getIdCustomer() {
		return idcustomer;
	}

	public void setIdCustomer(String idCustomer) {
		this.idcustomer = idCustomer;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Customer [id=" + id + ", name=" + name + ", surname=" + surname + ", idcustomer=" + idcustomer
				+ ", photo=" + photo + ", createby=" + createby + ", modifyby=" + modifyby + "]";
	}

}
