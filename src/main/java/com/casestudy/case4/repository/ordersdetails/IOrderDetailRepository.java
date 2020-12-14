package com.casestudy.case4.repository.ordersdetails;

import com.casestudy.case4.model.OrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IOrderDetailRepository extends JpaRepository<OrderDetails, Long> {

}
