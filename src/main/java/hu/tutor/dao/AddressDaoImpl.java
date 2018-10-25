package hu.tutor.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import hu.tutor.model.Address;
import hu.tutor.util.HibernateUtil;

@Repository
public class AddressDaoImpl implements AddressDao {

	@Autowired
	private HibernateUtil hibernateUtil;

	@Override
	public void saveNewAddress(Address adddress) {
		// TODO Auto-generated method stub

	}

	@Override
	public void modifyAddress(Address address) {
		// TODO Auto-generated method stub

	}

}
