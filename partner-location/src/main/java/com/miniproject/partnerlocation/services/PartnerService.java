package com.miniproject.partnerlocation.services;

import com.miniproject.partnerlocation.model.Partner;

import java.util.List;

public interface PartnerService {
    Partner updatePartner(long partnerId, String name, double latitude, double longitude);

    String deletePartner(long partnerId);

    Partner getPartner(long partnerId);

    List<Object> getAllPartner();
}
