package com.tinyurl.service;

import com.tinyurl.entity.TinyUrl;
import com.tinyurl.repo.TinyUrlRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


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

    @Transactional
    public void updateUrl(List<TinyUrl> tinyUrls) {
        TinyUrl tinyUrl1 = tinyUrls.get(0);
        tinyUrlRepository.updateUrl(tinyUrl1.getId(), tinyUrl1.getUrl());
        this.updateUrl(tinyUrls.get(1));
        throw new NullPointerException();
    }

//    @Transactional
    public void updateUrl(TinyUrl tinyUrl) {
        tinyUrlRepository.updateUrl(tinyUrl.getId(), tinyUrl.getUrl());
    }

    @Transactional
    public void save(TinyUrl tinyUrl) {
        tinyUrlRepository.save(tinyUrl);
    }

}
