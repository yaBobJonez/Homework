---
layout: default
title: Условные программы
nav_order: 2
parent: Информатика (Java)
description: "Курс Java на уроках Информатики."
has_toc: false
nav_exclude: true
search_exclude: true
---

# Условные программы, или ветвление

Проверить условие, выполнить код А если правда, и Б если ложь, можно через ключевые слова "если" и "иначе":

```java
if(<условие>){
    <Если true>
} else{
    <Иначе>
}
```

Тернарный (троичный) оператор:

```java
boolean var = (condition)? true : false;
```
есть короткой заменой if/else. Код выше работает также само, как и код ниже:

```java
if(condition) boolean var = true;
else boolean var = false;
```

Утверждение switch ("переключатель") есть заменой нескольким утверждениям if/else с оператором равно (==):

```java
switch(var){
    case "a": <Делать А>; break;
    case "b":
    case "c": <Делать B>; break;
    default: <Иначе>;
}
```
это тоже самое, что и:

```java
if(var == "a"){
    <Делать А>;
} else if(var == "b" || var == "c"){
    <Делать B>;
} else{
    <Иначе>
}
```

Логические операторы:

- `==` равно
- `!=` не равно
- `!` не (унарный)
- `&&` и
- `&` и (побитовый)
- `||` или
- `|` или (побитовый)
- `^` исключающие или