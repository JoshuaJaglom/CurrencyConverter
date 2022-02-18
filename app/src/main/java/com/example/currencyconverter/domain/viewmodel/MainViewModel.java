package com.example.currencyconverter.domain.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.currencyconverter.domain.model.CurrencyDTO;
import com.example.currencyconverter.repository.network.ConverterAnalysis;
import com.example.currencyconverter.repository.room.CurrencyRepository;

import java.util.List;


public class MainViewModel extends AndroidViewModel {

    private CurrencyRepository currencyRepository;

    public MainViewModel(@NonNull Application application) {
        super(application);
        currencyRepository = new CurrencyRepository(application);
    }

    ConverterAnalysis converterAnalysis = new ConverterAnalysis();

    public LiveData<Double> getValue(String convertTo, String convertRes) {
        return converterAnalysis.getValue(convertTo, convertRes);
    }

    public LiveData<Double> getValueFromDb(String convertTo, String convertRes) {
        return currencyRepository.getConversionRate(convertTo, convertRes);
    }

    public LiveData<List<CurrencyDTO>> getConvertingCurrenciesFromDb() {
        return currencyRepository.getConvertingCurrency();
    }

    public LiveData<List<CurrencyDTO>> getCurrencyToConvertFromDb(String convertingCurrency) {
        return currencyRepository.getCurrencyToConvert(convertingCurrency);
    }

    public void saveCurrency(CurrencyDTO currencyDTO) {
        currencyRepository.insertCurrencies(currencyDTO);
    }

    public void updateCurrency(Double conversionRate,
                               String convertingCurrency,
                               String currencyToConvert) {
        currencyRepository.updateConversionRate(conversionRate, convertingCurrency, currencyToConvert);
    }
}
