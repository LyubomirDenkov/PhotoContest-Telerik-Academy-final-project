package application.photocontest.service;

import application.photocontest.models.Message;
import application.photocontest.models.User;
import application.photocontest.repository.contracts.MessageRepository;
import application.photocontest.service.contracts.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;

    @Autowired
    public MessageServiceImpl(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @Override
    public Message getById(User user, int id) {
        return messageRepository.getById(id);
    }

    @Override
    public Message create(Message message) {
        Message createdMessage = messageRepository.create(message);
        return createdMessage;
    }

    @Override
    public void delete(int messageId) {
        messageRepository.delete(messageId);
    }
}
