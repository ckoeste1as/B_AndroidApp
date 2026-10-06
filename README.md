# Android Template

Native Android-Vorlage mit Kotlin, Jetpack Compose und Material 3. Enthalten sind eine Startseite mit Zähler, Navigation zu Einstellungen, System-/Hell-/Dunkelmodus, dynamische Farben ab Android 12, Compose-Previews und Beispieltests.

## Voraussetzungen und erster Start

Das Projekt wurde ohne Android-SDK erstellt. Ein Android-Build und die Tests wurden deshalb noch nicht ausgeführt.

1. Android Studio installieren und diesen Ordner als Projekt öffnen.
2. Im SDK Manager **Android SDK Platform 36**, **Build-Tools 35.0.0** und **Platform-Tools** installieren. Für einen Emulator zusätzlich Android Emulator und ein passendes System Image installieren.
3. Als Gradle JDK **JDK 17** einstellen; ein separat installiertes JDK 17 kann unter Settings → Build, Execution, Deployment → Build Tools → Gradle ausgewählt werden.
4. Einmal im Projektordner `powershell -NoProfile -ExecutionPolicy Bypass -File scripts/Setup-GradleWrapper.ps1` ausführen, dann Gradle in Android Studio synchronisieren. Beim ersten Start werden Wrapper, Gradle und Maven-Abhängigkeiten heruntergeladen; dafür ist Internetzugang erforderlich.
5. Einen Emulator oder ein Android-Gerät ab Android 8.0 (API 26) auswählen und die Run-Aktion für `app` ausführen.

Android Studio verwaltet den lokalen SDK-Pfad in `local.properties`. Alternativ diese ignorierte Datei selbst anlegen, beispielsweise mit `sdk.dir=C:/Users/DEIN_NAME/AppData/Local/Android/Sdk`. Keine Rechnerpfade ins Repository übernehmen.

## Kommandozeile nach SDK-Installation

`JAVA_HOME` muss auf JDK 17 zeigen; der SDK-Pfad muss über `local.properties` oder `ANDROID_HOME` verfügbar sein.

Die binäre `gradle-wrapper.jar` konnte in der Erstellungsumgebung wegen des gesperrten Netzwerkzugangs nicht heruntergeladen werden. Die mitgelieferten Startskripte laden sie beim ersten Aufruf vom offiziellen Gradle-Repository und prüfen SHA-256 gegen die von Gradle veröffentlichte Prüfsumme. Android Studio benötigt diesen einmaligen Vorbereitungsschritt vor dem ersten Sync. Anschließend die geprüfte JAR zusammen mit dem Projekt versionieren. Unter Linux/macOS benötigt der automatische Download `curl` und `sha256sum` oder `shasum`.

```powershell
.\gradlew.bat assembleDebug
.\gradlew.bat testDebugUnitTest lintDebug
.\gradlew.bat connectedDebugAndroidTest
```

Der letzte Befehl benötigt einen laufenden Emulator oder ein verbundenes Gerät. Unter Linux/macOS `sh ./gradlew` verwenden (oder `chmod +x gradlew` und danach `./gradlew`). Die Debug-APK liegt unter `app/build/outputs/apk/debug/app-debug.apk`.

## Aufbau und Anpassung

- `gradle/libs.versions.toml`: festgelegte Plugin- und Bibliotheksversionen.
- `app/build.gradle.kts`: Application-ID, SDK-Versionen und Build-Konfiguration.
- `MainActivity.kt`: Einstiegspunkt und Edge-to-Edge-Konfiguration.
- `ui/TemplateApp.kt`: Scaffold und Navigation mit Wiederherstellung der Tab-Zustände.
- `ui/AppViewModel.kt`: UI-Zustand mit `StateFlow` und `SavedStateHandle`.
- `ui/screens/`: zustandslose Beispielbildschirme mit Previews.
- `ui/theme/Theme.kt`: Material-3-Farben und Typografie.
- `res/values/strings.xml`: deutsche Texte; weitere Sprachen können über zusätzliche Ressourcenordner ergänzt werden.
- `src/test/`: JVM-Tests für Zähler und Zustandswiederherstellung.
- `src/androidTest/`: Compose-Test für Navigation, Theme-Auswahl und Zähler.

Für ein neues Produkt `applicationId` und `namespace` ändern, das Kotlin-Package über Android Studios Refactor-Funktion umbenennen und App-Namen sowie Icon ersetzen. Weitere Ziele in `TemplateApp.kt` ergänzen und eigene Bildschirme in `ui/screens` anlegen.

Zähler und Theme bleiben bei Konfigurationswechseln und Androids gespeicherter Zustandswiederherstellung erhalten. Es gibt noch keine dauerhafte Speicherung: Für Einstellungen über vollständige Neustarts hinweg beispielsweise DataStore ergänzen. Die App benötigt keine Berechtigungen und keinen Netzwerkzugang.

Release-Builds verwenden R8 und Ressourcenverkleinerung. Für eine Veröffentlichung muss eine eigene Signierung konfiguriert werden; Schlüssel und Passwörter gehören nicht ins Repository.

Die Vorlage verwendet AGP 8.13.2, Gradle 8.13, Kotlin/Compose-Compiler 2.2.21 und Compose BOM 2025.10.00 als festgelegte Basis. Die AGP-Anforderungen sind in der [offiziellen Kompatibilitätstabelle](https://developer.android.com/build/releases/agp-8-13-0-release-notes) dokumentiert. Compose-Bibliotheksversionen werden über die [Compose BOM](https://developer.android.com/develop/ui/compose/bom) abgestimmt.
