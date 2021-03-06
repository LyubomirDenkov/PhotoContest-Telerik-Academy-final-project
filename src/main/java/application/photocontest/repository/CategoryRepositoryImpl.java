package application.photocontest.repository;

import application.photocontest.exceptions.EntityNotFoundException;
import application.photocontest.models.Category;
import application.photocontest.repository.contracts.CategoryRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
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
    public Category create(Category category) {
        try (Session session = sessionFactory.openSession()) {

            session.save(category);

            return category;
        }
    }


    @Override
    public List<Category> getAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from Category ", Category.class).list();
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

    @Override
    public Category getByName(String name) {
        try (Session session = sessionFactory.openSession()) {

            Query<Category> query = session.createQuery("from Category " +
                    "where name = :name ", Category.class);

            query.setParameter("name", name);

            List<Category> result = query.list();

            if (result.isEmpty()) {

                throw new EntityNotFoundException("Category", "name", name);

            }
            return result.get(0);
        }
    }
}
