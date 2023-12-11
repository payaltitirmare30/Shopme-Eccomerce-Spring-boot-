package com.shopme.admin.user;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import com.shopme.common.entity.User;
import com.shopme.common.entity.Role;

@DataJpaTest
@AutoConfigureTestDatabase(replace= Replace.NONE)
@Rollback(false)
public class UserRepositoryTest {    

	@Autowired
	private UserRepository repo;
	@Autowired
	private TestEntityManager entityManager;    //this class is provided by spring data jpa for unit testing with repository
	
	@Test
	public void testCreateUser()      
	{
	Role roleAdmin=	entityManager.find(Role.class, 1);
	User user = new User("mayuri@gmail.com","mayuri@123","mayuri","titirmare");
	user.addRole(roleAdmin);	
    User savedUser = repo.save(user);
    assertThat(savedUser.getId()).isGreaterThan(0);   
	}
	
	//method to test user with two roles 
	@Test
	public void testCreateNewUserwithTwoRoles() {   //if this method gives error then enter another data bcz previous data is  already present thats why its showing error
		User userRam = new User("sita@gmail.com","sita","sita","Kumar");
		Role roleEditor = new Role(3);
		
		Role roleAssistance = new Role(5);
		
		userRam.addRole(roleEditor);
		userRam.addRole(roleAssistance);
		
		User savedUser = repo.save(userRam);
		assertThat(savedUser.getId()).isGreaterThan(0);	
	}
	
	@Test
	public void testListAllUsers()
	{
		Iterable<User> listUser = repo.findAll();	
	    listUser.forEach(user -> System.out.println(user));	    
	}
	@Test
	public void testUserById()
	{
		User user = repo.findById(3).get();
		System.out.println(user);
		assertThat(user).isNotNull();
	}
	
	@Test
	public void testUpdateUserDetails() {
		User username= repo.findById(3).get();	
		username.setEnabled(true);
		username.setEmail("programming@gmail.com");
		repo.save(username);
	}
	
	@Test
	public void testUpdateUserRole() {
		User userravi = repo.findById(3).get();	
		Role roleEditor = new Role(3);
		Role roleSalesperson = new Role(2);
		userravi.getRoles().remove(roleEditor);
		userravi.addRole(roleSalesperson);
		repo.save(userravi);
	}
	
	@Test
	public void testDeleteUser()
	{
		repo.deleteById(2);		
	}
	
	@Test
	public void testGetUserByEmail() {
		String email = "nam@codejava.net";
		User user = repo.getUserByEmail(email);
		assertThat(user).isNotNull();
	}
	
	@Test
	public void testCountById() {
		Integer id =3;
		Long countById = repo.countById(id);
		assertThat(countById).isNotNull().isGreaterThan(0);	
	}
	
	@Test
	public void testDisabledUser() {
		Integer id =4;
		repo.updateEnabledStatus(id,false);
	}
	
	@Test
	public void testEnabledUser() {
		Integer id =4;
		repo.updateEnabledStatus(id,true);
	}
	
	/*
	@Test
	public void testListFirstPage() {
		int pageNumber =2;
		int pageSize=4;
		Pageable pageable = PageRequest.of(pageNumber, pageSize);
		Page<User> page = repo.findAll(pageable);
		List<User> listUsers = page.getContent();
		listUsers.forEach(user -> System.out.println(user));
		
		assertThat(listUsers.size()).isEqualTo(pageSize);
		
	} */
	
}
