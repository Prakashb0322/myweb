package com.prakash.myweb.service;

import com.prakash.myweb.model.Conversion;
import com.prakash.myweb.repository.ConversionRepository;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;

@Service
public class CurrencyConverterService {

    private static final String API_URL = "https://v6.exchangerate-api.com/v6/04e9c6bd5d091c433a5b2b92/latest/";

    @Autowired
    private ConversionRepository conversionRepository;

    public double convert(double amount, String from, String to) {
        String url = API_URL + from;
        RestTemplate restTemplate = new RestTemplate();
        try {
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            JSONObject jsonObject = new JSONObject(response.getBody());
            JSONObject rates = jsonObject.getJSONObject("conversion_rates");

            if (rates.has(to)) {
                double exchangeRate = rates.getDouble(to);
                double convertedAmount = amount * exchangeRate;

                // Save conversion data to MySQL
                Conversion conversion = new Conversion();
                conversion.setAmount(amount);
                conversion.setFromCurrency(from);
                conversion.setToCurrency(to);
                conversion.setConvertedAmount(convertedAmount);
                conversionRepository.save(conversion); // This line saves the conversion

                return convertedAmount;
            } else {
                throw new IllegalArgumentException("Invalid currency code: " + to);
            }
        } catch (HttpClientErrorException e) {
            throw new RuntimeException("Error fetching conversion rates: " + e.getMessage());
        }
    }
}