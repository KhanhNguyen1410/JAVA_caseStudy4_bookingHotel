package com.casestudy.case4.repository.province;

//import com.example.demo.model.Province;
import com.casestudy.case4.model.Province;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProvinceRepository extends JpaRepository<Province, Long> {
}
