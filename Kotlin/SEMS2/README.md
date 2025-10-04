# SimpleEvacManSys 2

Творчий проєкт з «Програмування на Kotlin» — мережева симуляція системи оповіщення про пожежі та управління евакуацією
людей (СОУЕ). Проєкт розроблений на фреймворці Compose Multiplatform з використанням технології WebSockets для повно
дуплексного обміну повідомленнями між пристроями у реальному часі. Складається з десктопної (JVM) частини – панелі
керування та мобільних (Android, iOS) застосунків – оповіщувачів (пожежних кнопок з вбудованими індикаторами). Завдяки
клієнт-серверній архітектурі, MVVC, власним Composable віджетам та управлінню станами, СОУЕ наближена до реальних
систем за надійністю та функціоналом. Через обмеження часу SEMS2 не локалізована, проте підтримує багато функції:

- активація та деактивація системи
- блокування підключень за IP-адресами
- відʼєднання клієнтів за рукостисканням
- «Підтвердження» (Acknowledge) та «Заглушення» (Silence) тривоги
- ручне ввімкнення з панелі
- безпечне скидання (вимагає, що всі пожежні кнопки були вимкнені, щоб відключити тривогу)
- звукові та світлові сигнали

### Структура файлів

Для реалізації сервера та клієнта WebSockets використовується відома бібліотека Ktor та широко застосовуються корутини.
У директорії, специфічній для [Android](./composeApp/src/androidMain/kotlin/com/yabobjonez/sems2), знаходиться
реалізація аудіоплеєра з використанням MediaPlayer. Для [iOS](./composeApp/src/iosMain/kotlin/com/yabobjonez/sems2)
такий програвач реалізується через AVFoundation (не тестувався через відсутність пристроїв від Apple).

[commonMain](./composeApp/src/commonMain) містить як аудіоресурси (wav файли), так і спільний для всіх платформ код
(здебільшого для Android та iOS) — інтерфейс, expected клас аудіопрогравача, кнопку з тривалим натисканням (майже
копіює стандартну реалізацію кнопки) та клієнт. [jvmMain](./composeApp/src/jvmMain/kotlin/com/yabobjonez/sems2) включає
інтерфейс десктопного застосунку, реалізацію аудіоплеєра на платформі Java, список клієнтів (візуальний компонент),
цифрову клавіатуру, клас керування станом програми та сервер.

Для збірки та запуску рекомендовано використовувати IntellĲ IDEA або Android Studio (еквівалентні за функціоналом),
проте нижче залишені інструкції для ручної збірки з системою Gradle (англійською мовою).

### Build and Run Android Application

To build and run the development version of the Android app, use the run configuration from the run widget
in your IDE’s toolbar or build it directly from the terminal:
- on macOS/Linux
  ```shell
  ./gradlew :composeApp:assembleDebug
  ```
- on Windows
  ```shell
  .\gradlew.bat :composeApp:assembleDebug
  ```

### Build and Run Desktop (JVM) Application

To build and run the development version of the desktop app, use the run configuration from the run widget
in your IDE’s toolbar or run it directly from the terminal:
- on macOS/Linux
  ```shell
  ./gradlew :composeApp:run
  ```
- on Windows
  ```shell
  .\gradlew.bat :composeApp:run
  ```

### Build and Run iOS Application

To build and run the development version of the iOS app, use the run configuration from the run widget
in your IDE’s toolbar or open the [/iosApp](./iosApp) directory in Xcode and run it from there.
