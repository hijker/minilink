package com.minlink.minlink.repository;

import com.minlink.minlink.model.Url;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UrlRepository extends MongoRepository<Url, String> {
    Url findByShortUrl(String shortUrl);

    List<Url> findAllByUserId(String userId);
}
