package hu.tutor.dao;

import hu.tutor.model.Address;

public interface AddressDao {

	public void saveNewAddress(Address adddress);

	public void modifyAddress(Address address);
}
