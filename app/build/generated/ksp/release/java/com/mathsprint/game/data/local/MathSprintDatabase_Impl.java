package com.mathsprint.game.data.local;

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
import com.mathsprint.game.data.local.dao.AchievementDao;
import com.mathsprint.game.data.local.dao.AchievementDao_Impl;
import com.mathsprint.game.data.local.dao.DailyChallengeDao;
import com.mathsprint.game.data.local.dao.DailyChallengeDao_Impl;
import com.mathsprint.game.data.local.dao.GameSessionDao;
import com.mathsprint.game.data.local.dao.GameSessionDao_Impl;
import com.mathsprint.game.data.local.dao.PlayerStatsDao;
import com.mathsprint.game.data.local.dao.PlayerStatsDao_Impl;
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
public final class MathSprintDatabase_Impl extends MathSprintDatabase {
  private volatile GameSessionDao _gameSessionDao;

  private volatile DailyChallengeDao _dailyChallengeDao;

  private volatile PlayerStatsDao _playerStatsDao;

  private volatile AchievementDao _achievementDao;

  @Override
  @NonNull
  protected SupportSQLiteOpenHelper createOpenHelper(@NonNull final DatabaseConfiguration config) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(config, new RoomOpenHelper.Delegate(1) {
      @Override
      public void createAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS `game_sessions` (`sessionId` TEXT NOT NULL, `mode` TEXT NOT NULL, `category` TEXT NOT NULL, `difficulty` TEXT NOT NULL, `totalQuestions` INTEGER NOT NULL, `correctCount` INTEGER NOT NULL, `accuracyPercent` INTEGER NOT NULL, `totalScore` INTEGER NOT NULL, `maxStreak` INTEGER NOT NULL, `durationSeconds` INTEGER NOT NULL, `timestamp` INTEGER NOT NULL, PRIMARY KEY(`sessionId`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `daily_challenges` (`dateKey` TEXT NOT NULL, `isCompleted` INTEGER NOT NULL, `bestScore` INTEGER NOT NULL, `accuracyPercent` INTEGER NOT NULL, `completedAt` INTEGER NOT NULL, PRIMARY KEY(`dateKey`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `player_stats` (`id` INTEGER NOT NULL, `totalXp` INTEGER NOT NULL, `level` INTEGER NOT NULL, `totalGamesPlayed` INTEGER NOT NULL, `totalQuestionsAnswered` INTEGER NOT NULL, `totalCorrectAnswers` INTEGER NOT NULL, `bestStreak` INTEGER NOT NULL, `currentDailyStreak` INTEGER NOT NULL, `highestScore` INTEGER NOT NULL, `lastActiveDate` TEXT NOT NULL, `unlockedBandsCsv` TEXT NOT NULL, PRIMARY KEY(`id`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `achievements` (`id` TEXT NOT NULL, `title` TEXT NOT NULL, `description` TEXT NOT NULL, `iconName` TEXT NOT NULL, `isUnlocked` INTEGER NOT NULL, `currentProgress` INTEGER NOT NULL, `maxProgress` INTEGER NOT NULL, `unlockedAt` INTEGER, PRIMARY KEY(`id`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, 'a98098b30d60facdb734cbe00ab508ec')");
      }

      @Override
      public void dropAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS `game_sessions`");
        db.execSQL("DROP TABLE IF EXISTS `daily_challenges`");
        db.execSQL("DROP TABLE IF EXISTS `player_stats`");
        db.execSQL("DROP TABLE IF EXISTS `achievements`");
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
        final HashMap<String, TableInfo.Column> _columnsGameSessions = new HashMap<String, TableInfo.Column>(11);
        _columnsGameSessions.put("sessionId", new TableInfo.Column("sessionId", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsGameSessions.put("mode", new TableInfo.Column("mode", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsGameSessions.put("category", new TableInfo.Column("category", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsGameSessions.put("difficulty", new TableInfo.Column("difficulty", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsGameSessions.put("totalQuestions", new TableInfo.Column("totalQuestions", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsGameSessions.put("correctCount", new TableInfo.Column("correctCount", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsGameSessions.put("accuracyPercent", new TableInfo.Column("accuracyPercent", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsGameSessions.put("totalScore", new TableInfo.Column("totalScore", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsGameSessions.put("maxStreak", new TableInfo.Column("maxStreak", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsGameSessions.put("durationSeconds", new TableInfo.Column("durationSeconds", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsGameSessions.put("timestamp", new TableInfo.Column("timestamp", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysGameSessions = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesGameSessions = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoGameSessions = new TableInfo("game_sessions", _columnsGameSessions, _foreignKeysGameSessions, _indicesGameSessions);
        final TableInfo _existingGameSessions = TableInfo.read(db, "game_sessions");
        if (!_infoGameSessions.equals(_existingGameSessions)) {
          return new RoomOpenHelper.ValidationResult(false, "game_sessions(com.mathsprint.game.data.local.entity.GameSessionEntity).\n"
                  + " Expected:\n" + _infoGameSessions + "\n"
                  + " Found:\n" + _existingGameSessions);
        }
        final HashMap<String, TableInfo.Column> _columnsDailyChallenges = new HashMap<String, TableInfo.Column>(5);
        _columnsDailyChallenges.put("dateKey", new TableInfo.Column("dateKey", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDailyChallenges.put("isCompleted", new TableInfo.Column("isCompleted", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDailyChallenges.put("bestScore", new TableInfo.Column("bestScore", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDailyChallenges.put("accuracyPercent", new TableInfo.Column("accuracyPercent", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDailyChallenges.put("completedAt", new TableInfo.Column("completedAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysDailyChallenges = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesDailyChallenges = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoDailyChallenges = new TableInfo("daily_challenges", _columnsDailyChallenges, _foreignKeysDailyChallenges, _indicesDailyChallenges);
        final TableInfo _existingDailyChallenges = TableInfo.read(db, "daily_challenges");
        if (!_infoDailyChallenges.equals(_existingDailyChallenges)) {
          return new RoomOpenHelper.ValidationResult(false, "daily_challenges(com.mathsprint.game.data.local.entity.DailyChallengeEntity).\n"
                  + " Expected:\n" + _infoDailyChallenges + "\n"
                  + " Found:\n" + _existingDailyChallenges);
        }
        final HashMap<String, TableInfo.Column> _columnsPlayerStats = new HashMap<String, TableInfo.Column>(11);
        _columnsPlayerStats.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPlayerStats.put("totalXp", new TableInfo.Column("totalXp", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPlayerStats.put("level", new TableInfo.Column("level", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPlayerStats.put("totalGamesPlayed", new TableInfo.Column("totalGamesPlayed", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPlayerStats.put("totalQuestionsAnswered", new TableInfo.Column("totalQuestionsAnswered", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPlayerStats.put("totalCorrectAnswers", new TableInfo.Column("totalCorrectAnswers", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPlayerStats.put("bestStreak", new TableInfo.Column("bestStreak", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPlayerStats.put("currentDailyStreak", new TableInfo.Column("currentDailyStreak", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPlayerStats.put("highestScore", new TableInfo.Column("highestScore", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPlayerStats.put("lastActiveDate", new TableInfo.Column("lastActiveDate", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPlayerStats.put("unlockedBandsCsv", new TableInfo.Column("unlockedBandsCsv", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysPlayerStats = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesPlayerStats = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoPlayerStats = new TableInfo("player_stats", _columnsPlayerStats, _foreignKeysPlayerStats, _indicesPlayerStats);
        final TableInfo _existingPlayerStats = TableInfo.read(db, "player_stats");
        if (!_infoPlayerStats.equals(_existingPlayerStats)) {
          return new RoomOpenHelper.ValidationResult(false, "player_stats(com.mathsprint.game.data.local.entity.PlayerStatsEntity).\n"
                  + " Expected:\n" + _infoPlayerStats + "\n"
                  + " Found:\n" + _existingPlayerStats);
        }
        final HashMap<String, TableInfo.Column> _columnsAchievements = new HashMap<String, TableInfo.Column>(8);
        _columnsAchievements.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAchievements.put("title", new TableInfo.Column("title", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAchievements.put("description", new TableInfo.Column("description", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAchievements.put("iconName", new TableInfo.Column("iconName", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAchievements.put("isUnlocked", new TableInfo.Column("isUnlocked", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAchievements.put("currentProgress", new TableInfo.Column("currentProgress", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAchievements.put("maxProgress", new TableInfo.Column("maxProgress", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAchievements.put("unlockedAt", new TableInfo.Column("unlockedAt", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysAchievements = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesAchievements = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoAchievements = new TableInfo("achievements", _columnsAchievements, _foreignKeysAchievements, _indicesAchievements);
        final TableInfo _existingAchievements = TableInfo.read(db, "achievements");
        if (!_infoAchievements.equals(_existingAchievements)) {
          return new RoomOpenHelper.ValidationResult(false, "achievements(com.mathsprint.game.data.local.entity.AchievementEntity).\n"
                  + " Expected:\n" + _infoAchievements + "\n"
                  + " Found:\n" + _existingAchievements);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "a98098b30d60facdb734cbe00ab508ec", "c902008641d62b6b8056e70f12f4133a");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(config.context).name(config.name).callback(_openCallback).build();
    final SupportSQLiteOpenHelper _helper = config.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  @NonNull
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    final HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "game_sessions","daily_challenges","player_stats","achievements");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `game_sessions`");
      _db.execSQL("DELETE FROM `daily_challenges`");
      _db.execSQL("DELETE FROM `player_stats`");
      _db.execSQL("DELETE FROM `achievements`");
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
    _typeConvertersMap.put(GameSessionDao.class, GameSessionDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(DailyChallengeDao.class, DailyChallengeDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(PlayerStatsDao.class, PlayerStatsDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(AchievementDao.class, AchievementDao_Impl.getRequiredConverters());
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
  public GameSessionDao gameSessionDao() {
    if (_gameSessionDao != null) {
      return _gameSessionDao;
    } else {
      synchronized(this) {
        if(_gameSessionDao == null) {
          _gameSessionDao = new GameSessionDao_Impl(this);
        }
        return _gameSessionDao;
      }
    }
  }

  @Override
  public DailyChallengeDao dailyChallengeDao() {
    if (_dailyChallengeDao != null) {
      return _dailyChallengeDao;
    } else {
      synchronized(this) {
        if(_dailyChallengeDao == null) {
          _dailyChallengeDao = new DailyChallengeDao_Impl(this);
        }
        return _dailyChallengeDao;
      }
    }
  }

  @Override
  public PlayerStatsDao playerStatsDao() {
    if (_playerStatsDao != null) {
      return _playerStatsDao;
    } else {
      synchronized(this) {
        if(_playerStatsDao == null) {
          _playerStatsDao = new PlayerStatsDao_Impl(this);
        }
        return _playerStatsDao;
      }
    }
  }

  @Override
  public AchievementDao achievementDao() {
    if (_achievementDao != null) {
      return _achievementDao;
    } else {
      synchronized(this) {
        if(_achievementDao == null) {
          _achievementDao = new AchievementDao_Impl(this);
        }
        return _achievementDao;
      }
    }
  }
}
