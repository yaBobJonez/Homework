#ifndef VECTORLISTMODEL_H
#define VECTORLISTMODEL_H

#include <QAbstractListModel>

/*
 * Модель для вектора:
 * визначає тип даних (double), спосіб їх зберігання (QList) та основні можливості для
 * взаємодії зі списком елементів (зокрема, редагування).
 * Реалізує MVC (Model-View-Controler) підхід до розробки ПЗ.
 */
class VectorListModel : public QAbstractListModel
{
    Q_OBJECT

public:
    explicit VectorListModel(QObject *parent = nullptr);

    // Базовий функціонал: отримання розміру та даних комірок
    int rowCount(const QModelIndex &parent = QModelIndex()) const override;

    QVariant data(const QModelIndex &index, int role = Qt::DisplayRole) const override;

    // Можливості редагування: встановлення даних комірок
    bool setData(const QModelIndex &index, const QVariant &value, int role = Qt::EditRole) override;

    Qt::ItemFlags flags(const QModelIndex &index) const override;

    // Зміна розмірності вектора
    void setRowCount(int count);

    // Обнулення вектора
    void resetVector();

    // Отримання всіх елементів вектора
    QList<double>& getVector() { return vector; }

private:
    // Список, що власне зберігає дані елементів
    QList<double> vector;
};

#endif // VECTORLISTMODEL_H
