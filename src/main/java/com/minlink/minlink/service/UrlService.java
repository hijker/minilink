package com.minlink.minlink.service;

import com.minlink.minlink.model.Url;
import com.minlink.minlink.repository.UrlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.minlink.minlink.constants.Constants.HIT_COUNT;
import static com.minlink.minlink.constants.Constants.SHORT_URL;

@Service
public class UrlService {

    @Autowired
    UrlRepository urlRepository;

    private final MongoTemplate mongoTemplate;

    @Autowired
    public UrlService(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }


    public Url createUrl(String userId,
                         String url,
                         String prefix) {
        Url urlObject = new Url(userId, url, prefix);
        return urlRepository.save(urlObject);
    }

    public Url findByShortUrl(String shortUrl) {
        return urlRepository.findByShortUrl(shortUrl);
    }

    public void increaseHitCount(String shortUrl) {
        Query query = new Query(Criteria.where(SHORT_URL).is(shortUrl));
        Update update = new Update().inc(HIT_COUNT, 1);
        mongoTemplate.updateFirst(query, update, Url.class);
    }

    public List<Url> findUrlsByUser(String userId) {
        return urlRepository.findAllByUserId(userId);
    }

    public void deactivateUrl(Url url) {
        url.setDeactivated(true);
        urlRepository.save(url);
    }

    public void activateUrl(Url url) {
        url.setDeactivated(false);
        urlRepository.save(url);
    }
}
