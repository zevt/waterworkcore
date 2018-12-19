package com.autowaterworks.core.controller;


import com.autowaterworks.core.model.Address;
import com.autowaterworks.core.service.GeoService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.maps.errors.ApiException;
import com.google.maps.model.GeocodingResult;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/address")
@Slf4j
public class AddressRestApi {

  @Autowired
  private GeoService geoService;

  @PostMapping(value = "/validate", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<?> validate(@RequestBody Address address) throws InterruptedException, IOException, ApiException {


//    String addr1 = Joiner.on('+').join(Splitter.onPattern("\\s+").split(address.getStreetAddress1()));
//    String addr2 = Joiner.on('+').join(Splitter.onPattern("\\s+").split(address.getCity()));
//    String addr3 = Joiner.on('+').join(Splitter.onPattern("\\s+").split(address.getState()));
//    String addr = Joiner.on(",+").join(addr1, addr2, addr3);
//
//    String url = String.format("%s=%s&key=%s",geoUrl,addr, this.apiKey);
//
//    ResponseEntity<Object> response = restTemplate.getForEntity(url, Object.class);
//
//    String payload = new Gson().toJson(response.getBody());

//    log.info("Address: {}", address.toString());

    GeocodingResult[] results = this.geoService.getGeoCodingResults(address);
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    String output = gson.toJson(results);

    return new ResponseEntity<>(output, HttpStatus.OK);
  }
}
