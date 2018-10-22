package hu.tutor.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "address")
public class Address {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "address_sequence")
	@SequenceGenerator(name = "address_sequence", sequenceName = "address_sq")
	@Column(name = "id")
	private int id;

	@Column(name = "country")
	private String country;

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

		if (this.componentIsPresent(this.country)) {
			builder.append(this.country);
			builder.append(" ");
		}

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

	private boolean componentIsPresent(String addressComponent) {
		return addressComponent != null && !addressComponent.trim().isEmpty();
	}

}
