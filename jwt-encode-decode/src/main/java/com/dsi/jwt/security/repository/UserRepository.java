package com.dsi.jwt.security.repository;


import com.dsi.jwt.security.model.DAOUser;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;


public interface UserRepository  {
	DAOUser findByUsername(String username);

}
@Component
class UserRepositoryImp implements UserRepository{

	@Override
	public DAOUser findByUsername(String username) {
		if(username.equals("admin")){
			DAOUser daoUser = new DAOUser();
			daoUser.setUsername("admin");
			daoUser.setPassword("admin");
			daoUser.setRole("admin");
			return daoUser;
		}
		return null;
	}
}