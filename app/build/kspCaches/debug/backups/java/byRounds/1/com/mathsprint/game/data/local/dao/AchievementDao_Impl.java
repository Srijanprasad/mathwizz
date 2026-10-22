package com.mathsprint.game.data.local.dao;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.mathsprint.game.data.local.entity.AchievementEntity;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Integer;
import java.lang.Long;
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
public final class AchievementDao_Impl implements AchievementDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<AchievementEntity> __insertionAdapterOfAchievementEntity;

  public AchievementDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfAchievementEntity = new EntityInsertionAdapter<AchievementEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `achievements` (`id`,`title`,`description`,`iconName`,`isUnlocked`,`currentProgress`,`maxProgress`,`unlockedAt`) VALUES (?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final AchievementEntity entity) {
        statement.bindString(1, entity.getId());
        statement.bindString(2, entity.getTitle());
        statement.bindString(3, entity.getDescription());
        statement.bindString(4, entity.getIconName());
        final int _tmp = entity.isUnlocked() ? 1 : 0;
        statement.bindLong(5, _tmp);
        statement.bindLong(6, entity.getCurrentProgress());
        statement.bindLong(7, entity.getMaxProgress());
        if (entity.getUnlockedAt() == null) {
          statement.bindNull(8);
        } else {
          statement.bindLong(8, entity.getUnlockedAt());
        }
      }
    };
  }

  @Override
  public Object insertAll(final List<AchievementEntity> achievements,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfAchievementEntity.insert(achievements);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object update(final AchievementEntity achievement,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfAchievementEntity.insert(achievement);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<AchievementEntity>> getAllAchievements() {
    final String _sql = "SELECT * FROM achievements";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"achievements"}, new Callable<List<AchievementEntity>>() {
      @Override
      @NonNull
      public List<AchievementEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfIconName = CursorUtil.getColumnIndexOrThrow(_cursor, "iconName");
          final int _cursorIndexOfIsUnlocked = CursorUtil.getColumnIndexOrThrow(_cursor, "isUnlocked");
          final int _cursorIndexOfCurrentProgress = CursorUtil.getColumnIndexOrThrow(_cursor, "currentProgress");
          final int _cursorIndexOfMaxProgress = CursorUtil.getColumnIndexOrThrow(_cursor, "maxProgress");
          final int _cursorIndexOfUnlockedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "unlockedAt");
          final List<AchievementEntity> _result = new ArrayList<AchievementEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final AchievementEntity _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            final String _tmpIconName;
            _tmpIconName = _cursor.getString(_cursorIndexOfIconName);
            final boolean _tmpIsUnlocked;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsUnlocked);
            _tmpIsUnlocked = _tmp != 0;
            final int _tmpCurrentProgress;
            _tmpCurrentProgress = _cursor.getInt(_cursorIndexOfCurrentProgress);
            final int _tmpMaxProgress;
            _tmpMaxProgress = _cursor.getInt(_cursorIndexOfMaxProgress);
            final Long _tmpUnlockedAt;
            if (_cursor.isNull(_cursorIndexOfUnlockedAt)) {
              _tmpUnlockedAt = null;
            } else {
              _tmpUnlockedAt = _cursor.getLong(_cursorIndexOfUnlockedAt);
            }
            _item = new AchievementEntity(_tmpId,_tmpTitle,_tmpDescription,_tmpIconName,_tmpIsUnlocked,_tmpCurrentProgress,_tmpMaxProgress,_tmpUnlockedAt);
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
  public Flow<List<AchievementEntity>> getUnlockedAchievements() {
    final String _sql = "SELECT * FROM achievements WHERE isUnlocked = 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"achievements"}, new Callable<List<AchievementEntity>>() {
      @Override
      @NonNull
      public List<AchievementEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfIconName = CursorUtil.getColumnIndexOrThrow(_cursor, "iconName");
          final int _cursorIndexOfIsUnlocked = CursorUtil.getColumnIndexOrThrow(_cursor, "isUnlocked");
          final int _cursorIndexOfCurrentProgress = CursorUtil.getColumnIndexOrThrow(_cursor, "currentProgress");
          final int _cursorIndexOfMaxProgress = CursorUtil.getColumnIndexOrThrow(_cursor, "maxProgress");
          final int _cursorIndexOfUnlockedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "unlockedAt");
          final List<AchievementEntity> _result = new ArrayList<AchievementEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final AchievementEntity _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            final String _tmpIconName;
            _tmpIconName = _cursor.getString(_cursorIndexOfIconName);
            final boolean _tmpIsUnlocked;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsUnlocked);
            _tmpIsUnlocked = _tmp != 0;
            final int _tmpCurrentProgress;
            _tmpCurrentProgress = _cursor.getInt(_cursorIndexOfCurrentProgress);
            final int _tmpMaxProgress;
            _tmpMaxProgress = _cursor.getInt(_cursorIndexOfMaxProgress);
            final Long _tmpUnlockedAt;
            if (_cursor.isNull(_cursorIndexOfUnlockedAt)) {
              _tmpUnlockedAt = null;
            } else {
              _tmpUnlockedAt = _cursor.getLong(_cursorIndexOfUnlockedAt);
            }
            _item = new AchievementEntity(_tmpId,_tmpTitle,_tmpDescription,_tmpIconName,_tmpIsUnlocked,_tmpCurrentProgress,_tmpMaxProgress,_tmpUnlockedAt);
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
  public Object getUnlockedCount(final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT COUNT(*) FROM achievements WHERE isUnlocked = 1";
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

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
