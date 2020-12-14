package com.casestudy.case4.repository.image;

import com.casestudy.case4.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IImageRepository extends JpaRepository<Image,Long> {
//    @Transactional
//    @Modifying
    @Query(value = "select * from image i where i.room_id = :id", nativeQuery = true)
    List<Image> findAllByRoomId(@Param("id")Long id);

}
