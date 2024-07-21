package com.miniproject.partnerlocation.services;

import com.miniproject.partnerlocation.Repository.PartnerRespository;
import com.miniproject.partnerlocation.model.Location;
import com.miniproject.partnerlocation.model.Partner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PartnerServiceImpl implements PartnerService {
    private final PartnerRespository partnerRespository;

    @Autowired
    public PartnerServiceImpl(PartnerRespository partnerRespository) {
        this.partnerRespository = partnerRespository;
    }

    @Override
    public Partner updatePartner(long partnerId, String name, double latitude, double longitude) {
        Partner partner;
        Optional<Partner> existingPartner = this.partnerRespository.findById(partnerId);
        if (existingPartner.isPresent()) {
            partner = existingPartner.get();
            partner.setName(name);
            Location location = new Location();
            location.setLatitude(latitude);
            location.setLongitude(longitude);
            partner.setLocation(location);
        }else{
            partner = new Partner();
            partner.setId(partnerId);
            partner.setName(name);
            Location location = new Location();
            location.setLatitude(latitude);
            location.setLongitude(longitude);
            partner.setLocation(location);
        }
        return this.partnerRespository.updatePartner(partner);
    }

    @Override
    public String deletePartner(long partnerId) {
        return this.partnerRespository.deleteById(partnerId);
    }

    @Override
    public Partner getPartner(long partnerId) {
        return this.partnerRespository.findById(partnerId).get();
    }

    @Override
    public List<Object> getAllPartner() {
        return this.partnerRespository.findAll();
    }
}
