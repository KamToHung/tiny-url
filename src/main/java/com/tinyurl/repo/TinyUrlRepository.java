package com.tinyurl.repo;

import com.tinyurl.entity.TinyUrl;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

/**
 * tiny_url repo
 *
 * @author Terry
 * @since 2021/5/6 11:41
 */
public interface TinyUrlRepository extends CrudRepository<TinyUrl, Long> {

    TinyUrl getById(Long id);


    @Query(value = "select url from tiny_url where id = ?1", nativeQuery = true)
    String getUrlById(Long id);


    @Modifying
    @Query(value = "update tiny_url set url = ?2 where id = ?1", nativeQuery = true)
    @Transactional
    public void updateUrlById(long id, String url);

}
