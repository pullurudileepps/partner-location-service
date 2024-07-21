package com.miniproject.allocation.repository;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Map;


@Repository
public class RedisRepository {

    private final RedisTemplate<String, Object> redisTemplate;
    private HashOperations<String, String, Object> hashOperations;

    @Autowired
    public RedisRepository(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @PostConstruct
    private void init() {
        hashOperations = redisTemplate.opsForHash();
    }
    public void save(String key, String id, Object value) {
        hashOperations.put(key, id, value);
    }

    public Object findById(String key, String id) {
        return hashOperations.get(key, id);
    }

    public Map<String, Object> findAll(String key) {
        return hashOperations.entries(key);
    }

    public void delete(String key, String id) {
        hashOperations.delete(key, id);
    }
}
