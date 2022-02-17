package com.example.currencyconverter.repository.room;


import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.example.currencyconverter.domain.model.CurrencyDTO;
import com.example.currencyconverter.repository.room.dao.CurrencyDAO;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {CurrencyDTO.class}, version = 1, exportSchema = false)
public abstract class CurrencyRoomDatabase extends RoomDatabase {
    public abstract CurrencyDAO currencyDAO();
    private static volatile CurrencyRoomDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static CurrencyRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (CurrencyRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            CurrencyRoomDatabase.class, "Currency")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
