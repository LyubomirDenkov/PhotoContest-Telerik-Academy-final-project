package application.photocontest.repository;

import application.photocontest.exceptions.EntityNotFoundException;
import application.photocontest.models.Category;
import application.photocontest.repository.contracts.CategoryRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CategoryRepositoryImpl implements CategoryRepository {


    private final SessionFactory sessionFactory;

    @Autowired
    public CategoryRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Category create(Category name) {
        return null;
    }


    @Override
    public List<Category> getAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from Category ",Category.class).list();
        }
    }

    @Override
    public Category getById(int id) {
        try (Session session = sessionFactory.openSession()) {
            Category category = session.get(Category.class, id);

            if (category == null) {

                throw new EntityNotFoundException("Category", id);
            }
            return category;
        }
    }
}
