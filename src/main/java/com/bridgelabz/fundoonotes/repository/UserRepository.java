package com.bridgelabz.fundoonotes.repository;
import java.util.List;

/**
 * @author:shiva
 */
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.bridgelabz.fundoonotes.model.UserDemo;

@Repository
public interface UserRepository extends CrudRepository<UserDemo, Integer>{
	@Query(value="select * from user_demo where user_id=?",nativeQuery=true)	
	public String login();

	@Query(value="select * from user_demo where user_id=?",nativeQuery=true)
	public UserDemo getUserById(int id);

	@Query(value="select * from user_demo where email=?",nativeQuery=true)
	public UserDemo getUserByEmail(String email);
    


}

