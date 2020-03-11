package com.bridgelabz.fundoonotes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.bridgelabz.fundoonotes.model.UserLabel;
@Repository
public interface LabelRepository extends CrudRepository<UserLabel,Long>{
	@Query(value="select * from  user_label where lable_id=?",nativeQuery=true)
	UserLabel findLabelById(long id);
	
	@Query(value="select * from  user_label where user_id=?",nativeQuery=true)
	List<UserLabel> findLabelByUserId(long userid);

}
