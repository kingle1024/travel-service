package com.travel.api.controller;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.travel.api.service.MapService;

@RestController
@RequestMapping("/api")
public class MapApiController {

    private final MapService mapService;

    @Autowired
    public MapApiController(MapService mapService) {
        this.mapService = mapService;
    }

    @GetMapping("/location")
    public String location(@RequestParam String address) {
        try {
            double[] latLng = mapService.getLatLngFromAddress(address);
            if (latLng != null) {
                JSONObject response = new JSONObject();
                response.put("latitude", latLng[0]);
                response.put("longitude", latLng[1]);
                return response.toString();
            } else {
                return "{\"error\": \"No location found for the given address.\"}";
            }
        } catch (Exception e) {
            return "{\"error\": \"An error occurred while processing the request.\"}";
        }
    }
}
