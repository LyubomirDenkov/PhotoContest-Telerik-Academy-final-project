package application.photocontest.service.contracts;

import application.photocontest.models.Message;
import application.photocontest.models.User;

public interface MessageService {
    Message getById(User user, int id);

    Message create(Message message);

    void delete(int messageId);
}
