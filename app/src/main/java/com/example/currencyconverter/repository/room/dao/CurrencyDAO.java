package com.example.currencyconverter.repository.room.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import com.example.currencyconverter.domain.model.CurrencyDTO;

import java.util.List;

@Dao
public interface CurrencyDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addCurrencies(CurrencyDTO currencyDTO);

    @Query("Select conversionRate From Currency Where convertingCurrency =:convertingCurrency " +
            "and currencyToConvert = :currencyToConvert")
    LiveData<Double> getConversionRate(String convertingCurrency, String currencyToConvert);

    @Query("Select currencyToConvert From Currency Where convertingCurrency =:convertingCurrency")
    LiveData<List<CurrencyDTO>> getCurrencyToConvert(String convertingCurrency);

    @Query("Select convertingCurrency From Currency")
    LiveData<List<CurrencyDTO>> getConvertingCurrency();
}
