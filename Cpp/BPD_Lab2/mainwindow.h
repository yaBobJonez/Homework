#ifndef MAINWINDOW_H
#define MAINWINDOW_H

#include <QMainWindow>

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
    void on_clearBtn_clicked();

    void on_aPlusB_clicked();

    void on_mSpin_valueChanged(int arg1);

    void on_aSpin_valueChanged(int arg1);

    void on_bSpin_valueChanged(int arg1);

    void on_addRevA_clicked();

    void on_aMinusB_clicked();

    void on_aMultB_clicked();

    void on_aPowB_clicked();

    void on_multRevA_clicked();

    void on_aDivB_clicked();

private:
    Ui::MainWindow *ui;

    int multRev(int n);

    int m = 2, a = 1, b = 1;
    void setC(int value);
};
#endif // MAINWINDOW_H
