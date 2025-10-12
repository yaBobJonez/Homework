from argparse import ArgumentParser

parser = ArgumentParser(description="Обчислення НСД за алгоритмом Евкліда")
parser.add_argument("a", type=int, help="Перше натуральне число")
parser.add_argument("b", type=int, help="Друге натуральне число")
args = parser.parse_args()

def gcd(a, b):
    if a < b:
        a, b = b, a
    while b != 0:
        r = a % b
        a, b = b, r
    return a

print(f"НСД({args.a}, {args.b}) = {gcd(args.a, args.b)}")
