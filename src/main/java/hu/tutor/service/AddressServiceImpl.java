package hu.tutor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hu.tutor.dao.AddressDao;
import hu.tutor.model.Address;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AddressServiceImpl implements AddressService {

	@Autowired
	private AddressDao addressDao;

	@Override
	public void saveNewAddress(Address address) {
		try {
			this.addressDao.saveNewAddress(address);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw e;
		}

	}

	@Override
	public void modifyAddress(Address address) {
		try {
			this.addressDao.modifyAddress(address);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw e;
		}

	}

}
