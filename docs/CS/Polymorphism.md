---
layout: default
title: ООП II
nav_order: 8
parent: Інформатика (Java)
description: "Курс Java на уроках Інформатики."
has_toc: false
---

# Об'єктно-орієнтоване програмування (продовження)

Одразу перейдемо до теми.

## Успадкування

У вступі до ООП розповідалось про те, що клас може _наслідувати_ інший, розширюючи його. Такі _підкласи_ мають окрім полей і методів _суперкласу_, ще й свої власні. Для наслідування необхідно після назви класу вказати слово `extends` і назву батьківського класу:

```java
class A {
	protected int a;
}
class B extends A {
	protected int b;
}
```
У класі "А" є лише поле `a`, а у класі "B" є поля `a` і `b`. _Зверніть увагу:_ для того щоб дані класу передались дочірньому, вони повинні бути хоча б захищеними — `protected` (або відкритими — `public`), як видно з таблиці у першій частині ООП!

#### super

Якщо підклас _перезаписує_ метод або перевизначає поле, то може бути потреба звернутись до методу (поля) суперкласу — для цього є слово `super`. Воно є схожим на `this`, тому використовується майже так само:

```java
class A {
	public void print(){
		System.out.println("Метод суперкласу");
	}
} class B extends A {
	@Override
	public void print(){
		super.print();
		System.out.println("Метод підкласу");
	}
}
```
```java
B obj = new B();
obj.print(); //Виведе "Метод суперкласу" і "Метод підкласу"
```
`@Override` — це _анотація_, що вказує Java, що ми дійсно бажаємо перезаписати метод `print()`, а не просто помилилися, вказавши таку ж назву. Звичайно, можна використовувати `super()` як функцію для виклику конструктора суперкласу.

## Абстрагування

### Абстракція

Буває й таке, що декілька класів мають спільні риси, тому для спрощення роботи з цими класами існує _абстрагування_ — відокремлення деяких значень класів з метою приведення до загального вигляду. Наприклад, є класи "Трикутник" і "Квадрат". Обидва мають площу та периметр, адже вони є фігурами. Для цього у Java існують _абстрактні класи й методи_. Абстракція значить, що у загальному вигляді методи нічого не роблять, а кожен клас, який наслідує абстрактний клас, має свою _імплементацію_ цього методу. Створити об'єкт абстрактного класу неможливо. Абстрактним є клас, що має хоч один абстрактний метод, але не обов'язково всі. Нехай буде абстрактний клас (визначається словом `abstract`) з масивом довжини сторін та методами обчислення площі й периметра. Периметр обчислюється однаково, лише додаванням довжини всіх сторін, а от площа по-різному, тому метод обчислення площі буде абстрактним:

```java
abstract class Shape {
	public int[] sides;
	public Shape(int[] sides) {
		this.sides = sides;
	}
	public abstract float calcArea();
	public int calcPerimeter() {
		int p = 0;
		for(int side : sides) p += side;
		return p;
	}
}
```
Квадрат, який наслідує Фігуру, має перезаписати абстрактний метод знаходження площі, а всі інші методи та поля автоматично передадуться йому:

```java
class Square {
	public float calcArea() {
		return Math.pow(this.sides[0], 2);
	}
}
```
То ж, тепер можна створити новий квадрат і обчислити його периметр і площу:

```java
Square sq = new Square(new int[]{2, 2, 2, 2});
System.out.println( sq.calcArea() );
```

#### VarArgs

Здається, не дуже зручно писати масиви в аргументах. Ця проблема, на щастя, має просте рішення! Якщо аргументів одного типу може бути 0 або більше (невизначена кількість), то можна використати _список аргументів змінної довжини_, або простіше _varargs_ — `...` після типу. Таким чином, конструктор Фігури можна замінити на такий:

```java
public Shape(int... sides) {
	this.sides = sides;
}
```
А у виклику конструктора достатньо вказати аргументи через кому:

```java
Square sq = new Square(2, 2, 2, 2);
```

### Інтерфейси

Java не підтримує _множинне успадкування_ — не можна успадкувати декілька класів, тому що інакше могла б виникнути ["ромбовидна проблема" (додатковий матеріал)](https://en.wikipedia.org/wiki/Multiple_inheritance#The_diamond_problem), коли виникає необднозначність, якщо декілька батьківських класів мають метод або поле з однаковим ім'ям. Однак, замість цього є _інтерфейси_, вони виконують роль повністю абстрактних класів (таких, що мають лише абстрактні методи). У них не має сенсу писати біля кожного метода `abstract`, а достатньо створити інтерфейс словом `interface`:

```java
interface Animal {
	public void voice();
	public void move();
}
```
Замість `extends` (розширення класу), інтерфейси `implements` (імплементують). Все інше просто, як і з абстрактними класами, потрібно перезаписати всі методи інтерфейсу. Як сказано вище, класи можуть імплементувати більше одного інтерфейсу:

```java
class Cat implements Animal {
	public void voice(){
		System.out.println("Няв");
	}
	public void move(){
		System.out.println("Кіт іде.");
	}
}
```

## Поліморфізм

У розділі про абстракцію були фігури, які наслідують один клас. Цікаво, що можна створити об'єкт конкретний об'єкт, але з типом загального:

```java
Shape a = new Square(3, 3, 3, 3);
Shape b = new Triangle(5, 5, 5);
```
Таке явище й називають _поліморфізмом_ — одна Фігура має кілька форм: Квадрат і Трикутник. Знаючи, що вони всі фігури (`Shape`), можна робити з ними спільні дії, наприклад _різні_ фігури помістити в один масив:

```java
Shape[] arr = {a, b};
```

### Приведення типів

_Приведенням типів_ (кастингом) називають приведення об'єкта до іншого типу, і робиться це вказуванням типу у дужках перед об'єктом:

```java
Square sq = new Square(6, 6, 6, 6);
Shape sh = (Shape)sq;
```
Приведення до дочірнього типу називається _понижувальним приведенням_ (downcasting), а до батьківського навпаки, _повищувальним приведенням_ (upcasting).

### final

У Java відсутнє поняття чистих "констант", але є слово `final`. Клас, який позначений _фінальним_, не може мати підкласів (його не можна наслідувати); фінальні методи не можна перезаписувати; а фінальні змінні є практично _константами_, тобто їх не можна змінювати. За [конвенцією імен](https://en.wikipedia.org/wiki/Naming_convention_(programming)) константи у Java називають ВЕЛИКИМИ_ЛІТЕРАМИ:

```java
final class Uninheritable {
	public static final float CHYSLO_PI = 3.1415926;
	public final example(){}
}
```

