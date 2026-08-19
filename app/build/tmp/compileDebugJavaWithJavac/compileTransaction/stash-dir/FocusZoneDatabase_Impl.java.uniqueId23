package br.ufpe.cin.focuszone.data.local;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomOpenHelper;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class FocusZoneDatabase_Impl extends FocusZoneDatabase {
  private volatile TarefaDao _tarefaDao;

  private volatile SessaoDao _sessaoDao;

  @Override
  @NonNull
  protected SupportSQLiteOpenHelper createOpenHelper(@NonNull final DatabaseConfiguration config) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(config, new RoomOpenHelper.Delegate(1) {
      @Override
      public void createAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS `tarefas` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `titulo` TEXT, `concluida` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `sessoes` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `tarefaTitulo` TEXT, `duracaoMinutos` INTEGER NOT NULL, `iniciadaEm` INTEGER, `concluida` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '57f74454a87e8dd3d16e9a5918fcbefc')");
      }

      @Override
      public void dropAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS `tarefas`");
        db.execSQL("DROP TABLE IF EXISTS `sessoes`");
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onDestructiveMigration(db);
          }
        }
      }

      @Override
      public void onCreate(@NonNull final SupportSQLiteDatabase db) {
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onCreate(db);
          }
        }
      }

      @Override
      public void onOpen(@NonNull final SupportSQLiteDatabase db) {
        mDatabase = db;
        internalInitInvalidationTracker(db);
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onOpen(db);
          }
        }
      }

      @Override
      public void onPreMigrate(@NonNull final SupportSQLiteDatabase db) {
        DBUtil.dropFtsSyncTriggers(db);
      }

      @Override
      public void onPostMigrate(@NonNull final SupportSQLiteDatabase db) {
      }

      @Override
      @NonNull
      public RoomOpenHelper.ValidationResult onValidateSchema(
          @NonNull final SupportSQLiteDatabase db) {
        final HashMap<String, TableInfo.Column> _columnsTarefas = new HashMap<String, TableInfo.Column>(3);
        _columnsTarefas.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTarefas.put("titulo", new TableInfo.Column("titulo", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTarefas.put("concluida", new TableInfo.Column("concluida", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysTarefas = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesTarefas = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoTarefas = new TableInfo("tarefas", _columnsTarefas, _foreignKeysTarefas, _indicesTarefas);
        final TableInfo _existingTarefas = TableInfo.read(db, "tarefas");
        if (!_infoTarefas.equals(_existingTarefas)) {
          return new RoomOpenHelper.ValidationResult(false, "tarefas(br.ufpe.cin.focuszone.domain.Tarefa).\n"
                  + " Expected:\n" + _infoTarefas + "\n"
                  + " Found:\n" + _existingTarefas);
        }
        final HashMap<String, TableInfo.Column> _columnsSessoes = new HashMap<String, TableInfo.Column>(5);
        _columnsSessoes.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSessoes.put("tarefaTitulo", new TableInfo.Column("tarefaTitulo", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSessoes.put("duracaoMinutos", new TableInfo.Column("duracaoMinutos", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSessoes.put("iniciadaEm", new TableInfo.Column("iniciadaEm", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSessoes.put("concluida", new TableInfo.Column("concluida", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysSessoes = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesSessoes = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoSessoes = new TableInfo("sessoes", _columnsSessoes, _foreignKeysSessoes, _indicesSessoes);
        final TableInfo _existingSessoes = TableInfo.read(db, "sessoes");
        if (!_infoSessoes.equals(_existingSessoes)) {
          return new RoomOpenHelper.ValidationResult(false, "sessoes(br.ufpe.cin.focuszone.domain.Sessao).\n"
                  + " Expected:\n" + _infoSessoes + "\n"
                  + " Found:\n" + _existingSessoes);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "57f74454a87e8dd3d16e9a5918fcbefc", "0c3b4f1c47d57b351510571799c0a630");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(config.context).name(config.name).callback(_openCallback).build();
    final SupportSQLiteOpenHelper _helper = config.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  @NonNull
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    final HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "tarefas","sessoes");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `tarefas`");
      _db.execSQL("DELETE FROM `sessoes`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  @NonNull
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(TarefaDao.class, TarefaDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(SessaoDao.class, SessaoDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  @NonNull
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  @NonNull
  public List<Migration> getAutoMigrations(
      @NonNull final Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecs) {
    final List<Migration> _autoMigrations = new ArrayList<Migration>();
    return _autoMigrations;
  }

  @Override
  public TarefaDao tarefaDao() {
    if (_tarefaDao != null) {
      return _tarefaDao;
    } else {
      synchronized(this) {
        if(_tarefaDao == null) {
          _tarefaDao = new TarefaDao_Impl(this);
        }
        return _tarefaDao;
      }
    }
  }

  @Override
  public SessaoDao sessaoDao() {
    if (_sessaoDao != null) {
      return _sessaoDao;
    } else {
      synchronized(this) {
        if(_sessaoDao == null) {
          _sessaoDao = new SessaoDao_Impl(this);
        }
        return _sessaoDao;
      }
    }
  }
}
