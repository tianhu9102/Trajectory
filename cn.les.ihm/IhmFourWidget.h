#ifndef IHMFOURWIDGET_H
#define IHMFOURWIDGET_H

#include <QWidget>
#include "IhmVTKWidget.h"

class IhmFourWidget : public QWidget
{
    Q_OBJECT
public:
    explicit IhmFourWidget(QWidget *parent = 0);

    IhmVTKWidget* getWidget(int i);

private:
      IhmVTKWidget *qWidget[4];

      QGridLayout* gridLayout;

signals:

public slots:
};

#endif // IHMFOURWIDGET_H
