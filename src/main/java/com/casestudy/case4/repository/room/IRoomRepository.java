package com.casestudy.case4.repository.room;

import com.casestudy.case4.model.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface IRoomRepository extends JpaRepository<Room, Long> {
//    Page<Room> findAllByHotelId(Long id ,Pageable pageable);
//    @Transactional
//    @Modifying
    @Query(value = "select * from room r where r.hotel_id = :id", nativeQuery = true)
    Page<Room> findAllByHotelId(@Param("id") Long id, Pageable pageable);
    Room findAllById(Long id);
}
