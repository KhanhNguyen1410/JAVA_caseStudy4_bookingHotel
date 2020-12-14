package com.casestudy.case4.service.user;

import com.casestudy.case4.model.User;
import com.casestudy.case4.model.UserPrinciple;
import com.casestudy.case4.repository.user.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class IUserServiceIPL implements IUserService {
    @Autowired
    private IUserRepository iUserRepository;

    @Override
    public Iterable<User> findAll() {
        return iUserRepository.findAll();
    }

    @Override
    public User save(User user) {
        return iUserRepository.save(user);
    }

    @Override
    public Optional<User> findById(Long id) {
        return iUserRepository.findById(id);
    }

    @Override
    public void remove(Long id) {
        iUserRepository.remove(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = iUserRepository.findByUserNameAndStatusIsFalse(username);
        if(user == null){
            throw new UsernameNotFoundException(username);
        }
        return UserPrinciple.build(user);
    }

    @Override
    public User findByUserName(String userName) {
        return iUserRepository.findByUserName(userName);
    }

    @Override
    public Page<User> findAllByStatusIsFalse(Pageable pageable) {
        return iUserRepository.findAllByStatusIsFalse(pageable);
    }

    @Override
    public Page<User> findAllByStatusIsTrue(Pageable pageable) {
        return iUserRepository.findAllByStatusIsTrue(pageable);
    }

    @Override
    public Page<User> findAllUserPage(Pageable pageable) {
        return iUserRepository.findAll(pageable);
    }

    @Override
    public void enable(Long id) {
        iUserRepository.enable(id);
    }

    @Override
    public User findAllById(Long id) {
        return iUserRepository.findAllById(id);
    }
}
