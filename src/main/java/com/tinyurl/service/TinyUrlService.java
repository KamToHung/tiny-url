package com.tinyurl.service;

import com.tinyurl.entity.TinyUrl;
import com.tinyurl.repo.TinyUrlRepository;
import com.tinyurl.util.SnowFlakeUtils;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * tiny_url service
 *
 * @author Terry
 * @since 2021/5/6 11:44
 */
@Service
public class TinyUrlService {

    private TinyUrlRepository tinyUrlRepository;

    public TinyUrlService(TinyUrlRepository tinyUrlRepository) {
        this.tinyUrlRepository = tinyUrlRepository;
    }

    @Transactional
    public void save(TinyUrl tinyUrl) {
        tinyUrlRepository.save(tinyUrl);
    }

    @Transactional
    public TinyUrl getById(long id) {
        return tinyUrlRepository.getById(id);
    }
    
    @Transactional
    public String getUrlById(long id) {
        return tinyUrlRepository.getUrlById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    public void update(long id, long id2, String url) {
        tinyUrlRepository.updateUrlById(id, url);
        tinyUrlRepository.updateUrlById(id2, url);
    }

}
