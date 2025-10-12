from argparse import ArgumentParser
from common import natural_number_type


def check_for_primality(args):
    p = args.p

    if p < 2:
        print(f'Число {p} не є простим.')
        return
    for a in range(2, p-1):
        if a**(p-1) % p != 1:
            print(f'Число {p} не є простим.')
            return
    print(f'Число {p} є простим.')

def generate_prime(args):
    A = args.A

    primes = []
    for p in range(2, A+1):
        is_prime = True
        for a in range(2, p - 1):
            if a**(p - 1) % p != 1:
                is_prime = False
                break
        if is_prime:
            primes.append(p)
    
    if primes:
        print(f'Знайдені прості числа: {primes}')
    else:
        print(f'Не знайдено простих чисел p ≤ {A}.')


parser = ArgumentParser()
subparsers = parser.add_subparsers(dest='subcommand')

check_parser = subparsers.add_parser('check', help='Перевірити число на простоту малою теоремою Ферма')
check_parser.add_argument('p', type=natural_number_type, help='Число для перевірки')
check_parser.set_defaults(func=check_for_primality)

gen_parser = subparsers.add_parser('gen', help='Згенерувати просте число p ≤ A')
gen_parser.add_argument('A', type=natural_number_type, help='Верхня межа')
gen_parser.set_defaults(func=generate_prime)

args = parser.parse_args()
if args.subcommand:
    args.func(args)
else:
    parser.print_help()
