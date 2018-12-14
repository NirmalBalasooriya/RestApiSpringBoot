package com.nirmal.springbootrest.dto;


import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nirmal.springbootrest.model.Book;

/**
 * Book DTO
 * 
 * @author Nirmal Balasooriya
 *
 */
@Repository
public interface IBookDto extends CrudRepository<Book,Integer> {
	public Book getBookByIsbmNumber(String isbmNumber);
	
	@Transactional
	@Modifying
	@Query(value="delete from Book u where u.isbmNumber = ?1")
    void deleteByIsbmNumber(@Param("isbmNumber") String isbmNumber);
}
