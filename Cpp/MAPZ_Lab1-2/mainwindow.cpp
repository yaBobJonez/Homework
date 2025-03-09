#include "mainwindow.h"
#include "ui_mainwindow.h"
#include <cmath>
#include <QMessageBox>

MainWindow::MainWindow(QWidget *parent)
    : QMainWindow(parent)
    , ui(new Ui::MainWindow)
{
    ui->setupUi(this);
}

MainWindow::~MainWindow()
{
    delete ui;
}

/*
 * Функція обчислення норми Мангеттена:
 * std::fabs — абсолютне значення
 * цей вираз сумує абсолютні значення n
 */
template <typename... Args>
double taxicab(Args ...n)
{
    return (std::fabs(n) + ...);
}

/*
 * Функція обчислення норми Евкліда:
 * std::sqrt — квадратний корінь
 * цей вираз сумує квадрати n, тобто n * n
 */
template <typename... Args>
double euclidean(Args... n)
{
    return std::sqrt( ((n * n) + ...) );
}

/*
 * Функція обчислення норми Чебишева:
 * std::fabs — абсолютне значення
 * std::max — максимальне значення, перевантажена приймати список ініціалізації
 * цей вираз знаходить максимальне серед абсолютних значень n
 */
template <typename... Args>
double chebyshev(Args... n)
{
    return std::max({std::fabs(n)...});
}

// Функція для приведення числа у рядок з точністю до 10 значущих знаків
auto to_string(double value)
{
    return QString::number(value, 'g', 10);
}

// Слот (обробник події) натискання на кнопку "Обчислити"
void MainWindow::on_calculate_btn_clicked()
{
    // Отримуємо значення введених координат векторів
    // v1 = [x1; y1; z1]
    double x1 = ui->x1->value();
    double y1 = ui->y1->value();
    double z1 = ui->z1->value();
    // v2 = [x2; y2; z2]
    double x2 = ui->x2->value();
    double y2 = ui->y2->value();
    double z2 = ui->z2->value();
    // Обчислимо вектор-різницю vd = [xd; yd; zd] = [x1 - x2; y1 - y2; z1 - z2]
    double xd = x1 - x2;
    double yd = y1 - y2;
    double zd = z1 - z2;

    // Використаємо визначені раніше функції для обчислення значень норм на трьох значеннях
    // координат векторів та перетворимо кожен у рядок
    QString taxicab1    = to_string(taxicab(x1, y1, z1));
    QString taxicab2    = to_string(taxicab(x2, y2, z2));
    QString taxicabDist = to_string(taxicab(xd, yd, zd));
    QString euclidean1    = to_string(euclidean(x1, y1, z1));
    QString euclidean2    = to_string(euclidean(x2, y2, z2));
    QString euclideanDist = to_string(euclidean(xd, yd, zd));
    QString chebyshev1    = to_string(chebyshev(x1, y1, z1));
    QString chebyshev2    = to_string(chebyshev(x2, y2, z2));
    QString chebyshevDist = to_string(chebyshev(xd, yd, zd));

    // Встановимо обчислені значення як текст для відповідних текстових полів результату
    ui->taxicab1->setText(taxicab1);
    ui->taxicab2->setText(taxicab2);
    ui->taxicabDist->setText(taxicabDist);
    ui->euclidean1->setText(euclidean1);
    ui->euclidean2->setText(euclidean2);
    ui->euclideanDist->setText(euclideanDist);
    ui->chebyshev1->setText(chebyshev1);
    ui->chebyshev2->setText(chebyshev2);
    ui->chebyshevDist->setText(chebyshevDist);
}

// Слот (обробник події) натискання на кнопку "Очистити"
void MainWindow::on_clear_btn_clicked()
{
    // Встановлюємо значення координат векторів на 0.0
    ui->x1->setValue(0.0);
    ui->y1->setValue(0.0);
    ui->z1->setValue(0.0);
    ui->x2->setValue(0.0);
    ui->y2->setValue(0.0);
    ui->z2->setValue(0.0);

    // Очищуємо текст відповідних текстових полів результатів
    ui->taxicab1->clear();
    ui->taxicab2->clear();
    ui->taxicabDist->clear();
    ui->euclidean1->clear();
    ui->euclidean2->clear();
    ui->euclideanDist->clear();
    ui->chebyshev1->clear();
    ui->chebyshev2->clear();
    ui->chebyshevDist->clear();
}


void MainWindow::on_result_btn_clicked()
{
    ui->calculate_btn->click();
    QMessageBox dlg(this);
    dlg.setWindowTitle("Результати");
    dlg.setText(
        QString("Розміри v1 за\n")
        .append("мангеттенською: ").append(ui->taxicab1->text()).append("\n")
        .append("евклідовою: ").append(ui->euclidean1->text()).append("\n")
        .append("чебишева: ").append(ui->chebyshev1->text())
    );
    dlg.exec();
}

