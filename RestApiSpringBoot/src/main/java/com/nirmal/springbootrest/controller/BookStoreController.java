package com.nirmal.springbootrest.controller;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nirmal.springbootrest.model.Book;
import com.nirmal.springbootrest.service.IBookService;

/**
 * Represent the REST controller for the application
 * 
 * @author Nirmal Balasooriya
 *
 */

@Controller
@Component
public class BookStoreController {

	Logger logger = LoggerFactory.getLogger(BookStoreController.class);

	@Autowired
	IBookService iBookService;

	@GetMapping("/findBook/{isbm}")
	@ResponseBody
	public Response<Book> findBook(@PathVariable("isbm")String isbm) {
		logger.info("Book find isbm : " + isbm);
		Book book = iBookService.getBook(isbm);
		Response<Book> response = new Response<Book>("0", "Book can not be found", null);
		if (book != null)
			response = new Response<Book>("1", "Book found", book);
		return response;
	}

	@RequestMapping(method = RequestMethod.POST, value = "/saveOrUpdate")
	@ResponseBody
	public Response<String> saveBookInJSON(@RequestBody Book book) {
		logger.info("Book saved request" + book);
		Response<String> response;
		if ((book.getIsbmNumber() == null) || (book.getIsbmNumber() != null && book.getIsbmNumber().trim().isEmpty())) {
			logger.info("Book saved fail ISBM_IS_MANDOTORY");
			response = new Response<String>("0", "Book save faild ISBM mandotory", null);
		} else if ((book.getName() == null) || (book.getName() != null && book.getName().trim().isEmpty())) {
			logger.info("Book saved failed BOOK_NAME_IS_MANDOTORY");
			response = new Response<String>("0", "Book save failed name mandotory", null);
		} else {
			try {
				setDateTime(book);
				String savedbook = iBookService.saveOrUpdateBook(book);
				response = new Response<String>("1", "Book save successful", savedbook);
				logger.info("Book saved sucess" + savedbook);
			} catch (Exception e) {
				logger.error("Book saved failed" + e.getMessage());
				response = new Response<String>("1", "Book save successful", null);
			}
		}
		return response;
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/deleteBook")
	@ResponseBody
	public Response<String> deleteBookInJSON(@RequestBody String isbm) throws IOException {
		logger.info("Book selete request" + isbm);
		ObjectMapper mapper = new ObjectMapper();
	    JsonNode actualObj = mapper.readTree(isbm);
		Response<String> response;
		isbm = actualObj.get("isbm").asText();
		if ((isbm == null) || (isbm != null && isbm.trim().isEmpty())) {
			logger.info("Book saved fail ISBM_IS_MANDOTORY");
			response = new Response<String>("0", "Book delete faild ISBM mandotory", null);
		} else {
			try {
				iBookService.deleteBook(isbm);
				response = new Response<String>("1", "Book delete successful", isbm);
				logger.info("Book delete sucess" + isbm);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("Book delete failed" + e.getMessage());
				response = new Response<String>("0", "Book delete fail", null);
			}
		}
		return response;
	}

	private void setDateTime(Book book){
		Date date= Calendar.getInstance().getTime();
		book.setTimeStamp(date);
		book.setDate(date);
		book.setTime(date);
	}
}
