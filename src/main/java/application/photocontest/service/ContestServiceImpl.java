package application.photocontest.service;

import application.photocontest.models.Contest;
import application.photocontest.repository.contracts.ContestRepository;
import application.photocontest.service.contracts.ContestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContestServiceImpl implements ContestService {

    private final ContestRepository contestRepository;

    @Autowired
    public ContestServiceImpl(ContestRepository contestRepository) {
        this.contestRepository = contestRepository;
    }

    @Override
    public List<Contest> getAll() {
        return null;
    }

    @Override
    public Contest getById(int id) {
        return null;
    }

    @Override
    public Contest create(Contest name) {
        return null;
    }

    @Override
    public Contest update(Contest name) {
        return null;
    }

    @Override
    public void delete(int id) {

    }
}
