/**
 * 
 */
package com.nirmal.springbootrest.model;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;

/**
 * Model class for map the BOOK table using Hibernate
 * @author Nirmal Balasooriya
 *
 */
@Entity
@Table(name = "book")
public class Book {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@JsonIgnore
	private int id;
	@Column(name="ISBMNUMBER")
	private String isbmNumber;
	private String name;
	private String description;
	private String auther;

	@Temporal(TemporalType.TIMESTAMP)
	private Date timeStamp;

	@Temporal(TemporalType.DATE)
	private Date date;

	@Temporal(TemporalType.TIME)
	private Date time;


	/**
	 * This is auto generated id from database
	 * @return the id
	 */
	@JsonIgnore
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the isbmNumber
	 */
	public String getIsbmNumber() {
		return isbmNumber;
	}
	/**
	 * @param isbmNumber the isbmNumber to set
	 */
	public void setIsbmNumber(String isbmNumber) {
		this.isbmNumber = isbmNumber;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @return the auther
	 */
	public String getAuther() {
		return auther;
	}
	/**
	 * @param auther the auther to set
	 */
	public void setAuther(String auther) {
		this.auther = auther;
	}

	public Date getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	/**
	 * @param isbmNumber
	 * @param name
	 */
	public Book(String isbmNumber, String name) {
		super();
		this.isbmNumber = isbmNumber;
		this.name = name;
	}



	/**
	 * @param isbmNumber
	 * @param name
	 * @param description
	 * @param auther
	 */
	public Book(String isbmNumber, String name, String description, String auther) {
		super();
		this.isbmNumber = isbmNumber;
		this.name = name;
		this.description = description;
		this.auther = auther;
	}
	
	/**
	 * 
	 */
	public Book() {
		super();
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((isbmNumber == null) ? 0 : isbmNumber.hashCode());
		return result;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Book other = (Book) obj;
		if (isbmNumber == null) {
			if (other.isbmNumber != null)
				return false;
		} else if (!isbmNumber.equals(other.isbmNumber))
			return false;
		return true;
	}
}
