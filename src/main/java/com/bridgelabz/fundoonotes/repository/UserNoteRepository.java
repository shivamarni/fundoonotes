package com.bridgelabz.fundoonotes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.bridgelabz.fundoonotes.model.NoteInformation;
@Repository
public interface UserNoteRepository extends CrudRepository<NoteInformation,Integer>{
	
	@Query(value="select * from note_information where note_id=?",nativeQuery=true)
	NoteInformation findNoteById(int id);
	
	@Query(value="select * from note_information where user_id=?",nativeQuery=true)
	List<NoteInformation>findNoteByUserId(int id);

}
