<!DOCTYPE html>
<html lang="uk">
<head>
    <meta charset="UTF-8">
    <title>Лабораторна 2.2 - Стецюк</title>
    <link rel="stylesheet" href="style.css">
</head>
<?php
    // Підключаємо зовнішній файл
    require "ext_func.php";

    // Введена дата
    $date = isset($_GET["date"])? $_GET["date"] : '';
    // Якщо форма отримала дату, обчислюємо результат у масив
    $result = isset($_GET["date"])? time_to_date($_GET["date"]) : [];
    // Встановлюємо значення полям з результатом
    $hours = $result["hours"] ?? '';
    $minutes = $result["minutes"] ?? '';
    $seconds = $result["seconds"] ?? '';
?>
<body>
<main>
    <h1>Час до дати</h1>
    <!-- Без action форма відправляє на ту ж сторінку -->
    <form method="get">
        <fieldset>
            <legend>Виберіть дату у майбутньому.</legend>
            <input type="date" id="date" name="date" required
                min="<?php echo date('Y-m-d'); ?>"
                value="<?php echo $date; ?>">
            <input type="submit" value="Обчислити">
        </fieldset>
        <fieldset>
            <legend>Результат буде показано нижче.</legend>
            <label for="hours">
                Годин
                <input type="number" id="hours" readonly
                    value="<?php echo $hours ?>">
            </label>
            <label for="minutes">
                Хвилин
                <input type="number" id="minutes" readonly
                    value="<?php echo $minutes ?>">
            </label>
            <label for="seconds">
                Секунд
                <input type="number" id="seconds" readonly
                    value="<?php echo $seconds ?>">
            </label>
        </fieldset>
    </form>
</main>
</body>
</html>
