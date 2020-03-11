package com.bridgelabz.fundoonotes.repository;
import java.util.List;
import java.util.Optional;

/**
 * @author:shiva
 */
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.bridgelabz.fundoonotes.model.UserDemo;

@Repository
public interface UserRepository extends CrudRepository<UserDemo, Long>{
	@Query(value="select * from user_demo where user_id=?",nativeQuery=true)	
	public String login();

	@Query(value="select * from user_demo where user_id=?",nativeQuery=true)
	public UserDemo getUserById(long id);

	@Query(value="select * from user_demo where email=?",nativeQuery=true)
	public Optional<UserDemo> getUserByEmail(String email);
    
	@Query(value = "update user_demo set password=? where email=?", nativeQuery = true)
	UserDemo forgotPassword(String password, String email);

	@Query(value = "insert into user_demo (date, email, is_verified, name, mobile, password, user_id) values (?, ?, ?, ?, ?, ?, ?)", nativeQuery = true)
	UserDemo register(UserDemo user);

	@Query(value = "update user_demo set is_verified=true where user_id=?", nativeQuery = true)
	void verify(long id);
	
	@Query(value="select * from user_demo_collabrate where user_id=?",nativeQuery=true)
	public List<UserDemo> getCollobaraterById(long id);
	
}

