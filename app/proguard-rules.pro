-keepattributes *Annotation*, InnerClasses
-dontnote kotlinx.serialization.AnnotationsKt
-keepclassmembers class kotlinx.serialization.json.** {
    *** Companion;
}
-keepclasseswithmembers class com.nakudin.techhausa.model.** {
    *** Companion;
}
-keep,includedescriptorclasses class com.nakudin.techhausa.model.**$$serializer { *; }
-keepclassmembers class com.nakudin.techhausa.model.** {
    *** serializer(...);
}
