package com.prakash.myweb.controller;

import com.prakash.myweb.service.CurrencyConverterService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CurrencyConverterController {

    private final CurrencyConverterService currencyService;

    public CurrencyConverterController(CurrencyConverterService currencyService) {
        this.currencyService = currencyService;
    }

    @PostMapping("/convert")
    public ResponseEntity<ConversionResponse> convertCurrency(@RequestParam double amount,
                                                              @RequestParam String fromCurrency,
                                                              @RequestParam String toCurrency) {
        double result = currencyService.convert(amount, fromCurrency, toCurrency);
        return ResponseEntity.ok(new ConversionResponse(result, fromCurrency, toCurrency));
    }

    // Inner class for response
    public static class ConversionResponse {
        private double convertedAmount;
        private String fromCurrency;
        private String toCurrency;

        public ConversionResponse(double convertedAmount, String fromCurrency, String toCurrency) {
            this.convertedAmount = convertedAmount;
            this.fromCurrency = fromCurrency;
            this.toCurrency = toCurrency;
        }

        public double getConvertedAmount() {
            return convertedAmount;
        }

        public String getFromCurrency() {
            return fromCurrency;
        }

        public String getToCurrency() {
            return toCurrency;
        }
    }
}