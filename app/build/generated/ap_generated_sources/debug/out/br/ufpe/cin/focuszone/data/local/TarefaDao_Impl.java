package br.ufpe.cin.focuszone.data.local;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import br.ufpe.cin.focuszone.domain.Tarefa;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class TarefaDao_Impl implements TarefaDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Tarefa> __insertionAdapterOfTarefa;

  private final EntityDeletionOrUpdateAdapter<Tarefa> __deletionAdapterOfTarefa;

  private final EntityDeletionOrUpdateAdapter<Tarefa> __updateAdapterOfTarefa;

  public TarefaDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfTarefa = new EntityInsertionAdapter<Tarefa>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `tarefas` (`id`,`titulo`,`concluida`) VALUES (nullif(?, 0),?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement, final Tarefa entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getTitulo() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getTitulo());
        }
        final int _tmp = entity.isConcluida() ? 1 : 0;
        statement.bindLong(3, _tmp);
      }
    };
    this.__deletionAdapterOfTarefa = new EntityDeletionOrUpdateAdapter<Tarefa>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `tarefas` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement, final Tarefa entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfTarefa = new EntityDeletionOrUpdateAdapter<Tarefa>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `tarefas` SET `id` = ?,`titulo` = ?,`concluida` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement, final Tarefa entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getTitulo() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getTitulo());
        }
        final int _tmp = entity.isConcluida() ? 1 : 0;
        statement.bindLong(3, _tmp);
        statement.bindLong(4, entity.getId());
      }
    };
  }

  @Override
  public void inserir(final Tarefa tarefa) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfTarefa.insert(tarefa);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void remover(final Tarefa tarefa) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfTarefa.handle(tarefa);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void atualizar(final Tarefa tarefa) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfTarefa.handle(tarefa);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public LiveData<List<Tarefa>> listarTodas() {
    final String _sql = "SELECT * FROM tarefas ORDER BY id ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[] {"tarefas"}, false, new Callable<List<Tarefa>>() {
      @Override
      @Nullable
      public List<Tarefa> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitulo = CursorUtil.getColumnIndexOrThrow(_cursor, "titulo");
          final int _cursorIndexOfConcluida = CursorUtil.getColumnIndexOrThrow(_cursor, "concluida");
          final List<Tarefa> _result = new ArrayList<Tarefa>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Tarefa _item;
            final String _tmpTitulo;
            if (_cursor.isNull(_cursorIndexOfTitulo)) {
              _tmpTitulo = null;
            } else {
              _tmpTitulo = _cursor.getString(_cursorIndexOfTitulo);
            }
            _item = new Tarefa(_tmpTitulo);
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            _item.setId(_tmpId);
            final boolean _tmpConcluida;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfConcluida);
            _tmpConcluida = _tmp != 0;
            _item.setConcluida(_tmpConcluida);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
