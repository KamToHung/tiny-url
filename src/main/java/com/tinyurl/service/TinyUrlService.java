package com.tinyurl.service;

import com.tinyurl.repo.TinyUrlRepository;
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
    public String getUrlById(long id) {
        return tinyUrlRepository.getUrlById(id);
    }

}
