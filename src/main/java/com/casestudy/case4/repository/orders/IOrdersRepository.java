package com.casestudy.case4.repository.orders;

import com.casestudy.case4.model.Orders;
import org.hibernate.criterion.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface IOrdersRepository extends JpaRepository<Orders, Long> {
    Page<Orders> findAllByStatusIsFalse(Pageable pageable);
    Page<Orders> findAllByStatusIsTrue(Pageable pageable);
    Page<Orders> findAllByStatusIsFalseAndUserId(Long id, Pageable pageable);
    Page<Orders> findAllByStatusIsTrueAndUserId(Long id, Pageable pageable);
    Page<Orders> findAllByUserId(Long id, Pageable pageable);
    @Transactional
    @Modifying
    @Query(value = "UPDATE orders o set status =1 where o.id = :id", nativeQuery = true)
    void remove(@Param("id") Long id);
}
