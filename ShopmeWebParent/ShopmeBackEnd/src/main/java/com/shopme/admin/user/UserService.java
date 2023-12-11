package com.shopme.admin.user;

import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.shopme.common.entity.Role;
import com.shopme.common.entity.User;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class UserService {

	//public static final int USER_PER_PAGE =5;
	
	@Autowired
	private UserRepository userRepo;
	@Autowired
	private RoleRepository roleRepo ;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	
	public User getByEmail(String email) {
		return userRepo.getUserByEmail(email);
	}
	

	public List<User> listAll(){	
		return (List<User>) userRepo.findAll();
	}
	
		
	public List<Role> listRoles(){
		return  (List<Role>) roleRepo.findAll();
	}

	//code for save user 
	public User save(User user) {
		boolean isUpdatingUser = (user.getId()!=null);  //if the user us already present then return true 
		
		if(isUpdatingUser) {          
			User existingUser = userRepo.findById(user.getId()).get();  //find user by id 
			
			if(user.getPassword().isEmpty()){       //while editing userpassword is empty then set previous password otherwise set new password
				user.setPassword(existingUser.getPassword());
			}
			else {
			encodePassword(user);  //here changes done
			} 
		}
		else {
			encodePassword(user);
		}
			return userRepo.save(user);
	}
	
	//code for updating user account details 
	public User updateAccount(User userInForm) {
		User userInDB = userRepo.findById(userInForm.getId()).get();
		if(! userInForm.getPassword().isEmpty()) {
			userInDB.setPassword(userInForm.getPassword());
			encodePassword(userInDB);
		}
		
			if(userInForm.getPhotos() != null) {
				userInDB.setPhotos(userInForm.getPhotos());				
			}
			
			userInDB.setFirstName(userInForm.getFirstName());		
			userInDB.setLastName(userInForm.getLastName());
		
			return userRepo.save(userInDB);
	}
	
	//code for encoding password
	private void encodePassword(User user) {
		String encodedPassword = passwordEncoder.encode(user.getPassword()); 
	   user.setPassword(encodedPassword);
	}

	
	//code for checking email is unique or not 
	public boolean isEmailUnique(Integer id ,String email) {
		User userByEmail = userRepo.getUserByEmail(email);
		if(userByEmail==null)return true;  //means user is unique 
		boolean isCreatingNew =(id==null);
		
		if(isCreatingNew) {
			if(userByEmail != null)return false;
		}else {
			if(userByEmail.getId() !=id) {
				return false;
		}  }
		return true;	  	
}
	//code for get user based on specific id 
	public User getUserById(Integer id) throws UserNotFoundException {
		try {
		return userRepo.findById(id).get();
		
		}catch(NoSuchElementException ex)
		{
			throw new UserNotFoundException("Could not find any user with ID"+id);
		}
	}
	//code for deleteUser based on id
	public void deleteUser(Integer id) throws UserNotFoundException {
		 Long countById= userRepo.countById(id);
		 if(countById==null || countById==0 ) {
			 throw new UserNotFoundException("Could not find any user with ID"+id);		
		 }
		 userRepo.deleteById(id);
	}
	//code for update status based on id and boolean value
	public void updateUserEnabledStatus(Integer id , boolean enabled) {
		userRepo.updateEnabledStatus(id, enabled);
	}
	
	
	
	
}


