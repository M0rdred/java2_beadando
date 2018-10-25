package hu.tutor.service;

import hu.tutor.model.Address;

public interface AddressService {
	public void saveNewAddress(Address address);

	public void modifyAddress(Address address);
}
