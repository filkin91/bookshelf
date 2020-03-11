package lv.itlat.bookshelf.persistence.repository;


import lv.itlat.bookshelf.persistence.domain.Book;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;
import javax.transaction.Transactional;
import java.util.List;

@RequestScoped
@Transactional
public class BookRepository {

    @PersistenceContext(unitName = "bookshelfPU")
    private EntityManager em;

    public EntityManager getEntityManager() {
        return em;
    }

    @Transactional
    public Book create(Book book) {
        getEntityManager().persist(book);
        return book;
    }

    public void update(List<Book> books) {
        for (Book book : books) {
            getEntityManager().merge(book);
        }
    }

    public void delete(Book book) {
        if (getEntityManager().contains(book)) {
            getEntityManager().remove(book);
        } else {
            Book managedBook = getEntityManager().find(Book.class, book.getId());
            if (managedBook != null) {
                getEntityManager().remove(managedBook);
            }
        }
    }

    public List<Book> retrieve() {
        CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(Book.class));
        return getEntityManager().createQuery(cq).getResultList();
    }
}
