#include <iostream>
typedef unsigned long long ull;

ull factorial(unsigned int n) {
    if (n < 2)
        return 1;
    return n * factorial(n - 1);
}

int main() {
    int num;
    std::cout << "Введіть невідʼємне число: ";
    std::cin >> num;

    if (num < 0) {
        std::cout << "Число має бути невідʼємним." << std::endl;
    } else {
        ull result = factorial(num);
        std::cout << "Факторіал числа " << num << ": " << result << std::endl;
    }
    
    return 0;
}