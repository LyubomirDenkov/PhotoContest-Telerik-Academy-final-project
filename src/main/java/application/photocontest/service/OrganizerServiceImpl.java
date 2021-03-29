package application.photocontest.service;

import application.photocontest.models.Organizer;
import application.photocontest.models.User;
import application.photocontest.repository.contracts.OrganizerRepository;
import application.photocontest.service.contracts.OrganizerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrganizerServiceImpl implements OrganizerService {

    private final OrganizerRepository organizerRepository;

    @Autowired
    public OrganizerServiceImpl(OrganizerRepository organizerRepository) {
        this.organizerRepository = organizerRepository;
    }

    @Override
    public List<Organizer> getAll(User user) {
        return organizerRepository.getAll();
    }


    @Override
    public Organizer getById(User user, int id) {
        return organizerRepository.getById(id);
    }

    @Override
    public Organizer create(User user, Organizer type) {
        return null;
    }

    @Override
    public Organizer update(User user, Organizer secondType) {
        return null;
    }

    @Override
    public void delete(User user, int id) {


    }
}
