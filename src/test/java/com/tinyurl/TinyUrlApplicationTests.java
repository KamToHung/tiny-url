package com.tinyurl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

    @Test
    void testTransactional() {
        Random random = new Random();
        long urlId = random.nextLong();
        List<TinyUrl> tinyUrls = new ArrayList<>();
        TinyUrl tinyUrl1 = new TinyUrl();
        tinyUrl1.setId(587715498259652608L);
        tinyUrl1.setUrl("www." + urlId + ".com");
        tinyUrls.add(tinyUrl1);
        TinyUrl tinyUrl2 = new TinyUrl();
        tinyUrl2.setId(587711794563391488L);
        tinyUrl2.setUrl("www." + urlId + ".com");
        tinyUrls.add(tinyUrl2);
        tinyUrlService.updateUrl(tinyUrls);
    }
}
