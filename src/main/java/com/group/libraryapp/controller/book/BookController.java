package com.group.libraryapp.controller.book;

import com.group.libraryapp.service.book.BookService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookController {
    private final BookService bookService;

    // BookService를 스프링 빈으로 등록하면서 생성자를 통해
    // 스프링 컨테이너가 대신 BookService를 넣어주도록 바뀐 것

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping("/book")
    public void saveBook(){
        bookService.saveBook();
    }
}
