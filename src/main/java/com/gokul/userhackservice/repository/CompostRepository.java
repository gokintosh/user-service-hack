package com.gokul.userhackservice.repository;

import com.gokul.userhackservice.model.Compost_Activity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;
import java.util.List;

public interface CompostRepository extends JpaRepository<Compost_Activity, Long> {

    List<Compost_Activity>findAllByUser_Id(Long userId);
}
