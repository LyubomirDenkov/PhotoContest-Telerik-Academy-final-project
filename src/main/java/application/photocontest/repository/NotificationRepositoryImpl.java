package application.photocontest.repository;


import application.photocontest.exceptions.EntityNotFoundException;
import application.photocontest.models.Notification;
import application.photocontest.repository.contracts.NotificationRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;


@Repository
public class NotificationRepositoryImpl implements NotificationRepository {


    private final SessionFactory sessionFactory;

    @Autowired
    public NotificationRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    @Override
    public Notification getById(int id) {
        try(Session session = sessionFactory.openSession()){
            Notification notification = session.get(Notification.class,id);
            if (notification == null){
                throw new EntityNotFoundException("Message",id);
            }
            return notification;
        }
    }

    @Transactional
    @Override
    public Notification create(Notification notification) {
        try (Session session = sessionFactory.openSession()) {
            session.save(notification);
            return getById(notification.getId());
        }
    }

    @Transactional
    @Override
    public void delete(int id) {
        Notification notification = getById(id);
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.delete(notification);
            session.getTransaction().commit();
        }
    }
}
