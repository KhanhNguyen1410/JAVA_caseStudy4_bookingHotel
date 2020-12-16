package com.casestudy.case4.service.orders;

import com.casestudy.case4.model.Orders;
import com.casestudy.case4.service.GeneralService;
import org.hibernate.criterion.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IOrdersService extends GeneralService<Orders> {
    Page<Orders> findAllByStatusIsFalse(Pageable pageable);
    Page<Orders> findAllByStatusIsTrue(Pageable pageable);
    Page<Orders> findAllByStatusIsFalseAndUserId(Long id, Pageable pageable);
    Page<Orders> findAllByStatusIsTrueAndUserId(Long id, Pageable pageable);
    Page<Orders> findAllByUserId(Long id, Pageable pageable);
}
