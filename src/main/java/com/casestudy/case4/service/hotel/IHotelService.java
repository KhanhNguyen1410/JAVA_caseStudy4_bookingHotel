package com.casestudy.case4.service.hotel;


import com.casestudy.case4.model.Hotel;
import com.casestudy.case4.service.GeneralService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

public interface IHotelService extends GeneralService<Hotel> {
    Page<Hotel> findAllByStatusIsFalse(Pageable pageable);
    Hotel findAllById(Long id);
    Page<Hotel> findAllByProvince(Long id, Pageable pageable);
    Page<Hotel> findAllByUser(Long id, Pageable pageable);
    Page<Hotel> findAllByNameContaining(String name);

}
