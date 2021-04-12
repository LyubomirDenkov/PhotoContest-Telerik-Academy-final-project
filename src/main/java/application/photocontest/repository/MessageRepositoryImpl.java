package application.photocontest.repository;


import application.photocontest.exceptions.EntityNotFoundException;
import application.photocontest.models.Message;
import application.photocontest.repository.contracts.MessageRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;


@Repository
public class MessageRepositoryImpl implements MessageRepository {


    private final SessionFactory sessionFactory;

    @Autowired
    public MessageRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    @Override
    public Message getById(int id) {
        try(Session session = sessionFactory.openSession()){
            Message message = session.get(Message.class,id);
            if (message == null){
                throw new EntityNotFoundException("Message",id);
            }
            return message;
        }
    }

    @Transactional
    @Override
    public Message create(Message message) {
        try (Session session = sessionFactory.openSession()) {
            session.save(message);
            return getById(message.getId());
        }
    }

    @Transactional
    @Override
    public void delete(int id) {
        Message message = getById(id);
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.delete(message);
            session.getTransaction().commit();
        }
    }
}
