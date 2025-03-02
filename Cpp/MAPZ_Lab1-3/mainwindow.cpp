#include <cmath>
#include "mainwindow.h"
#include "ui_mainwindow.h"

MainWindow::MainWindow(QWidget *parent)
    : QMainWindow(parent)
    , ui(new Ui::MainWindow)
{
    ui->setupUi(this);
    // Після створення інтерфейсу вікна програми створюємо та встановлюємо моделі для списків
    v1Model = new VectorListModel(this);
    v2Model = new VectorListModel(this);
    ui->vector1List->setModel(v1Model);
    ui->vector2List->setModel(v2Model);
}

MainWindow::~MainWindow()
{
    delete ui;
}

void MainWindow::on_dimensionsSpin_valueChanged(int value)
{
    // При зміні значення розмірності відповідно оновлюємо моделі списків
    v1Model->setRowCount(value);
    v2Model->setRowCount(value);
}

// Функція обчислення скалярного добутку двох векторів у циклі
double dotProduct(const QList<double> &x, const QList<double> &y)
{
    if (x.count() != y.count()) return 0.0;
    double res = 0.0;
    for (qsizetype i = 0; i < x.count(); ++i)
        res += x[i] * y[i];
    return res;
}

// Функція обчислення нового вектора як різниці двох векторів у циклі
QList<double> difference(const QList<double> &x, const QList<double> &y)
{
    if (x.count() != y.count()) return QList<double>();
    QList<double> res(x.count());
    for (qsizetype i = 0; i < x.size(); ++i)
        res[i] = x[i] - y[i];
    return res;
}

// Подія натискання на кнопку «Обчислити»
void MainWindow::on_calculateBtn_clicked()
{
    // Отримуємо з моделі дані введених елементів векторів v1, v2
    QList<double>& v1 = v1Model->getVector();
    QList<double>& v2 = v2Model->getVector();
    // Обчислюємо різницю цих векторів (для відстані між ними)
    QList<double> vd = difference(v1, v2);

    /*
     * v<n>Norm — розмір n-го вектора (його норма)
     * distance — відстань між векторами
     * angleRad — кут між ними у радіанах,
     * angleDeg — " у градусах
     *
     * std::sqrt — квадратний корінь
     * std::acos — arccos (арккосинус)
     *
     * Для переведення у градуси радіани множаться на 180/π
     */
    double v1Norm = std::sqrt(dotProduct(v1, v1));
    double v2Norm = std::sqrt(dotProduct(v2, v2));
    double distance = std::sqrt(dotProduct(vd, vd));
    double angleRad = std::acos( dotProduct(v1, v2) / (v1Norm * v2Norm) );
    double angleDeg = angleRad * (180 / M_PI);

    // Встановлюємо у текстові поля результати обчислень з точністю до 10 значущих цифр
    ui->v1NormEdit->setText(QString::number(v1Norm, 'g', 10));
    ui->v2NormEdit->setText(QString::number(v2Norm, 'g', 10));
    ui->distanceEdit->setText(QString::number(distance, 'g', 10));
    ui->angleEdit->setText(QString::number(angleDeg, 'g', 10).append("°"));
}

// Подія натискання на кнопку «Очистити»
void MainWindow::on_clearBtn_clicked()
{
    // Використовуємо задані у моделі функції обнулення векторів
    v1Model->resetVector();
    v2Model->resetVector();

    // Очищуємо всі текстові поля для результатів
    ui->v1NormEdit->clear();
    ui->v2NormEdit->clear();
    ui->distanceEdit->clear();
    ui->angleEdit->clear();
}

