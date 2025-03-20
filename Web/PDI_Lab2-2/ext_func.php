<?php

function time_to_date($date) {
    // Отримуємо поточний час у секундах епохи UNIX
    $current_time = time();
    // Конвертуємо рядок у час
    $future_time = strtotime($date);
    // Обчислюємо різницю між поточним часом і введеною датою
    $diff = $future_time - $current_time;

    // Розрахунок кількості годин, хвилин і секунд
    $hours = floor($diff / 3600);
    $minutes = floor(($diff / 60) % 60);
    $seconds = $diff % 60;

    // Повертаємо результат у вигляді асоціативного масиву
    return [
        "hours" => $hours,
        "minutes" => $minutes,
        "seconds" => $seconds
    ];
}

?>
