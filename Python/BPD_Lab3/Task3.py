from argparse import ArgumentParser

parser = ArgumentParser(description="Визначення кількості взаємно простих до n чисел.")
parser.add_argument("n", type=int, help="Натуральне число")
args = parser.parse_args()

def count_coprimes(n):
    result = n
    p = 2
    while p * p <= n:
        if n % p == 0:
            while n % p == 0:
                n //= p
            result -= result // p
        p += 1
    if n > 1:
        result -= result // n
    return result

print(f"φ({args.n}) = {count_coprimes(args.n)}")
