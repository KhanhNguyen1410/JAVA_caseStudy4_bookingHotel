package com.casestudy.case4.repository.user;



import com.casestudy.case4.model.Hotel;
import com.casestudy.case4.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> {
    User findByUserName(String userName);
    User findByUserNameAndStatusIsFalse(String userName);
    Page<User> findAllByStatusIsFalse(Pageable pageable);
    Page<User> findAllByStatusIsTrue(Pageable pageable);
    User findAllById(Long id);
    @Transactional
    @Modifying
    @Query(value = "UPDATE user h set status =1 where h.id = :id", nativeQuery = true)
    void remove(@Param("id") Long id);
    @Transactional
    @Modifying
    @Query(value = "UPDATE user h set status =0 where h.id = :id", nativeQuery = true)
    void enable(@Param("id") Long id);
}
