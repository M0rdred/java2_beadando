package hu.tutor.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "address")
public class Address {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private int id;

	@Column(name = "street")
	private String street;

	@Column(name = "houseNumber")
	private String houseNumber;

	@Column(name = "city")
	private String city;

	@Column(name = "zip")
	private String zip;

	public String getFullAddress() {
		StringBuilder builder = new StringBuilder();

		if (this.componentIsPresent(this.zip)) {
			builder.append(this.zip);
			builder.append(", ");
		}

		if (this.componentIsPresent(this.city)) {
			builder.append(this.city);
			builder.append(" ");
		}

		if (this.componentIsPresent(this.street)) {
			builder.append(this.street);
			builder.append(" ");
		}

		if (this.componentIsPresent(this.houseNumber)) {
			builder.append(this.houseNumber);
		}

		return builder.toString();
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getStreet() {
		return this.street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getHouseNumber() {
		return this.houseNumber;
	}

	public void setHouseNumber(String houseNumber) {
		this.houseNumber = houseNumber;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getZip() {
		return this.zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	private boolean componentIsPresent(String addressComponent) {
		return addressComponent != null && !addressComponent.trim().isEmpty();
	}

}
