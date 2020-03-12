package com.bridgelabz.fundoonotes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.bridgelabz.fundoonotes.model.NoteInformation;
@Repository
public interface UserNoteRepository extends CrudRepository<NoteInformation,Long>{
	
	@Query(value="select * from note_information where note_id=?",nativeQuery=true)
	NoteInformation findNoteById(long id);
	
	@Query(value="select * from note_information where user_id=?",nativeQuery=true)
	List<NoteInformation>findNoteByUserId(long id);

	@Query(value="select * from note_information where user_id=? AND is_trashed =1",nativeQuery = true)
	List<NoteInformation> restoreNote(long userid);
	
	@Query(value="select * from note_information where user_id=? AND is_archeived =1",nativeQuery = true)
	List<NoteInformation> getArchievedNotes(long userid);
	
	@Query(value="select * from  note_information where user_id=? AND is_pinned =1",nativeQuery = true)
	List<NoteInformation> getPinnededNotes(long userid);


}
