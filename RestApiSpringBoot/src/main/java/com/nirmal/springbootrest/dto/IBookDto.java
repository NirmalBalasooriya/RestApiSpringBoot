/**
 * 
 */
package com.nirmal.springbootrest.dto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nirmal.springbootrest.model.Book;

/**
 * Book DTO
 * 
 * @author Nirmal Balasooriya
 *
 */
@Repository
public interface IBookDto extends JpaRepository<Book, Long> {
	public Book getBookByIsbmNumber(String isbmNumber);
	
}
