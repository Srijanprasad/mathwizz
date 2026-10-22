package com.mathsprint.game.data.local.dao;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.mathsprint.game.data.local.entity.PlayerStatsEntity;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class PlayerStatsDao_Impl implements PlayerStatsDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<PlayerStatsEntity> __insertionAdapterOfPlayerStatsEntity;

  private final SharedSQLiteStatement __preparedStmtOfClear;

  public PlayerStatsDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfPlayerStatsEntity = new EntityInsertionAdapter<PlayerStatsEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `player_stats` (`id`,`totalXp`,`level`,`totalGamesPlayed`,`totalQuestionsAnswered`,`totalCorrectAnswers`,`bestStreak`,`currentDailyStreak`,`highestScore`,`lastActiveDate`,`unlockedBandsCsv`) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final PlayerStatsEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getTotalXp());
        statement.bindLong(3, entity.getLevel());
        statement.bindLong(4, entity.getTotalGamesPlayed());
        statement.bindLong(5, entity.getTotalQuestionsAnswered());
        statement.bindLong(6, entity.getTotalCorrectAnswers());
        statement.bindLong(7, entity.getBestStreak());
        statement.bindLong(8, entity.getCurrentDailyStreak());
        statement.bindLong(9, entity.getHighestScore());
        statement.bindString(10, entity.getLastActiveDate());
        statement.bindString(11, entity.getUnlockedBandsCsv());
      }
    };
    this.__preparedStmtOfClear = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM player_stats";
        return _query;
      }
    };
  }

  @Override
  public Object insertOrUpdate(final PlayerStatsEntity stats,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfPlayerStatsEntity.insert(stats);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object clear(final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfClear.acquire();
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfClear.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<PlayerStatsEntity> getPlayerStats() {
    final String _sql = "SELECT * FROM player_stats WHERE id = 1 LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"player_stats"}, new Callable<PlayerStatsEntity>() {
      @Override
      @Nullable
      public PlayerStatsEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTotalXp = CursorUtil.getColumnIndexOrThrow(_cursor, "totalXp");
          final int _cursorIndexOfLevel = CursorUtil.getColumnIndexOrThrow(_cursor, "level");
          final int _cursorIndexOfTotalGamesPlayed = CursorUtil.getColumnIndexOrThrow(_cursor, "totalGamesPlayed");
          final int _cursorIndexOfTotalQuestionsAnswered = CursorUtil.getColumnIndexOrThrow(_cursor, "totalQuestionsAnswered");
          final int _cursorIndexOfTotalCorrectAnswers = CursorUtil.getColumnIndexOrThrow(_cursor, "totalCorrectAnswers");
          final int _cursorIndexOfBestStreak = CursorUtil.getColumnIndexOrThrow(_cursor, "bestStreak");
          final int _cursorIndexOfCurrentDailyStreak = CursorUtil.getColumnIndexOrThrow(_cursor, "currentDailyStreak");
          final int _cursorIndexOfHighestScore = CursorUtil.getColumnIndexOrThrow(_cursor, "highestScore");
          final int _cursorIndexOfLastActiveDate = CursorUtil.getColumnIndexOrThrow(_cursor, "lastActiveDate");
          final int _cursorIndexOfUnlockedBandsCsv = CursorUtil.getColumnIndexOrThrow(_cursor, "unlockedBandsCsv");
          final PlayerStatsEntity _result;
          if (_cursor.moveToFirst()) {
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final int _tmpTotalXp;
            _tmpTotalXp = _cursor.getInt(_cursorIndexOfTotalXp);
            final int _tmpLevel;
            _tmpLevel = _cursor.getInt(_cursorIndexOfLevel);
            final int _tmpTotalGamesPlayed;
            _tmpTotalGamesPlayed = _cursor.getInt(_cursorIndexOfTotalGamesPlayed);
            final int _tmpTotalQuestionsAnswered;
            _tmpTotalQuestionsAnswered = _cursor.getInt(_cursorIndexOfTotalQuestionsAnswered);
            final int _tmpTotalCorrectAnswers;
            _tmpTotalCorrectAnswers = _cursor.getInt(_cursorIndexOfTotalCorrectAnswers);
            final int _tmpBestStreak;
            _tmpBestStreak = _cursor.getInt(_cursorIndexOfBestStreak);
            final int _tmpCurrentDailyStreak;
            _tmpCurrentDailyStreak = _cursor.getInt(_cursorIndexOfCurrentDailyStreak);
            final int _tmpHighestScore;
            _tmpHighestScore = _cursor.getInt(_cursorIndexOfHighestScore);
            final String _tmpLastActiveDate;
            _tmpLastActiveDate = _cursor.getString(_cursorIndexOfLastActiveDate);
            final String _tmpUnlockedBandsCsv;
            _tmpUnlockedBandsCsv = _cursor.getString(_cursorIndexOfUnlockedBandsCsv);
            _result = new PlayerStatsEntity(_tmpId,_tmpTotalXp,_tmpLevel,_tmpTotalGamesPlayed,_tmpTotalQuestionsAnswered,_tmpTotalCorrectAnswers,_tmpBestStreak,_tmpCurrentDailyStreak,_tmpHighestScore,_tmpLastActiveDate,_tmpUnlockedBandsCsv);
          } else {
            _result = null;
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

  @Override
  public Object getPlayerStatsSync(final Continuation<? super PlayerStatsEntity> $completion) {
    final String _sql = "SELECT * FROM player_stats WHERE id = 1 LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<PlayerStatsEntity>() {
      @Override
      @Nullable
      public PlayerStatsEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTotalXp = CursorUtil.getColumnIndexOrThrow(_cursor, "totalXp");
          final int _cursorIndexOfLevel = CursorUtil.getColumnIndexOrThrow(_cursor, "level");
          final int _cursorIndexOfTotalGamesPlayed = CursorUtil.getColumnIndexOrThrow(_cursor, "totalGamesPlayed");
          final int _cursorIndexOfTotalQuestionsAnswered = CursorUtil.getColumnIndexOrThrow(_cursor, "totalQuestionsAnswered");
          final int _cursorIndexOfTotalCorrectAnswers = CursorUtil.getColumnIndexOrThrow(_cursor, "totalCorrectAnswers");
          final int _cursorIndexOfBestStreak = CursorUtil.getColumnIndexOrThrow(_cursor, "bestStreak");
          final int _cursorIndexOfCurrentDailyStreak = CursorUtil.getColumnIndexOrThrow(_cursor, "currentDailyStreak");
          final int _cursorIndexOfHighestScore = CursorUtil.getColumnIndexOrThrow(_cursor, "highestScore");
          final int _cursorIndexOfLastActiveDate = CursorUtil.getColumnIndexOrThrow(_cursor, "lastActiveDate");
          final int _cursorIndexOfUnlockedBandsCsv = CursorUtil.getColumnIndexOrThrow(_cursor, "unlockedBandsCsv");
          final PlayerStatsEntity _result;
          if (_cursor.moveToFirst()) {
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final int _tmpTotalXp;
            _tmpTotalXp = _cursor.getInt(_cursorIndexOfTotalXp);
            final int _tmpLevel;
            _tmpLevel = _cursor.getInt(_cursorIndexOfLevel);
            final int _tmpTotalGamesPlayed;
            _tmpTotalGamesPlayed = _cursor.getInt(_cursorIndexOfTotalGamesPlayed);
            final int _tmpTotalQuestionsAnswered;
            _tmpTotalQuestionsAnswered = _cursor.getInt(_cursorIndexOfTotalQuestionsAnswered);
            final int _tmpTotalCorrectAnswers;
            _tmpTotalCorrectAnswers = _cursor.getInt(_cursorIndexOfTotalCorrectAnswers);
            final int _tmpBestStreak;
            _tmpBestStreak = _cursor.getInt(_cursorIndexOfBestStreak);
            final int _tmpCurrentDailyStreak;
            _tmpCurrentDailyStreak = _cursor.getInt(_cursorIndexOfCurrentDailyStreak);
            final int _tmpHighestScore;
            _tmpHighestScore = _cursor.getInt(_cursorIndexOfHighestScore);
            final String _tmpLastActiveDate;
            _tmpLastActiveDate = _cursor.getString(_cursorIndexOfLastActiveDate);
            final String _tmpUnlockedBandsCsv;
            _tmpUnlockedBandsCsv = _cursor.getString(_cursorIndexOfUnlockedBandsCsv);
            _result = new PlayerStatsEntity(_tmpId,_tmpTotalXp,_tmpLevel,_tmpTotalGamesPlayed,_tmpTotalQuestionsAnswered,_tmpTotalCorrectAnswers,_tmpBestStreak,_tmpCurrentDailyStreak,_tmpHighestScore,_tmpLastActiveDate,_tmpUnlockedBandsCsv);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
