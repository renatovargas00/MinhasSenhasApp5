package br.usjt.devmobile.minhassenhasapp;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Senha.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract SenhaDao senhaDao();
}