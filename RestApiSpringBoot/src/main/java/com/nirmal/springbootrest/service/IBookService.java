package com.nirmal.springbootrest.service;

import org.springframework.stereotype.Component;

import com.nirmal.springbootrest.model.Book;

/**
 * BookService interfacce with abstract methods about Book Service
 * 
 * @author Nirmal Balasooriya
 *
 */
@Component
public interface IBookService {
	/**
	 * to save or update the book on database
	 * 
	 * @param book
	 * @return bookIsbm
	 */
	public String saveOrUpdateBook(Book book);

	/**
	 * to retrive the book from database
	 * 
	 * @param String String
	 * @return book
	 */
	public Book getBook(String isbm);
	

	/**
	 * Delete Book on database
	 * @param isbm
	 */
	public void deleteBook(String isbm);
}