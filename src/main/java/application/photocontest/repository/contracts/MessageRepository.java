package application.photocontest.repository.contracts;


import application.photocontest.models.Message;
import application.photocontest.models.User;

import java.util.List;

public interface MessageRepository {


    Message getById(int id);

    Message create(Message message);

    void delete(int id);

}
