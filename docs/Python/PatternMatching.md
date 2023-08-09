---
layout: default
title: Зіставляння зі взірцем
nav_order: 5
parent: Інформатика (Python)
description: "Курс Python на уроках Інформатики."
has_toc: false
---

# Зіставляння зі взірцем

У житті нам не рідко необхідно пізнавати певні предмети або людей за деякими їхніми ознаками. Як саме Ви знаєте, що деякий телефон ваш, а інший телефон чужий? Кожний телефон різного кольору, має різну кількість камер, оперативної пам'яті тощо. Відрізняються й встановлені застосунки, налаштування. Ваш мозок має деякий _взірець_ (pattern), який складається з характеристик вашого телефону. Якщо взяти декілька пристроїв, Ви зіставите відомі такі параметри вашого телефону з кожним із них, тому безперечно знайдете саме ваш телефон, коли отримаєте _збіг_ (match).

Наведу конкретніший мінімальний приклад. Нехай ваш телефон синього кольору. Лежать на столі телефони червоного, чорного, синього та білого кольорів. Ви візуально поглянули б на кожен телефон, доки не побачили синій — враховуючи, що Ви знаєте лише колір, Ви б зробили висновок, що це ваш телефон.

## Деструктуризація

Перед тим як переходити безпосередньо до зіставляння зі взірцем, я пропоную розібратись з _деструктуризацією_ — операцією "розбиття" структур даних на окремі компоненти. У Python таким чином можна деструктуризувати списки, кортежі або множини, вказавши декілька змінних через кому перед знаком присвоєння.

```python
a, b = [2, 3] # a = 2, b = 3
c, d, e = (8, 'hi', True) # c = 8, d = 'hi', e = True
f, g = {'f', 14.82} # f = 'f', g = 14.82
```
Якщо розмір масиву не збігається з кількістю змінних, то буде помилка. Їй можна запобігти, використавши астериск (`*`, у народній мові "зірочка") перед змінною — він позначає "залишок" елементів. `*` можна використовувати у будь-якій частині виразу, однак лише один.

```python
a, *b, c = (8, 'hi', True, 'rraur') # a = 8, b = ['hi', True], c = 'rraur'
*d, e = ["I", "like", "waffles", ":D"] # d = ["I", "like", "waffles"], e = ":D"
```
Неформально, якщо деякі значення непотрібні, їх за звичаєм присвоюють змінній "_": `a, _ = {3.14, 6.28}`.

## match

**Нове у Python 3.10; у старіших версіях немає.**

Для зіставляння зі взірцем (pattern matching) у Python додали `match`. Він дещо схожий загальною схемою на `switch` у деяких інших мовах:

```python
match <значення>:
    case <шаблон>:
        <інструкції>
    #case ...
```
Шаблони (взірці) у Пайтоні можуть бути як невеликими значеннями, так і комплексними багатокомпонентними структурами. Така гнучкість та різноманіття шаблонів дозволяє писати лаконічний та експресивний код, який у інших мовай займав би безліч часу та рядків. У цьому підрозділі будуть описані коротко всі доступні шаблони, окрім, напевно, класів.

### Літерали та змінні

_Літерал_ — постійне значення у коді програми. Вони включають числа `3.4`, рядки `"a string"`, булеві значення `False` тощо. У шаблонах літерали будуть зіставлятись за значенням, тобто шаблон `1.0` також буде збігатись з цілим числом `1`. Винятком є булеві значення, які мають відповідати типу — тобто `1` з `True` не буде збігатись.

Вказавши деяку назву змінної як шаблон, значення завжди буде надано цій змінній. Таким чином сам шаблон буде завжди збігатись, а у `case` можна буде використовувати цю змінну.

```python
x = 2
match x:
    case 8:
        print("Не буде виконано")
    case y:
        print("y тепер дорівнює 2")
```
На даному етапі може бути незрозуміло, як це працює, однак коли Ви вивчите всі шаблони, застосування `match` можуть бути досить простими та корисними.

### Байдужі символи

Інколи деякі значення не потрібні або ми хочемо довільну їх кількість. Якщо Ви згадали про `*` та `_`, то саме так, вони також можуть бути шаблонами. Можливо, тепер здається, що зіставляння зі взірцем є аналогічним до деструктуризації. Частково так і є — `match` деякою мірою є if-else з деструктуризацією, а втім, шаблонів дещо більше і `match` дозволяє перевіряти по черзі кожен з них. До речі, `case _:` використовується як `else`, тому його пишуть останнім на випадок, якщо жоден попередній шаблон не підійшов.

### Послідовності

Очевидно, що порівнювати за шаблонами окремі одиничні значення було б не дуже корисно. У Python  є можливість зіставлення зі шаблонами-списками. Так, можна зіставляти окремі шаблони для кожного елементу наданого контейнера.

Наприклад, у рядків є метод `split()`, що перетворює рядок у список елементів через пропуски (наприклад, `"Hello world!".split() # ["Hello", "world!"]`). Нехай потрібно дізнатись напрямок, у якому слід піти, або ж вивести помилку, якщо команда не існує. Це можна реалізувати так:

```python
match input().split():
    case ["йти", direction]:
        print("Ви пішли", direction)
    case _:
        print("Невідома команда!")
```
Тепер якщо ввести "йти ліворуч", буде виведено "Ви пішли ліворуч", а якщо ввести "я хочу їсти", буде "Невідома команда!" :)

Так само можна "розділити" словник на шаблони:

```python
match someDict:
    case {'color': color}:
        print("Колір об'єкта: " + color)
    #...
```

### АБО, "як"

Останніми із простих шаблонів є шаблон "АБО" та ключове слово "як" — `as`. У випадку, коли потрібно дозволити декілька варіантів (шаблонів), тобто "А або Б або В або...", можна розділити кілька шаблонів знайомим знаком `|`. Як тоді дізнатись, який саме з них підійшов? Можна присвоїти значення ключовим словом `as`. Зверніть увагу, що у шаблонах круглі дужки працюють як у звичайних виразах — для порядку операцій (групування).

```python
match value:
    case ["обрати", ("північ"|"південь"|"захід"|"схід") as direction]:
        print("Ви обрали " + direction)
    case ["обрати", _]:
        print("Такої сторони не існує!")
    case _:
        print("Невідома дія!")
```

## Варта (guards)

Інколи бувають випадки, коли навіть цього переліку шаблонів недостатньо, і хочеться використати звичайні логічні вирази. Це дозволяє зробити _вираз-варта_ (pattern guard). Такі вирази записуюють як `if` та логічний вираз після основного шаблону.

```python
match value:
    case [x, y] if x < y:
        print("Перше число менше ніж друге.")
    case [x, y] if x > y:
        print("Перше число більше ніж друге.")
    case [x, y]:
        print("Два однакових числа.")
    case _:
        print("Щось інше.")
```

## Детальніше

Дізнатися повну інформацію англійською мовою про зіставляння зі взірцем можна у [PEP 636](https://peps.python.org/pep-0636/).