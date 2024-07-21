package com.miniproject.partnerlocation.Repository;

import com.miniproject.partnerlocation.model.Partner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class PartnerRespository {
    public static final String HASH_KEY = "Partner";

    private final RedisTemplate<String, Object> template;

    @Autowired
    public PartnerRespository(RedisTemplate<String, Object> template) {
        this.template = template;
    }

    public Partner createPartner(Partner partner) {
        this.template.opsForHash().put(HASH_KEY, "partner_" + partner.getId(), partner);
        return partner;
    }
    public Optional<Partner> findById(long id) {
        Object result = this.template.opsForHash().get(HASH_KEY, "partner_" + id);
        if (result instanceof Partner) {
            return Optional.of((Partner) result);
        }
        return Optional.empty();
    }

    public List<Object> findAll(){
        return this.template.opsForHash().values(HASH_KEY);
    }
    public String deleteById(long id){
        Object result = this.template.opsForHash().get(HASH_KEY, "partner_" + id);
        if(result == null){
            return "Partner with id: " + id + " not found";
        }
        this.template.opsForHash().delete(HASH_KEY,"partner_" + id);
        return "product removed !!";
    }
}
