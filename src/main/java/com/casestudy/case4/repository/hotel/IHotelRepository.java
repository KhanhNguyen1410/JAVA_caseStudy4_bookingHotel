package com.casestudy.case4.repository.hotel;

//import com.example.demo.model.Hotel;
import com.casestudy.case4.model.Hotel;
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
public interface IHotelRepository extends JpaRepository<Hotel, Long> {
    Page<Hotel> findAllByStatusIsFalse(Pageable pageable);
    Hotel findAllById(Long id);
    @Transactional
    @Modifying
    @Query(value = "UPDATE hotel h set status =1 where h.id = :id", nativeQuery = true)
    void remove(@Param("id") Long id);

    @Query(value = "select * from hotel r where r.province_id = :id", nativeQuery = true)
    Page<Hotel> findAllByProvince(@Param("id") Long id, Pageable pageable);
    @Query(value = "select * from hotel h where h.user_id = :id", nativeQuery = true)
    Page<Hotel> findAllByUser(@Param("id") Long id, Pageable pageable);
//    Page<Hotel> findAllByNameContaining(String name);
}
