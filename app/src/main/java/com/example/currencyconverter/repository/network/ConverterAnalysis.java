package com.example.currencyconverter.repository.network;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ConverterAnalysis {
    private ConverterApi api;

    public ConverterAnalysis() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://v6.exchangerate-api.com/v6/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api = retrofit.create(ConverterApi.class);
    }

    public LiveData<Double> getValue(String convertFrom, String convertTo) {
        MutableLiveData<Double> convertRate = new MutableLiveData<>();
        Call<ValuePojo> call = api.getMultiplier(convertFrom, convertTo, "25ef7019ae531866f7c9a425");
        call.enqueue(new Callback<ValuePojo>() {

            @Override
            public void onResponse(@NonNull Call<ValuePojo> call, @NonNull Response<ValuePojo> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ValuePojo valuePojo = response.body();
                    convertRate.setValue((double) valuePojo.getConversion_rate());
                }
            }
            @Override
            public void onFailure(@NonNull Call<ValuePojo> call, Throwable t) {
                return;
            }
        });
        return convertRate;
    }
}

