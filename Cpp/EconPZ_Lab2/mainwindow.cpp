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

void MainWindow::on_resetBtn_clicked()
{
    ui->projectTypeCombo->setCurrentIndex(0);
    ui->projectSizeSpin->setValue(0.01);

    ui->effortLabel->setText("0");
    ui->durationLabel->setText("0");
    ui->staffingLabel->setText("0");
    ui->efficiencyLabel->setText("0");
}

void MainWindow::on_calculateBtn_clicked()
{
    double ai = 0.0, bi = 0.0, di = 0.0;
    double ci = 2.5;
    switch (ui->projectTypeCombo->currentIndex()) {
    case 0:
        ai = 2.4;
        bi = 1.05;
        di = 0.38;
        break;
    case 1:
        ai = 3.0;
        bi = 1.12;
        di = 0.35;
        break;
    case 2:
        ai = 3.6;
        bi = 1.2;
        di = 0.32;
        break;
    }
    const double size = ui->projectSizeSpin->value();

    const double pm = ai * std::pow(size, bi);
    const double tm = ci * std::pow(pm, di);
    const double ss = pm / tm;
    const double p = size / pm;

    ui->effortLabel->setText(QString::number(pm, 'f', 2));
    ui->durationLabel->setText(QString::number(tm, 'f', 2));
    ui->staffingLabel->setText(QString::number(std::ceil(ss)));
    ui->efficiencyLabel->setText(QString::number(p * 100.0, 'f', 1));
}
