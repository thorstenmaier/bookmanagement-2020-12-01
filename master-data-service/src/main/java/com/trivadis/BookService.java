package com.trivadis;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class BookService {

    private BookRepository bookRepository;
    private JmsTemplate jmsTemplate;

    public BookService(BookRepository bookRepository, JmsTemplate jmsTemplate) {
        this.bookRepository = bookRepository;
        this.jmsTemplate = jmsTemplate;
    }

    public void createBook(Book book) {
        bookRepository.save(book);
        jmsTemplate.convertAndSend("newBookTopic", new CreatedBookEvent(book.getName(), book.getAuthor().getName()));
    }
}
