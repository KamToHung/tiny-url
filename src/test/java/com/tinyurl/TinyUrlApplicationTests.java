package com.tinyurl;

import java.sql.Timestamp;

import com.tinyurl.entity.TinyUrl;
import com.tinyurl.helper.TinyUrlHelper;
import com.tinyurl.repo.TinyUrlRepository;
import com.tinyurl.service.TinyUrlService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TinyUrlApplicationTests {

    @Autowired
    private TinyUrlService tinyUrlService;

    @Autowired
    private TinyUrlRepository tinyUrlRepository;

    @Test
    void testGenTinyUrl(){
        String tinyCode = TinyUrlHelper.genTinyUrl("www.terry.top", 3600, "127.0.0.1");
        System.out.println(tinyCode);
    }
}
