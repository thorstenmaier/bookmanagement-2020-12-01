package com.trivadis;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class BookService {

    private BookRepository bookRepository;
    private AuthorRepository authorRepository;
    private JmsTemplate jmsTemplate;

    public BookService(BookRepository bookRepository, AuthorRepository authorRepository, JmsTemplate jmsTemplate) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.jmsTemplate = jmsTemplate;
    }

    @Transactional
    public void createBook(Book book) {
        Author authorFromDb = authorRepository.findByName(book.getAuthor().getName());
        if (authorFromDb != null) {
            book.setAuthor(authorFromDb);
        }
        bookRepository.save(book);
        jmsTemplate.convertAndSend("newBookTopic", new CreatedBookEvent(book.getName(), book.getAuthor().getName()));
    }
}
