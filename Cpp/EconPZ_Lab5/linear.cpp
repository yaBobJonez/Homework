#include <iostream>
#include <cmath>

int main() {
    double a, b;
    std::cout << "Введіть сторони прямокутника: ";
    std::cin >> a >> b;

    double area = a * b;
    double perimeter = 2 * (a + b);
    double diagonal = std::sqrt(a * a + b * b);

    std::cout << "Периметр: " << perimeter << std::endl;
    std::cout << "Площа: " << area << std::endl;
    std::cout << "Діагональ: " << diagonal << std::endl;
    
    return 0;
}