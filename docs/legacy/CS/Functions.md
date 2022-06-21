---
layout: default
title: Функции
nav_order: 5
parent: Информатика (Java)
description: "Курс Java на уроках Информатики."
has_toc: false
nav_exclude: true
search_exclude: true
---

# Функции

Для создания своей функции нужно сначала указать _область видимости_, потом _модификатор_ (опционально), _возвращаемый тип_, затем название функции, и с круглых скобках её _аргументы_, а в теле написать сам код:

```java
public static int sum(int a, int b){
    return a + b;
}
```
В примере выше, публичная статическая функция, которая принимает два аргумента — целых числа, и возвращает целое число.

Функции, находящиеся внутри класса называют _методами_. Вызвать метод внутри класса можно, указав имя, и скобки для аргументов:

```java
sum(2, 3);
```

Вызвать _статическую_ функцию внешне, из другого класса, указав имя класса, затем оператор доступа `.`, и имя метода:

```java
Class.Function();
```

Объекты есть инстанциями класса. Чтобы создать объект, нужно указать его тип (как в обычной переменной), и в качестве присвоения использовать слово `new`, указав конструктор класса:

```java
Class object = new Class();
```
Для доступа не-статических полей или вызова функций, используем объект и оператор доступа `.`:

```java
object.method();
object.field;
```
Для доступа статических методов и полей внутри класса, рекомендуеться для избежания ошибок дописывать `this` и оператор доступа. `this` указывает на текущий класс, в котором мы работаем:

```java
this.field;
this.method();
```

### Рекурсия

Рекурсия это многократное повторение функции с использованием самой себя. Рекурсивная функция выполняет условие, после чего вызывает себя же.

### Аппендикс

#### Область видимости

- `public`
- `protected`
- `private`
- Нету (default)

[Таблица сравнения](Classes)

#### Тип

`static`

#### Классовые

- `final`
- `abstract`

### Технические

- `transient`
- `volatile`
- `synchronized`

#### Возвращаемое значение

Функции могут выполняться отдельно, тогда используем `void` — указывает, что функция не возвращает значений с помощью `return`;
либо могут возвращать любой тип данных, например `int`, `double`, `String`, и так далее.

#### Аргументы

Аргументы это параметры функции, значения с которыми она работает. Может быть 0 или больше аргументов.
