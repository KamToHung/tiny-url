package com.tinyurl.controller;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.tinyurl.helper.TinyUrlHelper;
import com.tinyurl.util.IPAddressUtils;
import com.tinyurl.util.ResponseData;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * tiny_url controller
 *
 * @author Terry
 * @since 2021/5/6 11:46
 */
@RestController
public class TinyUrlController {

    private static final int MAX_TRY_COUNT = 10;

    /**
     * ip封禁缓存。
     */
    private final Cache<String, Integer> ipBlockCache = Caffeine
            .newBuilder()
            .expireAfterWrite(30, TimeUnit.MINUTES)
            .build();

    /**
     * 生成短链接
     *
     * @param request
     * @param url
     * @param ttl
     * @return
     */
    @PutMapping("/save")
    public ResponseData<String> save(HttpServletRequest request,
                                     @RequestParam String url,
                                     @RequestParam(required = false, defaultValue = "0") int ttl) {
        String shortUrl = TinyUrlHelper.genTinyUrl(url, ttl, IPAddressUtils.getIpAddr(request));
        return ResponseData.success(shortUrl);
    }

    @GetMapping("/{tinyCode}")
    public void sendRedirect(HttpServletRequest request,
                             HttpServletResponse response,
                             @PathVariable String tinyCode) throws IOException {
        String clientIp = IPAddressUtils.getIpAddr(request);
        Integer tryCount = ipBlockCache.getIfPresent(clientIp);
        if (tryCount == null) {
            tryCount = 0;
        }
        //如果超过最大错误尝试数，
        if (tryCount >= MAX_TRY_COUNT) {
            //直接403屏蔽掉
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
        } else {
            String url = TinyUrlHelper.getUrl(tinyCode);
            if (url != null) {
                response.sendRedirect(url);
            } else {
                //进行错误计数后，返回404
                tryCount++;
                ipBlockCache.put(clientIp, tryCount);
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        }
    }
}
