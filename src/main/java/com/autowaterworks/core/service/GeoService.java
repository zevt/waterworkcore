package com.autowaterworks.core.service;

import com.autowaterworks.core.model.Address;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.GeocodingResult;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author Viet Quoc Tran vt on 12/2/18. www.zeroexception.com
 */


@Service
public class GeoService {


  @Value("${GoogleApiKey}")
  private String apiKey;

  @Value("${GeoCodeUrl}")
  private String geoUrl;

  public GeocodingResult[] getGeoCodingResults(Address address) throws ApiException, InterruptedException, IOException {

    String addr1 = Joiner.on('+').join(Splitter.onPattern("\\s+").split(address.getStreetAddress1()));
    String addr2 = Joiner.on('+').join(Splitter.onPattern("\\s+").split(address.getCity()));
    String addr3 = Joiner.on('+').join(Splitter.onPattern("\\s+").split(address.getState()));
    String addr = Joiner.on(",+").join(addr1, addr2, addr3);

    GeoApiContext context = new GeoApiContext.Builder()
        .apiKey(this.apiKey)
        .build();

    return GeocodingApi.geocode(context, Joiner.on(',')
        .join(address.getStreetAddress1(), address.getCity(), address.getState(), address.getZipcode())).await();

  }
}
