package com.example.Beep;

import com.example.Beep.api.domain.entity.Message24;
import com.example.Beep.api.repository.Message24Repository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class RedisRepositoryTest1 {

    @Autowired
    private Message24Repository repo;

//    @Test
//    void test(){
//        Message24 message = new Message24(1L,"content",null,1L, 2L, 1, 1);
//
//        repo.save(message);
//
//        repo.findById(message.getId());
//
//        repo.count();
//
//        repo.delete(message);
//    }
}
