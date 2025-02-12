#include "mainwindow.h"
#include "ui_mainwindow.h"
#include <cmath>

// Конструктор класу вікна підвантажує розмітку інтерфейсу з файлу mainwindow.ui
// Метод згенерований автоматично у Qt Creator
MainWindow::MainWindow(QWidget *parent)
    : QMainWindow(parent)
    , ui(new Ui::MainWindow)
{
    ui->setupUi(this);
}

// Деструктор звільняє ресурси після закриття вікна
// Метод згенерований автоматично у Qt Creator
MainWindow::~MainWindow()
{
    delete ui;
}

// Слот натискання на кнопку «Обчислити»
void MainWindow::on_calculateBtn_clicked()
{
    // Отримуємо значення координат векторів з відповідних QDoubleSpinBox
    double x1 = ui->x1->value();
    double y1 = ui->y1->value();
    double z1 = ui->z1->value();
    double x2 = ui->x2->value();
    double y2 = ui->y2->value();
    double z2 = ui->z2->value();

    // Знаходимо різниці і записуємо в окремі змінні для зручності, адже вони потрібні в усіх формулах
    double xd = x1 - x2;
    double yd = y1 - y2;
    double zd = z1 - z2;

    /*
     * З використанням методів стандартної бібліотеки C++ обчислюємо формули:
     * sqrt — квадратний корінь;
     * fabs — модуль (абсолютне значення) для дійсних чисел;
     * fmax — максимальне з двох дійсних чисел
     */
    double euclideanDist = std::sqrt( xd*xd + yd*yd + zd*zd );
    double taxicabDist = std::fabs(xd) + std::fabs(yd) + std::fabs(zd);
    double chebyshevDist = std::fmax( std::fmax( std::fabs(xd), std::fabs(yd) ), std::fabs(zd) );

    // Встановлюємо результати формул до текстових полів, попередньо привівши до QString з точністю 6 знаків
    ui->euclideanRes->setText(QString::number(euclideanDist));
    ui->taxicabRes->setText(QString::number(taxicabDist));
    ui->chebyshevRes->setText(QString::number(chebyshevDist));
}

// Слот натискання на кнопку «Очистити»
void MainWindow::on_clearBtn_clicked()
{
    // Для кожного поля введення координат встановлюється значення 0.0
    ui->x1->setValue(0.0);
    ui->y1->setValue(0.0);
    ui->z1->setValue(0.0);
    ui->x2->setValue(0.0);
    ui->y2->setValue(0.0);
    ui->z2->setValue(0.0);

    // Текстові поля для результатів взагалі очищаються відповідним методом
    ui->euclideanRes->clear();
    ui->taxicabRes->clear();
    ui->chebyshevRes->clear();
}

