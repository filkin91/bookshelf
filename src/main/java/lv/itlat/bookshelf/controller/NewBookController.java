package lv.itlat.bookshelf.controller;


import lv.itlat.bookshelf.persistence.domain.Book;
import lv.itlat.bookshelf.persistence.repository.BookRepository;
import org.apache.log4j.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Named
@RequestScoped
public class NewBookController {

    private static final Logger logger = Logger.getLogger(NewBookController.class);

    @Inject
    private BookRepository bookRepository;

    private Book book = new Book();
    private List<Book> bookList;

    public String createBook() {
        bookRepository.create(book);
        book = new Book();
        return "book-created.xhtml";
    }

    @PostConstruct
    public void init() {
        bookList = bookRepository.retrieve();
    }
//    public void create() {
//        bookRepository.create(book);
//        bookList.add(0, book);
//        book = new Book();
//    }

    public void update() {
        bookRepository.update(bookList);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Update successful"));
    }

    public void delete(Book book) {
        bookRepository.delete(book);
        bookList.remove(book);
    }

    public List<Book> getBookList() {
        return bookList;
    }

    public void setBookList(List<Book> bookList) {
        this.bookList = bookList;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }
}
