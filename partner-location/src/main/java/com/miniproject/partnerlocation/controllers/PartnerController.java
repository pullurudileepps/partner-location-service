package com.miniproject.partnerlocation.controllers;

import com.miniproject.partnerlocation.dtos.UpdatePartnerRequestDto;
import com.miniproject.partnerlocation.exceptions.InvalidLatitudeException;
import com.miniproject.partnerlocation.exceptions.InvalidLongitudeException;
import com.miniproject.partnerlocation.model.Partner;
import com.miniproject.partnerlocation.services.PartnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/partner")
public class PartnerController {

    private final PartnerService partnerService;

    @Autowired
    public PartnerController(PartnerService partnerService) {
        this.partnerService = partnerService;
    }

    @PostMapping("createPartner/{id}")
    public ResponseEntity<?> CreatePartner(@PathVariable("id") long partnerId,
                                           @RequestBody UpdatePartnerRequestDto requestDto)
            throws InvalidLatitudeException, InvalidLongitudeException {
        try {
            Double latitude = (Double) requestDto.getLocation().getLatitude();
            Double longitude = (Double) requestDto.getLocation().getLongitude();
            if (!validateLatitudeCheck(latitude)) {
                throw new InvalidLatitudeException("Value should be between -90 to +90");
            }
            if (!validateLongitudeCheck(longitude)) {
                throw new InvalidLongitudeException("Value should be between -180 to +180");
            }
            Partner result = this.partnerService.createPartner(partnerId, requestDto.getName(), latitude, longitude);
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    private boolean validateLatitudeCheck(Double latitude) {
        if (latitude == null) return false;
        return latitude >= -90 && latitude <= 90;
    }

    private boolean validateLongitudeCheck(Double longitude) {
        if (longitude == null) return false;
        return longitude >= -180 && longitude <= 180;
    }

    @DeleteMapping({"/{id}"})
    public String deletePartner(@PathVariable("id") long partnerId) {
        return this.partnerService.deletePartner(partnerId);
    }

    @GetMapping("/{id}")
    public Partner getPartner(@PathVariable("id") long partnerId) {
        return this.partnerService.getPartner(partnerId);
    }

    @GetMapping("/All")
    public List<?> getAllPartners() {
        return this.partnerService.getAllPartner();
    }
}
