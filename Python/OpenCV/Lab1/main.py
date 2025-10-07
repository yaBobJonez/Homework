# Типи даних
def task1():
    a = 24
    b = 8.93
    c = 'something'
    d = False
    print([type(i) for i in [a, b, c, d]])

# Списки
def task2():
    arr = [7, 4.9, -16.5, 2e7, 0.00005]
    arr.insert(2, 0.00005)
    arr.remove(2e7)
    arr.sort()
    print(arr, arr.count(0.00005))

# Словники
def task3():
    json = {
        'name': 'Mykhailo',
        'age': 20,
        'city': 'Kyiv'
    }
    json['course'] = 4
    print(json.items())
    json.update({'age': -1})
    print(json)

# Кортежі
def task4():
    tup = ('ab', 'ra', 'ca', 'dab', 'ra')
    print(tup.count('dab'), tup.count('SA'))

# Множини
def task5():
    set1 = {'Into the Wild', 'Fire and Ice', 'Into the Wild', 'Forest of Secrets', 'Pet Sematary'}
    set2 = {'Pet Sematary', 'Rising Storm'}
    print(set1.union(set2))
    print(set1.intersection(set2))

# Умови
def task6():
    age = input('Введіть Ваш вік: ')
    try:
        age = int(age)
        if age < 18: print('Ти ще школяр')
        elif 18 <= age < 25: print('Ти студент')
        else: print('Ти дорослий')
    except:
        print('Вік же має бути числом, нє?')

# Цикли
def task7():
    for i in range(1, 11):
        print(i, end=' ')
    print()
    i = 10
    while i >= 1:
        print(i, end=' ')
        i -= 1
    print()
    for i in ['m', 'e', 'o', 'w']:
        print(i, end='')
    print()
    for k, v in {'type': 'car', 'model': 'Camaro'}.items():
        print(f'{k} → {v}')

# Функції
def task8():
    def square(x: int|float):
        return x * x
    def sum(list_: list[int|float]):
        from functools import reduce
        return reduce(lambda a, b: a + b, list_)
    def defParam(x = 'Bob'):
        print(f'Hello, {x}!')
    print(square(4))
    print(sum([2, 3, 4]))
    defParam('Mykola')
    defParam()

# Класи та обʼєкти
def task9():
    class Student:
        def __init__(self, name = 'Nobody', age = 0):
            self.name = name
            self.age = age
        def say_hello(self):
            print(f'Привіт, мене звати {self.name}')
    bob = Student('Bob', 17)
    bob.say_hello()

# Імпорт бібліотек
def task10():
    import math
    print(math.sqrt(2))
    import random as rng
    print(rng.randint(1, 6))
    from datetime import datetime as dt
    print(dt.now())

#-------------------------

from sys import argv, exit
if len(argv) < 2:
    print('Недостатньо аргументів: очікувався номер завдання')
    exit(1)
if not argv[1].isdigit():
    print('Некоректний аргумент: очікувався числовий номер завдання')
    exit(2)
n = argv[1]
if not (1 <= int(n) <= 10):
    print('Некоректний аргумент: доступні завдання — 1..10')
globals()["task" + n]()
