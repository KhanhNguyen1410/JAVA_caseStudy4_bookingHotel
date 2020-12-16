package com.casestudy.case4.service.hotel;

import com.casestudy.case4.model.Hotel;
import com.casestudy.case4.repository.hotel.IHotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HotelServiceImpl implements IHotelService {
    @Autowired
    private IHotelRepository hotelRepository;

    @Override
    public Iterable<Hotel> findAll() {
        return hotelRepository.findAll();
    }

    @Override
    public Hotel save(Hotel hotel) {
        return hotelRepository.save(hotel);
    }

    @Override
    public Optional<Hotel> findById(Long id) {
        return hotelRepository.findById(id);
    }

    @Override
    public void remove(Long id) {
        hotelRepository.remove(id);
    }

    @Override
    public Page<Hotel> findAllByStatusIsFalse(Pageable pageable) {
        return hotelRepository.findAllByStatusIsFalse(pageable);
    }

    @Override
    public Hotel findAllById(Long id) {
        return hotelRepository.findAllById(id);
    }

    @Override
    public Page<Hotel> findAllByProvince(Long id, Pageable pageable) {
        return hotelRepository.findAllByProvince(id,pageable);
    }

    @Override
    public Page<Hotel> findAllByUser(Long id, Pageable pageable) {
        return hotelRepository.findAllByUser(id, pageable);
    }

    @Override
    public Page<Hotel> findAllByNameContaining(String name, Pageable pageable) {
        return hotelRepository.findAllByStatusIsFalseAndNameContaining(name,pageable);
    }

//    @Override
//    public Page<Hotel> findAllByNameContaining(String name) {
//        return hotelRepository.findAllByNameContaining(name);
//    }

}
