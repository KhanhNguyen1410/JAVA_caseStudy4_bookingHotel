package com.casestudy.case4.service.image;

import com.casestudy.case4.model.Image;
import com.casestudy.case4.repository.image.IImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ImageServiceImpl implements ImageService{
    @Autowired
    private IImageRepository imageRepository;

    @Override
    public Iterable<Image> findAll() {
        return imageRepository.findAll();
    }

    @Override
    public Image save(Image image) {
        return imageRepository.save(image);
    }

    @Override
    public Optional<Image> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public void remove(Long id) {
        imageRepository.deleteById(id);
    }


    @Override
    public List<Image> findImagesByRoomId(Long id) {
        return imageRepository.findAllByRoomId(id);
    }
}
