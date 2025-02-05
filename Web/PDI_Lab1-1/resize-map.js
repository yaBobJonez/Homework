/*
 * Цей скрипт на JavaScript власноруч написаний мною задля можливості динамічної зміни координат елементів
 * `area` обʼєкта `map` при зміні розмірів сторінки або самого елемента. Пояснення надані у коментарях.
 */

// Знаходимо необхідні елементи: img, map, масив area.
const img = document.querySelector('img[usemap="#kai-map"]');
const map = document.querySelector('map[name="kai-map"]');
const areas = map.querySelectorAll('area');

// Обчислюємо масив координат зон базового (повнорозмірного) зображення:
const defaultCoords = [];
areas.forEach(area => {
    // для кожної зони отримуємо атрибут coords та ділимо значення на окремі числа комами.
    let coords = area.getAttribute("coords").split(',');
    defaultCoords.push(coords);
});

// Функція-обробник подій, що власне змінює розмір.
function resizeMap() {
    // Визначаємо множник довжини зображення порівняно з оригіналом.
    const proportion = img.width / img.naturalWidth;
    for (let i = 0; i < areas.length; i++) {
        // Множимо кожне типове значення координати на множник та обʼєднуємо знову комами.
        const newCoords = defaultCoords[i].map(c => Math.floor(c * proportion)).join(',');
        // Встановлюємо нове значення координат атрибутові coords зони.
        areas[i].setAttribute("coords", newCoords);
    }
}
// Закріплюємо подію зміни розміру зображення мапи за функцією вище.
new ResizeObserver(resizeMap).observe(img);
