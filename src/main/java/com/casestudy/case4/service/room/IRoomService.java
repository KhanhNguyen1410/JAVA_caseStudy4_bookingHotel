package com.casestudy.case4.service.room;

import com.casestudy.case4.model.Room;
import com.casestudy.case4.service.GeneralService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

public interface IRoomService extends GeneralService<Room> {
    Page<Room>  findAllByHotelId(@Param("id") Long id, Pageable pageable);
    Room findAllById(Long id);
//    Page<Room> findAllByHotelId(Long id, Pageable pageable);
}
