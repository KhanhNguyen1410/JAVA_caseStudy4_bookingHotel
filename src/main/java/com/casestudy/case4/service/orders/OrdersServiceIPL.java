package com.casestudy.case4.service.orders;

import com.casestudy.case4.model.Orders;
import com.casestudy.case4.repository.orders.IOrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class OrdersServiceIPL implements IOrdersService {

    @Autowired
    private IOrdersRepository iOrdersRepository;

    @Override
    public Iterable<Orders> findAll() {
        return iOrdersRepository.findAll();
    }

    @Override
    public Orders save(Orders orders) {
        return iOrdersRepository.save(orders);
    }

    @Override
    public Optional<Orders> findById(Long id) {
        return iOrdersRepository.findById(id);
    }

    @Override
    public void remove(Long id) {
        iOrdersRepository.remove(id);
    }

    @Override
    public Page<Orders> findAllByStatusIsFalse(Pageable pageable) {
        return iOrdersRepository.findAllByStatusIsFalse(pageable);
    }
}
