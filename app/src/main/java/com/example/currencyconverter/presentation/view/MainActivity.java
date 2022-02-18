package com.example.currencyconverter.presentation.view;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.appcompat.app.AppCompatActivity;

import com.example.converter.R;
import com.example.converter.databinding.ActivityMainBinding;
import com.example.currencyconverter.domain.model.CurrencyDTO;
import com.example.currencyconverter.domain.viewmodel.MainViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private MainViewModel mainViewModel;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mainViewModel = new MainViewModel(getApplication());
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        if (checkConnection(getApplication().getApplicationContext())) {
            setContentView(view);
        } else {
            binding.SpinnerConvertTo.setAdapter(null);
            binding.SpinnerConvertRes.setAdapter(null);
            mainViewModel.getConvertingCurrenciesFromDb().observe(this, v -> {
                    List<String> convertingCurrency = new ArrayList<>();
                for (CurrencyDTO currencyDTO: v) {
                        convertingCurrency.add(currencyDTO.getConvertingCurrency());
                }
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(
                            this,
                            android.R.layout.simple_spinner_item,
                            convertingCurrency);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    binding.SpinnerConvertTo.setAdapter(adapter);
                });

            binding.SpinnerConvertTo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    mainViewModel.getCurrencyToConvertFromDb(adapterView.getItemAtPosition(i).
                            toString()).observe(MainActivity.this, v-> {
                        List<String> currencyToConvert = new ArrayList<>();
                        for (CurrencyDTO currencyDTO: v) {
                            currencyToConvert.add(currencyDTO.getCurrencyToConvert());
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                                MainActivity.this,
                                android.R.layout.simple_spinner_item,
                                currencyToConvert);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        binding.SpinnerConvertRes.setAdapter(adapter);
                    });
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                }
            });
        }
            setContentView(view);
        }

    public void onClickConvert(View view){
        String currencyTo;
        String currencyRes;
        if (checkConnection(getApplication().getApplicationContext())) {
            currencyTo = binding.SpinnerConvertTo.getSelectedItem().toString();
            currencyRes = binding.SpinnerConvertRes.getSelectedItem().toString();
            String finalCurrencyTo = currencyTo;
            String finalCurrencyRes = currencyRes;
            mainViewModel.getValue(currencyTo, currencyRes).observe(this,
                    conversionRate -> {
                        double result;
                        result = Double.parseDouble(binding.EtConvertTo.getText().toString())
                                * conversionRate;
                        binding.EtConvertRes.setText(Double.toString(result));
                        new Thread(() -> mainViewModel.updateCurrency(conversionRate,
                                finalCurrencyTo,
                                finalCurrencyRes)).start();
                    });
        } else {
            currencyTo = binding.SpinnerConvertTo.getSelectedItem().toString();
            currencyRes = binding.SpinnerConvertRes.getSelectedItem().toString();
            mainViewModel.getValueFromDb(currencyTo, currencyRes).observe(this,
                    conversionRate -> {
                        double result;
                        result = Double.parseDouble(binding.EtConvertTo.getText().toString())
                                * conversionRate;
                        binding.EtConvertRes.setText(Double.toString(result));
                    });
        }
    }

    public void onClickSave(View view) {
        String currencyTo;
        String currencyRes;
        currencyTo = getResources().getStringArray(R.array.currency)
                [binding.SpinnerConvertTo.getSelectedItemPosition()];
        currencyRes = getResources().getStringArray(R.array.currency)
                [binding.SpinnerConvertRes.getSelectedItemPosition()];
        String finalCurrencyTo = currencyTo;
        String finalCurrencyRes = currencyRes;
        mainViewModel.getValue(currencyTo, currencyRes).observe(this, conversionRate -> {
            mainViewModel.getValueFromDb(finalCurrencyTo, finalCurrencyRes).observe(this,
                    conversionRateFromDb ->{
                        if (conversionRateFromDb == null) {
                            CurrencyDTO currencyDTO = new CurrencyDTO(finalCurrencyTo,
                                    finalCurrencyRes, conversionRate);
                            mainViewModel.saveCurrency(currencyDTO);
                        }
                    });
        });
    }

    public static boolean checkConnection(Context context) {
        final ConnectivityManager connMgr = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connMgr != null) {
            NetworkInfo activeNetworkInfo = connMgr.getActiveNetworkInfo();

            if (activeNetworkInfo != null) { // connected to the internet
                if (activeNetworkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                    return true;
                } else return activeNetworkInfo.getType() == ConnectivityManager.TYPE_MOBILE;
            }
        }
        return false;
    }
}