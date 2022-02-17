package com.example.currencyconverter.domain.model;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Currency")
public class CurrencyDTO {
    @ColumnInfo(name = "currencyId")
    @PrimaryKey(autoGenerate = true)
    private Integer currencyId;
    @ColumnInfo(name = "convertingCurrency")
    private String convertingCurrency;
    @ColumnInfo(name = "currencyToConvert")
    private String currencyToConvert;
    @ColumnInfo(name = "conversionRate")
    private Double conversionRate;

    public CurrencyDTO() {}

    public CurrencyDTO(String convertingCurrency, String currencyToConvert) {
        this.convertingCurrency = convertingCurrency;
        this.currencyToConvert = currencyToConvert;
    }

    public CurrencyDTO(String convertingCurrency, String currencyToConvert, Double conversionRate) {
        this.convertingCurrency = convertingCurrency;
        this.currencyToConvert = currencyToConvert;
        this.conversionRate = conversionRate;
    }

    public Integer getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(Integer currencyId) {
        this.currencyId = currencyId;
    }

    public String getConvertingCurrency() {
        return convertingCurrency;
    }

    public void setConvertingCurrency(String convertingCurrency) {
        this.convertingCurrency = convertingCurrency;
    }

    public String getCurrencyToConvert() {
        return currencyToConvert;
    }

    public void setCurrencyToConvert(String currencyToConvert) {
        this.currencyToConvert = currencyToConvert;
    }

    public Double getConversionRate() {
        return conversionRate;
    }

    public void setConversionRate(Double conversionRate) {
        this.conversionRate = conversionRate;
    }
}

