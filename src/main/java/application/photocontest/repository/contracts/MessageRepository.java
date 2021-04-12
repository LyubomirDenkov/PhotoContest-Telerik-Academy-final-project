package application.photocontest.repository.contracts;

import application.photocontest.models.Message;

import javax.transaction.Transactional;

public interface MessageRepository {
    Message getById(int id);


    Message create(Message message);


    void delete(int id);
}
