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
    void testLoad() {
        TinyUrl byId = tinyUrlService.getById(2);
        System.out.println(byId);
    }

    @Test
    void testSave() {
        TinyUrl tinyUrl = new TinyUrl();
        tinyUrl.setId(1000000001L);
        tinyUrl.setUrl("www.ailey.com");
        tinyUrl.setCreateDate(new Timestamp(new java.util.Date().getTime()));
        tinyUrl.setExpireDate(new Timestamp(new java.util.Date().getTime() + 3600000L));
        tinyUrl.setCreateIp("127.0.0.1");
        tinyUrl.setState(1L);
        tinyUrlService.save(tinyUrl);
    }

    @Test
    void testSQL() {
        tinyUrlService.update(1000000000L, 1000000001L, "www.ailey.cn");
    }

    @Test
    void testGenTinyUrl(){
        String tinyCode = TinyUrlHelper.genTinyUrl("www.terry.top", 3600, "127.0.0.1");
        System.out.println(tinyCode);
    }
}
