package application.photocontest.service;

import application.photocontest.models.Image;
import application.photocontest.repository.contracts.ImageRepository;
import application.photocontest.service.contracts.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;

    @Autowired
    public ImageServiceImpl(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }


    @Override
    public List<Image> getAll() {
        return null;
    }

    @Override
    public Image getById(int id) {
        return null;
    }

    @Override
    public Image create(Image name) {
        return null;
    }

    @Override
    public Image update(Image name,int id) {
        return null;
    }

    @Override
    public void delete(int id) {

    }
}
