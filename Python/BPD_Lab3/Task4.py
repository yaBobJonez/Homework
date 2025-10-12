from argparse import ArgumentParser
from math import gcd

parser = ArgumentParser(description="Знаходження зворотного числа у мультиплікативній групі.")
parser.add_argument("a", type=int, help="Натуральне число")
parser.add_argument("p", type=int, help="Модуль")
args = parser.parse_args()

def φ(n):
    count = 0
    for i in range(1, n + 1):
        if gcd(i, n) == 1:
            count += 1
    return count

a = args.a
p = args.p

if gcd(a, p) == 1:
    b = a ** (φ(p) - 1) % p
    print(f"Зворотне число до {a} за модулем {p} = {b}")
else:
    print(f"Зворотне число до {a} за модулем {p} не існує.")
