#ifndef MAINWINDOW_H
#define MAINWINDOW_H

#include <QMainWindow>
#include "vectorlistmodel.h"

QT_BEGIN_NAMESPACE
namespace Ui {
class MainWindow;
}
QT_END_NAMESPACE

class MainWindow : public QMainWindow
{
    Q_OBJECT

public:
    MainWindow(QWidget *parent = nullptr);
    ~MainWindow();

private slots:
    void on_dimensionsSpin_valueChanged(int arg1);

    void on_calculateBtn_clicked();

    void on_clearBtn_clicked();

private:
    Ui::MainWindow *ui;

    VectorListModel *v1Model, *v2Model;
};
#endif // MAINWINDOW_H
