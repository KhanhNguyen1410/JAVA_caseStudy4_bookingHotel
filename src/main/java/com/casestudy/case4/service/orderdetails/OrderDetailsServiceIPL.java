package com.casestudy.case4.service.orderdetails;

import com.casestudy.case4.model.OrderDetails;
import com.casestudy.case4.repository.orders.IOrdersRepository;
import com.casestudy.case4.repository.ordersdetails.IOrderDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class OrderDetailsServiceIPL implements IOrderDetailsService {

    @Autowired
    private IOrderDetailRepository iOrderDetailRepository;

    @Override
    public Iterable<OrderDetails> findAll() {
        return iOrderDetailRepository.findAll();
    }

    @Override
    public OrderDetails save(OrderDetails orderDetails) {
        return iOrderDetailRepository.save(orderDetails);
    }

    @Override
    public Optional<OrderDetails> findById(Long id) {
        return iOrderDetailRepository.findById(id);
    }

    @Override
    public void remove(Long id) {
        iOrderDetailRepository.deleteById(id);
    }

//    @Override
//    public Page<OrderDetails> findAllByOrdersId(Long id) {
//        return iOrderDetailRepository.findAllByOrdersId(id);
//    }
}
