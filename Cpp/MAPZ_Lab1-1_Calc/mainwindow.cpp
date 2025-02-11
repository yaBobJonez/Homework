#include "mainwindow.h"
#include "ui_mainwindow.h"
#include <cmath>

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

void MainWindow::on_calculateBtn_clicked()
{
    double x1 = ui->x1->value();
    double y1 = ui->y1->value();
    double z1 = ui->z1->value();
    double x2 = ui->x2->value();
    double y2 = ui->y2->value();
    double z2 = ui->z2->value();

    double xd = x1 - x2;
    double yd = y1 - y2;
    double zd = z1 - z2;

    double euclideanDist = std::sqrt( xd*xd + yd*yd + zd*zd );
    double taxicabDist = std::fabs(xd) + std::fabs(yd) + std::fabs(zd);
    double chebyshevDist = std::fmax( std::fmax( std::fabs(xd), std::fabs(yd) ), std::fabs(zd) );

    ui->euclideanRes->setText(QString::number(euclideanDist));
    ui->taxicabRes->setText(QString::number(taxicabDist));
    ui->chebyshevRes->setText(QString::number(chebyshevDist));
}

void MainWindow::on_clearBtn_clicked()
{
    ui->x1->setValue(0.0);
    ui->y1->setValue(0.0);
    ui->z1->setValue(0.0);
    ui->x2->setValue(0.0);
    ui->y2->setValue(0.0);
    ui->z2->setValue(0.0);

    ui->euclideanRes->clear();
    ui->taxicabRes->clear();
    ui->chebyshevRes->clear();
}

