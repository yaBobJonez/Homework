#include "vectorlistmodel.h"

VectorListModel::VectorListModel(QObject *parent)
    : QAbstractListModel(parent)
{
    // При ініціалізації моделі додати один елемент, щоб вектор не був порожнім
    setRowCount(1);
}

int VectorListModel::rowCount(const QModelIndex &parent) const
{
    // Повертає розмір вектора, визначивши за внутрішнім списком
    if (parent.isValid())
        return 0;
    return vector.count();
}

QVariant VectorListModel::data(const QModelIndex &index, int role) const
{
    // Якщо індекс не існує, повертаємо порожній елемент даних
    if (!index.isValid() || index.row() >= rowCount())
        return QVariant();

    // Дані показуються лише при відображенні або редагуванні;
    // інші види запитів ігноруються
    if (role == Qt::DisplayRole || role == Qt::EditRole)
        return vector.at(index.row());

    return QVariant();
}

bool VectorListModel::setData(const QModelIndex &index, const QVariant &value, int role)
{
    // Модель має перебувати в режимі редагування, а індекс існувати
    if (!index.isValid() || role != Qt::EditRole)
        return false;

    // Операція встановлення значення відбувається лише, якщо воно відмінне від поточного
    if (data(index, role) != value) {
        // Записуємо значення елемента за індексом у внутрішній список
        vector[index.row()] = value.toDouble();
        // Надсилаємо сигнал про зміну даних комірки для оновлення UI
        emit dataChanged(index, index, {role});
        return true;
    }

    return false;
}

Qt::ItemFlags VectorListModel::flags(const QModelIndex &index) const
{
    if (!index.isValid())
        return Qt::ItemIsEnabled;

    // Вказуємо Qt, що ця модель підтримує редагування списку
    return QAbstractItemModel::flags(index) | Qt::ItemIsEditable;
}

void VectorListModel::setRowCount(int count)
{
    // Вектор має містити невідʼємну кількість елементів
    if (count < 0)
        return;

    // Отримуємо поточний розмір та визначаємо, слід додавати чи прибирати рядки
    int currentSize = vector.size();
    if (count > currentSize) {
        // Додаємо count - currentSize рядків та оновлюємо внутрішній список
        beginInsertRows(QModelIndex(), currentSize, count - 1);
        vector.resize(count);
        endInsertRows();
    } else if (count < currentSize) {
        // Видаляємо рядки та оновлюємо внутрішній список
        beginRemoveRows(QModelIndex(), count, currentSize - 1);
        vector.resize(count);
        endRemoveRows();
    }
}

void VectorListModel::resetVector()
{
    // Заповнюємо внутрішній список повністю нулями
    vector.fill(0.0);
    // Надсилаємо сигнал про зміну всіх даних для оновлення UI
    emit dataChanged(index(0), index(vector.count() - 1), {Qt::DisplayRole});
}

