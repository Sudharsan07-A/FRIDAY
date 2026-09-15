-keep class com.friday.ai.** { *; }
-keepclassmembers class com.friday.ai.** { *; }
-keep class kotlinx.serialization.** { *; }
-keepclassmembers class * {
    @kotlinx.serialization.Serializable *;
}
