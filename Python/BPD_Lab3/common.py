from argparse import ArgumentTypeError

def natural_number_type(x):
    x = int(x)
    if x < 1:
        raise ArgumentTypeError("Введене число не є натуральним.")
    return x
