package org.digigeo.digigeo.Database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.arch.persistence.db.SupportSQLiteDatabase;
import android.support.annotation.NonNull;

import java.util.concurrent.Executors;

import org.digigeo.digigeo.Entity.Cache;

@Database(entities = {Cache.class}, version = 1, exportSchema = false)
public abstract class AppDb extends RoomDatabase {

    private static AppDb INSTANCE;

    public abstract CacheDao cacheDao();

    public synchronized static AppDb getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = buildDatabase(context);
        }
        return INSTANCE;
    }

    private static AppDb buildDatabase(final Context context) {
        return Room.databaseBuilder(context,
                AppDb.class,
                "my-database")
                .addCallback(new Callback() {
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        super.onCreate(db);
                        Executors.newSingleThreadScheduledExecutor().execute(new Runnable() {
                            @Override
                            public void run() {
                                getInstance(context).cacheDao().insertAll(Cache.populateData());
                            }
                        });
                    }
                })
                .build();
    }
}
