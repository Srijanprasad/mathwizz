package com.mathsprint.game.data.local.dao;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.mathsprint.game.data.local.entity.DailyChallengeEntity;
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
public final class DailyChallengeDao_Impl implements DailyChallengeDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<DailyChallengeEntity> __insertionAdapterOfDailyChallengeEntity;

  public DailyChallengeDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfDailyChallengeEntity = new EntityInsertionAdapter<DailyChallengeEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `daily_challenges` (`dateKey`,`isCompleted`,`bestScore`,`accuracyPercent`,`completedAt`) VALUES (?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final DailyChallengeEntity entity) {
        statement.bindString(1, entity.getDateKey());
        final int _tmp = entity.isCompleted() ? 1 : 0;
        statement.bindLong(2, _tmp);
        statement.bindLong(3, entity.getBestScore());
        statement.bindLong(4, entity.getAccuracyPercent());
        statement.bindLong(5, entity.getCompletedAt());
      }
    };
  }

  @Override
  public Object insertOrUpdate(final DailyChallengeEntity challenge,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfDailyChallengeEntity.insert(challenge);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object getChallenge(final String dateKey,
      final Continuation<? super DailyChallengeEntity> $completion) {
    final String _sql = "SELECT * FROM daily_challenges WHERE dateKey = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, dateKey);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<DailyChallengeEntity>() {
      @Override
      @Nullable
      public DailyChallengeEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfDateKey = CursorUtil.getColumnIndexOrThrow(_cursor, "dateKey");
          final int _cursorIndexOfIsCompleted = CursorUtil.getColumnIndexOrThrow(_cursor, "isCompleted");
          final int _cursorIndexOfBestScore = CursorUtil.getColumnIndexOrThrow(_cursor, "bestScore");
          final int _cursorIndexOfAccuracyPercent = CursorUtil.getColumnIndexOrThrow(_cursor, "accuracyPercent");
          final int _cursorIndexOfCompletedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "completedAt");
          final DailyChallengeEntity _result;
          if (_cursor.moveToFirst()) {
            final String _tmpDateKey;
            _tmpDateKey = _cursor.getString(_cursorIndexOfDateKey);
            final boolean _tmpIsCompleted;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsCompleted);
            _tmpIsCompleted = _tmp != 0;
            final int _tmpBestScore;
            _tmpBestScore = _cursor.getInt(_cursorIndexOfBestScore);
            final int _tmpAccuracyPercent;
            _tmpAccuracyPercent = _cursor.getInt(_cursorIndexOfAccuracyPercent);
            final long _tmpCompletedAt;
            _tmpCompletedAt = _cursor.getLong(_cursorIndexOfCompletedAt);
            _result = new DailyChallengeEntity(_tmpDateKey,_tmpIsCompleted,_tmpBestScore,_tmpAccuracyPercent,_tmpCompletedAt);
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
  public Flow<DailyChallengeEntity> observeChallenge(final String dateKey) {
    final String _sql = "SELECT * FROM daily_challenges WHERE dateKey = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, dateKey);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"daily_challenges"}, new Callable<DailyChallengeEntity>() {
      @Override
      @Nullable
      public DailyChallengeEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfDateKey = CursorUtil.getColumnIndexOrThrow(_cursor, "dateKey");
          final int _cursorIndexOfIsCompleted = CursorUtil.getColumnIndexOrThrow(_cursor, "isCompleted");
          final int _cursorIndexOfBestScore = CursorUtil.getColumnIndexOrThrow(_cursor, "bestScore");
          final int _cursorIndexOfAccuracyPercent = CursorUtil.getColumnIndexOrThrow(_cursor, "accuracyPercent");
          final int _cursorIndexOfCompletedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "completedAt");
          final DailyChallengeEntity _result;
          if (_cursor.moveToFirst()) {
            final String _tmpDateKey;
            _tmpDateKey = _cursor.getString(_cursorIndexOfDateKey);
            final boolean _tmpIsCompleted;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsCompleted);
            _tmpIsCompleted = _tmp != 0;
            final int _tmpBestScore;
            _tmpBestScore = _cursor.getInt(_cursorIndexOfBestScore);
            final int _tmpAccuracyPercent;
            _tmpAccuracyPercent = _cursor.getInt(_cursorIndexOfAccuracyPercent);
            final long _tmpCompletedAt;
            _tmpCompletedAt = _cursor.getLong(_cursorIndexOfCompletedAt);
            _result = new DailyChallengeEntity(_tmpDateKey,_tmpIsCompleted,_tmpBestScore,_tmpAccuracyPercent,_tmpCompletedAt);
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
  public Object getCompletedCount(final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT COUNT(*) FROM daily_challenges WHERE isCompleted = 1";
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
  public Flow<List<DailyChallengeEntity>> getRecentChallenges() {
    final String _sql = "SELECT * FROM daily_challenges ORDER BY dateKey DESC LIMIT 30";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"daily_challenges"}, new Callable<List<DailyChallengeEntity>>() {
      @Override
      @NonNull
      public List<DailyChallengeEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfDateKey = CursorUtil.getColumnIndexOrThrow(_cursor, "dateKey");
          final int _cursorIndexOfIsCompleted = CursorUtil.getColumnIndexOrThrow(_cursor, "isCompleted");
          final int _cursorIndexOfBestScore = CursorUtil.getColumnIndexOrThrow(_cursor, "bestScore");
          final int _cursorIndexOfAccuracyPercent = CursorUtil.getColumnIndexOrThrow(_cursor, "accuracyPercent");
          final int _cursorIndexOfCompletedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "completedAt");
          final List<DailyChallengeEntity> _result = new ArrayList<DailyChallengeEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final DailyChallengeEntity _item;
            final String _tmpDateKey;
            _tmpDateKey = _cursor.getString(_cursorIndexOfDateKey);
            final boolean _tmpIsCompleted;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsCompleted);
            _tmpIsCompleted = _tmp != 0;
            final int _tmpBestScore;
            _tmpBestScore = _cursor.getInt(_cursorIndexOfBestScore);
            final int _tmpAccuracyPercent;
            _tmpAccuracyPercent = _cursor.getInt(_cursorIndexOfAccuracyPercent);
            final long _tmpCompletedAt;
            _tmpCompletedAt = _cursor.getLong(_cursorIndexOfCompletedAt);
            _item = new DailyChallengeEntity(_tmpDateKey,_tmpIsCompleted,_tmpBestScore,_tmpAccuracyPercent,_tmpCompletedAt);
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
