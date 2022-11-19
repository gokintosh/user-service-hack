package com.gokul.userhackservice.repository;

import com.gokul.userhackservice.model.Compost_Activity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.runner.RunWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


//@RunWith(SpringRunner.class)
//@DataJpaTest
//@ExtendWith(MockitoExtension.class)
//class CompostRepositoryTest {
//
//    private final CompostRepository compostRepository;
//
//    CompostRepositoryTest(CompostRepository compostRepository) {
//        this.compostRepository = compostRepository;
//    }
//
//    @Test
//    public void printCompost(){
//        List<Compost_Activity>compost_activities=compostRepository.findAllByUser("1");
//        assertThat(compost_activities).hasSize(3);
//
//    }

//}