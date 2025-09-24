#!.venv/bin/python3

import argparse
import textwrap
import sys
from bitarray import bitarray
from codec import HammingCodec

parser = argparse.ArgumentParser(description="Кодек текстових даних за алгоритмом Геммінга")
parser.add_argument("mode",
                    choices=["e", "encode", "d", "decode"],
                    help="Режим роботи програми — кодування чи декодування")
parser.add_argument('-b', '--block-size',
                    type=int, default=1024,
                    help="Довжина блоку у бітах")
parser.add_argument("input_file", nargs="?",
                    type=argparse.FileType("r"), default=sys.stdin,
                    help="Вхідний файл (типово stdin)")
parser.add_argument("-o", "--output", dest="output_file",
                    type=argparse.FileType("w"), default=sys.stdout,
                    help="Вихідний файл (типово stdout)")
args = parser.parse_args()

try:
    with args.input_file as infile:
        input_data = infile.read()
        if args.mode in ("e", "encode"):
            bs = args.block_size // 8
            input_data = [input_data[i:i+bs] for i in range(0, len(input_data), bs)]
        else:
            input_data = input_data.splitlines()
except FileNotFoundError:
    print(f"Помилка: вхідний файл {args.input_file.name} не знайдено")
    sys.exit(10)
except PermissionError:
    print(f"Помилка: відсутній дозвіл на читання файлу {args.input_file.name}")
    sys.exit(11)
except IOError as e:
    print(f"Помилка: сталася помилка введення-виведення під час читання файлу: {e}")
    sys.exit(12)
except Exception as e:
    print(f"Помилка: сталася непередбачена помилка: {e}")
    sys.exit(19)

if args.mode in ("e", "encode"):
    output_data = []
    for block in input_data:
        ba = HammingCodec.encode(block)
        output_data.append(ba.to01())
    output_data = '\n'.join(output_data)
else:
    output_data = []
    for block in input_data:
        ba = bitarray(block)
        output_data.append(HammingCodec.decode(ba))
    output_data = ''.join(output_data)

try:
    with args.output_file as outfile:
        outfile.write(output_data + '\n')
except PermissionError:
    print(f"Помилка: відсутній дозвіл на запис до файлу {args.output_file.name}")
    sys.exit(21)
except IOError as e:
    print(f"Помилка: сталася помилка введення-виведення під час запису до файлу: {e}")
    sys.exit(22)
except Exception as e:
    print(f"Помилка: сталася непередбачена помилка: {e}")
    sys.exit(29)
