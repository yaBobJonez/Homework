#include <iostream>

int main() {
    int *x = new int;
    int *y = new int;
    std::cout << "Введіть два цілих числа: ";
    std::cin >> *x >> *y;

    std::cout << "До перестановки: x = " << *x << ", y = " << *y << std::endl;
    int temp = *x;
    *x = *y;
    *y = temp;
    std::cout << "Після перестановки: x = " << *x << ", y = " << *y << std::endl;
    
    delete x, y;
    return 0;
}