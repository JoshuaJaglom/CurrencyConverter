package com.example.currencyconverter.repository.room;

import android.app.Application;
import androidx.lifecycle.LiveData;
import com.example.currencyconverter.domain.model.CurrencyDTO;
import com.example.currencyconverter.repository.room.dao.CurrencyDAO;
import java.util.List;

public class CurrencyRepository {
    private CurrencyDAO currencyDAO;

    public CurrencyRepository (Application application) {
        CurrencyRoomDatabase db = CurrencyRoomDatabase.getDatabase(application);
        currencyDAO = db.currencyDAO();
    }

    public LiveData<Double> getConversionRate(String convertingCurrency,
                                                   String currencyToConvert) {
        return currencyDAO.getConversionRate(convertingCurrency, currencyToConvert);
    }

    public void insertCurrencies(CurrencyDTO currencies) {
        CurrencyRoomDatabase.databaseWriteExecutor.execute(() -> {
            currencyDAO.addCurrencies(currencies);
        });
    }

    public LiveData<List<CurrencyDTO>> getCurrencyToConvert(String convertingCurrency) {
        return currencyDAO.getCurrencyToConvert(convertingCurrency);
    }

    public LiveData<List<CurrencyDTO>> getConvertingCurrency() {
        return currencyDAO.getConvertingCurrency();
    }

}
