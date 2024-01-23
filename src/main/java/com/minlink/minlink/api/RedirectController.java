package com.minlink.minlink.api;

import com.minlink.minlink.helper.ResponseGenerationHelper;
import com.minlink.minlink.model.Url;
import com.minlink.minlink.service.UrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

@RestController
public class RedirectController {

    @Autowired
    UrlService urlService;

    private static final ExecutorService executorService = new ScheduledThreadPoolExecutor(10);

    @GetMapping("/{shortUrl:[a-z,A-z,0-9]{7}}")
    public ResponseEntity<Map<String, Object>> redirectShortUrl(@PathVariable String shortUrl) {
        try {
            Url url = urlService.findByShortUrl(shortUrl);
            if (url == null || url.isDeactivated()) {
                return ResponseEntity.notFound().build();
            }
            executorService.submit(() -> handleRedirect(shortUrl));
            return ResponseEntity.status(HttpStatus.TEMPORARY_REDIRECT)
                    .header("Location", url.getOriginalUrl()).build();
        } catch (Exception e) {
            return ResponseGenerationHelper.exceptionResponse();
        }
    }

    private void handleRedirect(String shortUrl) {
        urlService.increaseHitCount(shortUrl);
    }

}
