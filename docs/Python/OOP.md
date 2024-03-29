---
layout: default
title: Об'єктно-орієнтоване програмування
nav_order: 9
parent: Інформатика (Python)
description: "Курс Python на уроках Інформатики."
has_toc: false
---

# ООП

З Java Ви вже знаєте, що _об'єктно-орієнтоване програмування_ — це _парадигма_ програмування, що полягає у використанні об'єктів та класів для написання структури програм. Відомо, що ООП полягає у принципах _інкапсуляції_, _успадкування_, _поліморфізму_ та _абстракції_.

## Інкапсуляція

У Python досить простий синтаксис створення класів — ключове слово і назва класу: `class <назва>:`. Створення об'єктів натомість виконується у стилі виклику назви класу як функції: уявіть, що відбувається виклик конструктора: `<назва класу>(<аргументи>)`.

Класи містять змінні (атрибути) та функції (методи). Ось тільки для останніх першим завжди треба вказувати параметр `self`, навіть якщо функція не прийматиме аргументів. Цей параметр є заміною `this` з Java та використовується для доступу до членів класу. У присвоєнні `self.` завжди важливо вказувати, адже інакше буде присвоєно значення новій локальній змінній! `self` як перший параметр — це особливість мови. Python передаватиме об'єкт класу у цей параметр.

```python
class Cat: # Клас "Кішка"
    fur_color = None # Атрибут "колір шерсті"
    def voice(self): # Метод "голос"
        print("Meow")
maple = Cat() # Створення об'єкта кішки Мейпл
maple.fur_color = "tortie" # Встановлення черепахового кольору шерсті
maple.voice() # Зробити "няв" :3
```
У Пайтоні методи, які мають вигляд `__назва__` називають "магічними методами". Замість конструкторів тут використовують магічний метод `__init__(self, <...>)`. Він може бути тільки один, тому варто визначати значення аргументів за замовчуванням, якщо потрібно "декілька конструкторів". Також не забувайте, що першим параметром має бути `self`.

Ще одним магічним методом є `__str__(self)` — визначення репрезентації об'єкта у вигляді рядка. Відповідає методу `toString()` у Java.

```python
class Cat:
    #...
    def __init__(self, color):
        self.fur_color = color
    def __str__(self):
        return f"A {self.fur_color} cat."
#...
marble = Cat("calico")
print(marble) # "A calico cat."
```
Не дуже часто потрібним методом є _деструктор_ `__del__(self)`. Він викликається автоматично одразу перед тим, як об'єкт буде видаленим (вручну або через невикористання).

### Модифікатори доступу?

На відміну від інших мов, ООП у Python працює дещо інакше. Модифікаторів тут немає, але є інші способи вказати область доступу атрибутів і методів (членів класу). _Приватні_ члени класу просто мають вигляд `__назва`. _Захищених_ взагалі немає, але за звичаєм їх позначають `_назва`.

```python
class Cat:
    __brain = 0
    _tag = ""
    def get_tag(self):
        return _tag
```

## Успадкування

Для створення дочірнього класу треба після його назви вказати у дужках назву батьківського класу: `class <назва_дочірнього>(<назва_батьківського>)`. Наслідуються усі члени класу, _навіть конструктор_, окрім приватних.

Конструктор або будь-які методи дочірнього класу можна викликати звичайним способом за назвою класу або викликати `super()`, що приблизно поверне посилання на батьківський клас. У конструкторі дочірнього класу _завжди варто_ викликати конструктор батьківського, адже Python не викликає його автоматично. Чому?

У цій мові ООП працює дещо по-іншому, ніж в інших мовах програмування. Класи є не просто "шаблонами об'єктів", а вони є частково "прототипами", а точніше, ["способом групування даних і функціоналу в одному місці"](https://docs.python.org/3/tutorial/classes.html). Тому створення дочірнього класу просто копіює члени батьківського класу, але не зв'язує його з ним.

```python
class Black_cat(Cat):
    def __init__(self):
        super().__init__("black")
```
_Множинне успадкування_ у Python дозволене — можна прописати кілька батьківських класів через кому.

## Поліморфізм

Поліморфізм у Python здебільшого досягається _перевизначенням_, або заміщенням методів. Як динамічна мова, він не буде попереджувати про можливість помилок якщо класи не мають спільного суперкласу, тому в цілому, можливо навіть створювати незалежні класи з однаковими методами і використовувати немовби вони перевизначені, хоча насправді такими не є.

```python
class Animal:
    def voice(self):
        pass
class Cat(Animal):
    def voice(self):
        print("Няв!")
class Dog(Animal):
    def voice(self):
        print("Гав!")
dog = Dog()
dog.voice()
cat = Cat()
cat.voice()
```

## Де статичні члени, абстракні класи, інтерфейси?

Як динамічна мова програмування, Python просто не потребує використання інтерфейсів та абстрактних класів, тому у звичній Вам "справжній формі" їх немає.

Будь-які атрибути, а також методи, які власне не мають першого параметра `self`, можна вважати "статичними". Доступ до них забезпечуватиметься як з об'єктів, так і з імені самого класу. Якщо змінити атрибут через об'єкт, то він буде змінений лише у цьому об'єкті, а якщо через клас — для класу. У Пайтоні класи також є об'єктами — ця ширша концепція називається _метакласами_, але для основ мови це не буде розглянуто у даному курсі.

```python
class Cat:
    number_of_cats = 0
    def __init__(self):
        Cat.number_of_cats += 1
    def how_many_cats():
        print(Cat.number_of_cats)
Cat.how_many_cats() # 0
cat1 = Cat()
cat2 = Cat()
Cat.how_many_cats() # 2
```
До того ж, унікальною особливістю мови, що дозволяє змінювати властивості методів та атрибутів, наприклад, вказувати явно статичні методи, є _декоратори_. Це не менш велика тема, яку я вважаю не зовсім базовою, тому рекомендую почитати про них у додаковому джерелі: [Python: Decorators in OOP](https://towardsdatascience.com/python-decorators-in-oop-3189c526ead6).
