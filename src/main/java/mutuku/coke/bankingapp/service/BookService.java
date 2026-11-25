package mutuku.coke.bankingapp.service;

import mutuku.coke.bankingapp.BookSpecification;
import mutuku.coke.bankingapp.entity.Book;
import mutuku.coke.bankingapp.repository.BookRepository;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> findAllFiltered(String categoryName,
                                      String languageName,
                                      String formatName,
                                      int maxPrice) {

        Specification<Book> spec = null;

        if (categoryName != null) {
            spec = spec.and(BookSpecification.hasCategoryName(categoryName));
        }

        if (languageName != null) {
            spec = spec.and(BookSpecification.hasLanguageName(languageName));
        }

        if (formatName != null) {
            spec = spec.and(BookSpecification.hasFormatName(formatName));
        }

        if (maxPrice > 0) {
            spec = spec.and(BookSpecification.hasPriceLessThanOrEqualTo(maxPrice));
        }

        return bookRepository.findAll(spec);
    }

}