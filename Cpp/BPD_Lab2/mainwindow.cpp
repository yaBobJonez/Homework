#include "mainwindow.h"
#include "ui_mainwindow.h"

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

void MainWindow::on_clearBtn_clicked()
{
    ui->mSpin->setValue(1);
    ui->aSpin->setValue(1);
    ui->bSpin->setValue(1);
    setC(0);
}


void MainWindow::on_mSpin_valueChanged(int value)
{
    m = value;
    ui->aSpin->setMaximum(m - 1);
    ui->bSpin->setMaximum(m - 1);
}

void MainWindow::on_aSpin_valueChanged(int value)
{
    a = value;
}

void MainWindow::on_bSpin_valueChanged(int value)
{
    b = value;
}

void MainWindow::setC(int value)
{
    ui->cLabel->setText(QString::number(value));
}


int MainWindow::multRev(int n) {
    if (std::gcd(n, m) != 1)
        return -1;
    for (int x = 1; x < m; x++)
        if ((n * x) % m == 1)
            return x;
    return -1;
}

void MainWindow::on_aPlusB_clicked()
{
    setC( (a + b) % m );
}

void MainWindow::on_addRevA_clicked()
{
    setC( m - a );
}

void MainWindow::on_aMinusB_clicked()
{
    setC( (a + m - b) % m );
}

void MainWindow::on_aMultB_clicked()
{
    setC( (a * b) % m );
}

void MainWindow::on_aPowB_clicked()
{
    int prod = 1;
    for (int i = 0; i < b; i++)
        prod = (a * prod) % m;
    setC(prod);
}

void MainWindow::on_multRevA_clicked()
{
    int res = multRev(a);
    if (res == -1) ui->cLabel->setText("Не існує");
    else setC(res);
}

void MainWindow::on_aDivB_clicked()
{
    int bM1 = multRev(b);
    if (bM1 == -1) ui->cLabel->setText("Не існує");
    else setC( (a * bM1) % m );
}

