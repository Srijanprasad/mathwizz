# Room Database Proguard Rules
-keepclassmembers class * extends androidx.room.RoomDatabase {
    <init>();
}
-keep class * extends androidx.room.RoomDatabase
-dontwarn androidx.room.paging.**

# Keep models serialized in Room
-keep class com.mathsprint.game.data.local.entity.** { *; }
-keep class com.mathsprint.game.domain.model.** { *; }

# Compose Keep Rules
-keepclassmembers class * {
    @androidx.compose.runtime.Composable *;
}
