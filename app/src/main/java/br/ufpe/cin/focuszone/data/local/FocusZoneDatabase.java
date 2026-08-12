package br.ufpe.cin.focuszone.data.local;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import br.ufpe.cin.focuszone.domain.Sessao;
import br.ufpe.cin.focuszone.domain.Tarefa;

@Database(entities = {Tarefa.class, Sessao.class}, version = 1)
@TypeConverters(InstantConverter.class)
public abstract class FocusZoneDatabase extends RoomDatabase {

    private static volatile FocusZoneDatabase instancia;

    public abstract TarefaDao tarefaDao();

    public abstract SessaoDao sessaoDao();

    public static synchronized FocusZoneDatabase getInstance(Context context) {
        if (instancia == null) {
            instancia = Room.databaseBuilder(
                            context.getApplicationContext(),
                            FocusZoneDatabase.class,
                            "focuszone.db")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instancia;
    }
}
