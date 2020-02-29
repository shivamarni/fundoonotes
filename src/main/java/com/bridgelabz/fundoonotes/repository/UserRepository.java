package com.bridgelabz.fundoonotes.repository;
/**
 * @author:shiva
 */
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.bridgelabz.fundoonotes.model.UserDemo;

@Repository
public interface UserRepository extends CrudRepository<UserDemo, Integer>{
	@Query(value="select * from fundoonotes.user where id=?",nativeQuery=true)	
	public String login();

	@Query(value="insert into fundoonotes.user(id,name,email,mobile,password,isVerified,date) values(?,?,?,?,?,?,?)",nativeQuery=true)
	public UserDemo register(UserDemo user);

	@Query(value="update fundoonotes.user set password=? where email=?",nativeQuery=true)
	public UserDemo forgotPassword(UserDemo user);

	@Query(value="select * from fundoonotes.user where id=?",nativeQuery=true)
	public UserDemo getUserById(int id);

	@Query(value="select * from fundoonotes.user where email=?",nativeQuery=true)
	public UserDemo getUserByEmail(String email);

	@Query(value="update fundoonotes.user set isVerified=true where id=?",nativeQuery=true)
	public void verify(Integer id);

}

