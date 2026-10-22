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
import com.mathsprint.game.data.local.entity.GameSessionEntity;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Integer;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class GameSessionDao_Impl implements GameSessionDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<GameSessionEntity> __insertionAdapterOfGameSessionEntity;

  private final SharedSQLiteStatement __preparedStmtOfClearAll;

  public GameSessionDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfGameSessionEntity = new EntityInsertionAdapter<GameSessionEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `game_sessions` (`sessionId`,`mode`,`category`,`difficulty`,`totalQuestions`,`correctCount`,`accuracyPercent`,`totalScore`,`maxStreak`,`durationSeconds`,`timestamp`) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final GameSessionEntity entity) {
        statement.bindString(1, entity.getSessionId());
        statement.bindString(2, entity.getMode());
        statement.bindString(3, entity.getCategory());
        statement.bindString(4, entity.getDifficulty());
        statement.bindLong(5, entity.getTotalQuestions());
        statement.bindLong(6, entity.getCorrectCount());
        statement.bindLong(7, entity.getAccuracyPercent());
        statement.bindLong(8, entity.getTotalScore());
        statement.bindLong(9, entity.getMaxStreak());
        statement.bindLong(10, entity.getDurationSeconds());
        statement.bindLong(11, entity.getTimestamp());
      }
    };
    this.__preparedStmtOfClearAll = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM game_sessions";
        return _query;
      }
    };
  }

  @Override
  public Object insertSession(final GameSessionEntity session,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfGameSessionEntity.insert(session);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object clearAll(final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfClearAll.acquire();
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
          __preparedStmtOfClearAll.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<GameSessionEntity>> getAllSessions() {
    final String _sql = "SELECT * FROM game_sessions ORDER BY timestamp DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"game_sessions"}, new Callable<List<GameSessionEntity>>() {
      @Override
      @NonNull
      public List<GameSessionEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfSessionId = CursorUtil.getColumnIndexOrThrow(_cursor, "sessionId");
          final int _cursorIndexOfMode = CursorUtil.getColumnIndexOrThrow(_cursor, "mode");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final int _cursorIndexOfDifficulty = CursorUtil.getColumnIndexOrThrow(_cursor, "difficulty");
          final int _cursorIndexOfTotalQuestions = CursorUtil.getColumnIndexOrThrow(_cursor, "totalQuestions");
          final int _cursorIndexOfCorrectCount = CursorUtil.getColumnIndexOrThrow(_cursor, "correctCount");
          final int _cursorIndexOfAccuracyPercent = CursorUtil.getColumnIndexOrThrow(_cursor, "accuracyPercent");
          final int _cursorIndexOfTotalScore = CursorUtil.getColumnIndexOrThrow(_cursor, "totalScore");
          final int _cursorIndexOfMaxStreak = CursorUtil.getColumnIndexOrThrow(_cursor, "maxStreak");
          final int _cursorIndexOfDurationSeconds = CursorUtil.getColumnIndexOrThrow(_cursor, "durationSeconds");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final List<GameSessionEntity> _result = new ArrayList<GameSessionEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final GameSessionEntity _item;
            final String _tmpSessionId;
            _tmpSessionId = _cursor.getString(_cursorIndexOfSessionId);
            final String _tmpMode;
            _tmpMode = _cursor.getString(_cursorIndexOfMode);
            final String _tmpCategory;
            _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
            final String _tmpDifficulty;
            _tmpDifficulty = _cursor.getString(_cursorIndexOfDifficulty);
            final int _tmpTotalQuestions;
            _tmpTotalQuestions = _cursor.getInt(_cursorIndexOfTotalQuestions);
            final int _tmpCorrectCount;
            _tmpCorrectCount = _cursor.getInt(_cursorIndexOfCorrectCount);
            final int _tmpAccuracyPercent;
            _tmpAccuracyPercent = _cursor.getInt(_cursorIndexOfAccuracyPercent);
            final int _tmpTotalScore;
            _tmpTotalScore = _cursor.getInt(_cursorIndexOfTotalScore);
            final int _tmpMaxStreak;
            _tmpMaxStreak = _cursor.getInt(_cursorIndexOfMaxStreak);
            final long _tmpDurationSeconds;
            _tmpDurationSeconds = _cursor.getLong(_cursorIndexOfDurationSeconds);
            final long _tmpTimestamp;
            _tmpTimestamp = _cursor.getLong(_cursorIndexOfTimestamp);
            _item = new GameSessionEntity(_tmpSessionId,_tmpMode,_tmpCategory,_tmpDifficulty,_tmpTotalQuestions,_tmpCorrectCount,_tmpAccuracyPercent,_tmpTotalScore,_tmpMaxStreak,_tmpDurationSeconds,_tmpTimestamp);
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

  @Override
  public Flow<List<GameSessionEntity>> getRecentSessions(final int limit) {
    final String _sql = "SELECT * FROM game_sessions ORDER BY timestamp DESC LIMIT ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, limit);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"game_sessions"}, new Callable<List<GameSessionEntity>>() {
      @Override
      @NonNull
      public List<GameSessionEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfSessionId = CursorUtil.getColumnIndexOrThrow(_cursor, "sessionId");
          final int _cursorIndexOfMode = CursorUtil.getColumnIndexOrThrow(_cursor, "mode");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final int _cursorIndexOfDifficulty = CursorUtil.getColumnIndexOrThrow(_cursor, "difficulty");
          final int _cursorIndexOfTotalQuestions = CursorUtil.getColumnIndexOrThrow(_cursor, "totalQuestions");
          final int _cursorIndexOfCorrectCount = CursorUtil.getColumnIndexOrThrow(_cursor, "correctCount");
          final int _cursorIndexOfAccuracyPercent = CursorUtil.getColumnIndexOrThrow(_cursor, "accuracyPercent");
          final int _cursorIndexOfTotalScore = CursorUtil.getColumnIndexOrThrow(_cursor, "totalScore");
          final int _cursorIndexOfMaxStreak = CursorUtil.getColumnIndexOrThrow(_cursor, "maxStreak");
          final int _cursorIndexOfDurationSeconds = CursorUtil.getColumnIndexOrThrow(_cursor, "durationSeconds");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final List<GameSessionEntity> _result = new ArrayList<GameSessionEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final GameSessionEntity _item;
            final String _tmpSessionId;
            _tmpSessionId = _cursor.getString(_cursorIndexOfSessionId);
            final String _tmpMode;
            _tmpMode = _cursor.getString(_cursorIndexOfMode);
            final String _tmpCategory;
            _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
            final String _tmpDifficulty;
            _tmpDifficulty = _cursor.getString(_cursorIndexOfDifficulty);
            final int _tmpTotalQuestions;
            _tmpTotalQuestions = _cursor.getInt(_cursorIndexOfTotalQuestions);
            final int _tmpCorrectCount;
            _tmpCorrectCount = _cursor.getInt(_cursorIndexOfCorrectCount);
            final int _tmpAccuracyPercent;
            _tmpAccuracyPercent = _cursor.getInt(_cursorIndexOfAccuracyPercent);
            final int _tmpTotalScore;
            _tmpTotalScore = _cursor.getInt(_cursorIndexOfTotalScore);
            final int _tmpMaxStreak;
            _tmpMaxStreak = _cursor.getInt(_cursorIndexOfMaxStreak);
            final long _tmpDurationSeconds;
            _tmpDurationSeconds = _cursor.getLong(_cursorIndexOfDurationSeconds);
            final long _tmpTimestamp;
            _tmpTimestamp = _cursor.getLong(_cursorIndexOfTimestamp);
            _item = new GameSessionEntity(_tmpSessionId,_tmpMode,_tmpCategory,_tmpDifficulty,_tmpTotalQuestions,_tmpCorrectCount,_tmpAccuracyPercent,_tmpTotalScore,_tmpMaxStreak,_tmpDurationSeconds,_tmpTimestamp);
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

  @Override
  public Object getSessionCount(final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT COUNT(*) FROM game_sessions";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if (_cursor.moveToFirst()) {
            final int _tmp;
            _tmp = _cursor.getInt(0);
            _result = _tmp;
          } else {
            _result = 0;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getHighestScore(final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT MAX(totalScore) FROM game_sessions";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Integer>() {
      @Override
      @Nullable
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if (_cursor.moveToFirst()) {
            final Integer _tmp;
            if (_cursor.isNull(0)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getInt(0);
            }
            _result = _tmp;
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

  @Override
  public Object getBestStreak(final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT MAX(maxStreak) FROM game_sessions";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Integer>() {
      @Override
      @Nullable
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if (_cursor.moveToFirst()) {
            final Integer _tmp;
            if (_cursor.isNull(0)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getInt(0);
            }
            _result = _tmp;
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
