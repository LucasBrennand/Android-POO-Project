package br.ufpe.cin.focuszone.data.local;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import br.ufpe.cin.focuszone.domain.Sessao;
import java.lang.Class;
import java.lang.Long;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class SessaoDao_Impl implements SessaoDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Sessao> __insertionAdapterOfSessao;

  public SessaoDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfSessao = new EntityInsertionAdapter<Sessao>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `sessoes` (`id`,`tarefaTitulo`,`duracaoMinutos`,`iniciadaEm`,`concluida`) VALUES (nullif(?, 0),?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement, final Sessao entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getTarefaTitulo() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getTarefaTitulo());
        }
        statement.bindLong(3, entity.getDuracaoMinutos());
        final Long _tmp = InstantConverter.fromInstant(entity.getIniciadaEm());
        if (_tmp == null) {
          statement.bindNull(4);
        } else {
          statement.bindLong(4, _tmp);
        }
        final int _tmp_1 = entity.isConcluida() ? 1 : 0;
        statement.bindLong(5, _tmp_1);
      }
    };
  }

  @Override
  public void inserir(final Sessao sessao) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfSessao.insert(sessao);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public List<Sessao> listarTodas() {
    final String _sql = "SELECT * FROM sessoes ORDER BY iniciadaEm DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfTarefaTitulo = CursorUtil.getColumnIndexOrThrow(_cursor, "tarefaTitulo");
      final int _cursorIndexOfDuracaoMinutos = CursorUtil.getColumnIndexOrThrow(_cursor, "duracaoMinutos");
      final int _cursorIndexOfIniciadaEm = CursorUtil.getColumnIndexOrThrow(_cursor, "iniciadaEm");
      final int _cursorIndexOfConcluida = CursorUtil.getColumnIndexOrThrow(_cursor, "concluida");
      final List<Sessao> _result = new ArrayList<Sessao>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final Sessao _item;
        final String _tmpTarefaTitulo;
        if (_cursor.isNull(_cursorIndexOfTarefaTitulo)) {
          _tmpTarefaTitulo = null;
        } else {
          _tmpTarefaTitulo = _cursor.getString(_cursorIndexOfTarefaTitulo);
        }
        final int _tmpDuracaoMinutos;
        _tmpDuracaoMinutos = _cursor.getInt(_cursorIndexOfDuracaoMinutos);
        final Instant _tmpIniciadaEm;
        final Long _tmp;
        if (_cursor.isNull(_cursorIndexOfIniciadaEm)) {
          _tmp = null;
        } else {
          _tmp = _cursor.getLong(_cursorIndexOfIniciadaEm);
        }
        _tmpIniciadaEm = InstantConverter.toInstant(_tmp);
        final boolean _tmpConcluida;
        final int _tmp_1;
        _tmp_1 = _cursor.getInt(_cursorIndexOfConcluida);
        _tmpConcluida = _tmp_1 != 0;
        _item = new Sessao(_tmpTarefaTitulo,_tmpDuracaoMinutos,_tmpIniciadaEm,_tmpConcluida);
        final long _tmpId;
        _tmpId = _cursor.getLong(_cursorIndexOfId);
        _item.setId(_tmpId);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<Sessao> listarDeHoje(final Instant inicioDoDia) {
    final String _sql = "SELECT * FROM sessoes WHERE iniciadaEm >= ? ORDER BY iniciadaEm DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    final Long _tmp = InstantConverter.fromInstant(inicioDoDia);
    if (_tmp == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindLong(_argIndex, _tmp);
    }
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfTarefaTitulo = CursorUtil.getColumnIndexOrThrow(_cursor, "tarefaTitulo");
      final int _cursorIndexOfDuracaoMinutos = CursorUtil.getColumnIndexOrThrow(_cursor, "duracaoMinutos");
      final int _cursorIndexOfIniciadaEm = CursorUtil.getColumnIndexOrThrow(_cursor, "iniciadaEm");
      final int _cursorIndexOfConcluida = CursorUtil.getColumnIndexOrThrow(_cursor, "concluida");
      final List<Sessao> _result = new ArrayList<Sessao>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final Sessao _item;
        final String _tmpTarefaTitulo;
        if (_cursor.isNull(_cursorIndexOfTarefaTitulo)) {
          _tmpTarefaTitulo = null;
        } else {
          _tmpTarefaTitulo = _cursor.getString(_cursorIndexOfTarefaTitulo);
        }
        final int _tmpDuracaoMinutos;
        _tmpDuracaoMinutos = _cursor.getInt(_cursorIndexOfDuracaoMinutos);
        final Instant _tmpIniciadaEm;
        final Long _tmp_1;
        if (_cursor.isNull(_cursorIndexOfIniciadaEm)) {
          _tmp_1 = null;
        } else {
          _tmp_1 = _cursor.getLong(_cursorIndexOfIniciadaEm);
        }
        _tmpIniciadaEm = InstantConverter.toInstant(_tmp_1);
        final boolean _tmpConcluida;
        final int _tmp_2;
        _tmp_2 = _cursor.getInt(_cursorIndexOfConcluida);
        _tmpConcluida = _tmp_2 != 0;
        _item = new Sessao(_tmpTarefaTitulo,_tmpDuracaoMinutos,_tmpIniciadaEm,_tmpConcluida);
        final long _tmpId;
        _tmpId = _cursor.getLong(_cursorIndexOfId);
        _item.setId(_tmpId);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
