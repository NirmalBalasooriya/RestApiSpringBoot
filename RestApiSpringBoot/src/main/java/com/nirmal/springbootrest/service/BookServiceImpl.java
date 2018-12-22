package com.nirmal.springbootrest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nirmal.springbootrest.aop.TimeTrack;
import com.nirmal.springbootrest.dto.IBookDto;
import com.nirmal.springbootrest.model.Book;

/**
 * Actual implementation of IBookService
 * 
 * @author Nirmal Balasooriya
 *
 */
@Service
public class BookServiceImpl implements IBookService {

	@Autowired
	IBookDto bookDto;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.nirmal.springbootrest.service.IBookService#saveOrUpdateBook(com.nirmal.
	 * springbootrest.model.Book)
	 */
	public String saveOrUpdateBook(Book book) {
		
		Book bookindb=bookDto.getBookByIsbmNumber(book.getIsbmNumber());
		if(bookindb!=null) {
			book.setId(bookindb.getId());
		}		
		bookindb = bookDto.save(book);
		
		if (bookindb != null)
			return bookindb.getIsbmNumber();
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.nirmal.springbootrest.service.IBookService#getBook(String)
	 */
	public Book getBook(String isbm) {
		Book saveBook = bookDto.getBookByIsbmNumber(isbm);
		return saveBook;
	}

	@Override
	@TimeTrack
	public void deleteBook(String isbm) {
		bookDto.deleteByIsbmNumber(isbm);
	}

}
