package com.bookstore.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import com.bookstore.entity.Book;
import com.bookstore.entity.MyBookList;
import com.bookstore.service.BookService;
import com.bookstore.service.MyBookService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;




@Controller
public class BookController {

	@Autowired
	private BookService bservice;

	@Autowired
	private MyBookService service;

	@GetMapping("/")
	public String home() {
		return "home";
	}

	@GetMapping("/book_register")
	public String bookRegister() {
		return "bookRegister";
	}

	@GetMapping("/available_books")
	public ModelAndView getAllBooks() {
		List<Book> list = bservice.getAllBooks();
		// ModelAndView m = new ModelAndView();
		// m.setViewName("bookList");
		// m.addObject("book", list);    // instead of writing above three lines we can use a single line.
		return new ModelAndView("bookList", "book", list);
	}

	@PostMapping("/save")
	public String addBook(@ModelAttribute Book b){

		bservice.save(b);
		return "redirect:/available_books";

	}

	@GetMapping("/my_books")
	public String getMyBooks(Model model){

		List<MyBookList> list = service.getAllMyBooks();
		model.addAttribute("book", list);
		return "myBooks";
	}
	
	@RequestMapping("/mylist/{id}")
	public String getMyList(@PathVariable("id") int id) {
		System.out.println("Book Id ====> "+id);
		Book b = bservice.getBookById(id);
		System.out.println("Complete Book ====> "+b.getName());
		MyBookList mb = new MyBookList(b.getId(), b.getName(), b.getAuthor(), b.getPrice());
		System.out.println("Book Object ====> " + mb);
		service.saveMyBooks(mb);
		return "redirect:/my_books";
	}

	@RequestMapping("/editBook/{id}")
	public String editBook(@PathVariable("id") int id, Model model) {
		Book b = bservice.getBookById(id);
		model.addAttribute("book", b);

		return "bookEdit";
	}

	@RequestMapping("/deleteBook/{id}")
	public String deleteBook(@PathVariable("id") int id){
		bservice.deleteById(id);
		return "redirect:/available_books";
	}
	
	

}
