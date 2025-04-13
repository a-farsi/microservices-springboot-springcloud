package org.afa.bookservice.web;

import org.afa.bookservice.dao.BookRepository;
import org.afa.bookservice.entities.Book;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RefreshScope
@RestController
public class BookRestService {
	@Value("${xParam}")
	private int xParam;
	@Value("${yParam}")
	private int yParam;
	@Value("${me}")
	private String me;

	private final BookRepository bookRepository;

	public BookRestService(BookRepository bookRepository) {
		this.bookRepository = bookRepository;
	}
	@GetMapping("/myConfig")
	public Map<String, Object> myConfig() {
		Map<String, Object> params = new HashMap<>();
		params.put("xParam", xParam);
		params.put("yParam", yParam);
		params.put("me", me);
		params.put("threadName", Thread.currentThread().getName());
		return params;
	}


	@GetMapping("/books")
	public List<Book> getAllBooks() {
		return bookRepository.findAll();
	}

	@GetMapping("/books/{id}")
	public Book getBookById(@PathVariable Long id) {
		return bookRepository.findById(id).orElse(null);
	}
}
