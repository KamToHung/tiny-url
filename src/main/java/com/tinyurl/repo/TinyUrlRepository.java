package com.tinyurl.repo;

import com.tinyurl.entity.TinyUrl;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 * tiny_url repo
 *
 * @author Terry
 * @since 2021/5/6 11:41
 */
public interface TinyUrlRepository extends CrudRepository<TinyUrl, Long> {


    /**
     * 通过id获取url
     *
     * @param id
     * @return
     */
    @Query(value = "select url from tiny_url where id = ?1", nativeQuery = true)
    String getUrlById(Long id);

}
