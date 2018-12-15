
import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nirmal.springbootrest.AppInitializer;
import com.nirmal.springbootrest.controller.Response;
import com.nirmal.springbootrest.model.Book;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = AppInitializer.class)
@WebAppConfiguration
public class TestRestAPI {

	protected MockMvc mvc;

	@Autowired
	WebApplicationContext webApplicationContext;

	@Before
	public void setUp() {
		mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	protected String mapToJson(Object obj) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(obj);
	}

	protected <T> T mapFromJson(String json, Class<T> clazz)
			throws JsonParseException, JsonMappingException, IOException {

		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.readValue(json, clazz);
	}
	
	
	@Test
	public void bookEqualsMethodTest() throws Exception {
		Book book = new Book("1111", "Singapore");
		book.setAuther("Nirmal Balasooriya");
		book.setDescription("About country development");
		Book secondBook=null;
		assertEquals(false, book.equals(secondBook));
		
		assertEquals(false, book.equals(""));
		
		secondBook=new Book("2222", "Singapore","","");
		assertEquals(false, book.equals(secondBook));
		
		secondBook=new Book(null, "Singapore","","");
		assertEquals(false, book.equals(secondBook));
		
		secondBook=new Book("1111", "Singapore2","","");
		assertEquals(true, book.equals(secondBook));
	}
	
	@Test
	public void searchNonExistingBook() throws Exception {
		String uri = "/findBook/" + "0011";
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON_VALUE))
				.andReturn();

		int statusCode = mvcResult.getResponse().getStatus();
		assertEquals(200, statusCode);
		String content = mvcResult.getResponse().getContentAsString();
		Response<Book> response = mapFromJson(content, Response.class);
		assertEquals("0", response.getCode());
	}

	@Test
	public void saveBookWithoutMandotoryFieldIsbn() throws Exception {
		String uri = "/saveOrUpdate";
		Book book = new Book("", "Singapore");
		book.setAuther("Nirmal Balasooriya");
		book.setDescription("About country development");

		String inputJson = mapToJson(book);
		MvcResult mvcResult = mvc.perform(
				MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
				.andReturn();

		int statusCode = mvcResult.getResponse().getStatus();
		assertEquals(200, statusCode);
		String content = mvcResult.getResponse().getContentAsString();
		Response response = mapFromJson(content, Response.class);
		assertEquals("0", response.getCode());
		assertEquals("Book save faild ISBM mandotory", response.getDesc());
	}

	@Test
	public void saveBookWithNullMandotoryFieldIsbn() throws Exception {
		String uri = "/saveOrUpdate";
		Book book = new Book(null, "Singapore");
		book.setAuther("Nirmal Balasooriya");
		book.setDescription("About country development");

		String inputJson = mapToJson(book);
		MvcResult mvcResult = mvc.perform(
				MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
				.andReturn();

		int statusCode = mvcResult.getResponse().getStatus();
		assertEquals(200, statusCode);
		String content = mvcResult.getResponse().getContentAsString();
		Response response = mapFromJson(content, Response.class);
		assertEquals("0", response.getCode());
		assertEquals("Book save faild ISBM mandotory", response.getDesc());
	}

	@Test
	public void saveBookWithoutMandotoryFieldName() throws Exception {
		String uri = "/saveOrUpdate";
		Book book = new Book("1234", "");
		book.setAuther("Nirmal Balasooriya");
		book.setDescription("About country development");

		String inputJson = mapToJson(book);
		MvcResult mvcResult = mvc.perform(
				MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
				.andReturn();

		int statusCode = mvcResult.getResponse().getStatus();
		assertEquals(200, statusCode);
		String content = mvcResult.getResponse().getContentAsString();
		Response response = mapFromJson(content, Response.class);
		assertEquals("0", response.getCode());
		assertEquals("Book save failed name mandotory", response.getDesc());
	}

	@Test
	public void saveBookWithoutCorrectBookDetails() throws Exception {
		String uri = "/saveOrUpdate";
		Book book = new Book("1234", "Future");
		book.setAuther("Nirmal Balasooriya");
		book.setDescription("About country development");

		String inputJson = mapToJson(book);
		MvcResult mvcResult = mvc.perform(
				MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
				.andReturn();

		int statusCode = mvcResult.getResponse().getStatus();
		assertEquals(200, statusCode);
		String content = mvcResult.getResponse().getContentAsString();
		Response response = mapFromJson(content, Response.class);
		assertEquals("1", response.getCode());
		assertEquals("Book save successful", response.getDesc());

	}

	@Test
	public void saveBookWithoutCorrectBookDetailsAndDeleteWithNullIsbm() throws Exception {
		String uri = "/saveOrUpdate";
		Book book = new Book("1234", "Future");
		book.setAuther("Nirmal Balasooriya");
		book.setDescription("About country development");

		String inputJson = mapToJson(book);
		MvcResult mvcResult = mvc.perform(
				MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
				.andReturn();

		int statusCode = mvcResult.getResponse().getStatus();
		assertEquals(200, statusCode);
		String content = mvcResult.getResponse().getContentAsString();
		Response response = mapFromJson(content, Response.class);
		assertEquals("1", response.getCode());
		assertEquals("Book save successful", response.getDesc());

		uri = "/deleteBook";
		inputJson = "{\"isbm\":\"\"}";
		mvcResult = mvc.perform(
				MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
				.andReturn();

		statusCode = mvcResult.getResponse().getStatus();
		assertEquals(200, statusCode);
		content = mvcResult.getResponse().getContentAsString();
		response = mapFromJson(content, Response.class);
		assertEquals("0", response.getCode());
		assertEquals("Book delete faild ISBM mandotory", response.getDesc());
	}

	@Test
	public void saveBookWithoutCorrectBookDetailsAndDelete() throws Exception {
		String uri = "/saveOrUpdate";
		Book book = new Book("1234", "Future");
		book.setAuther("Nirmal Balasooriya");
		book.setDescription("About country development");

		String inputJson = mapToJson(book);
		MvcResult mvcResult = mvc.perform(
				MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
				.andReturn();

		int statusCode = mvcResult.getResponse().getStatus();
		assertEquals(200, statusCode);
		String content = mvcResult.getResponse().getContentAsString();
		Response response = mapFromJson(content, Response.class);
		assertEquals("1", response.getCode());
		assertEquals("Book save successful", response.getDesc());

		uri = "/deleteBook";
		inputJson = "{\"isbm\":\"1234\"}";
		mvcResult = mvc.perform(
				MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
				.andReturn();

		statusCode = mvcResult.getResponse().getStatus();
		assertEquals(200, statusCode);
		content = mvcResult.getResponse().getContentAsString();
		response = mapFromJson(content, Response.class);
		assertEquals("1", response.getCode());
		assertEquals("Book delete successful", response.getDesc());
	}
}