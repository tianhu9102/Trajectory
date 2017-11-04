#include "IhmFourWidget.h"

IhmFourWidget::IhmFourWidget(QWidget *parent) : QWidget(parent)
{
    setAttribute(Qt::WA_DeleteOnClose);

    for(int i=0;i<4;i++){
        qWidget[i] = new IhmVTKWidget;
    }
    gridLayout = new QGridLayout(this);
    gridLayout->addWidget(qWidget[0],0,0);
    gridLayout->addWidget(qWidget[1],0,1);
    gridLayout->addWidget(qWidget[2],1,0);
    gridLayout->addWidget(qWidget[3],1,1);
    gridLayout->setSpacing(1);
    gridLayout->setMargin(0);

    QPalette palette;
    palette.setColor(QPalette::Background,QColor(192,253,123));
    this->setAutoFillBackground(true);
    this->setPalette(palette);
}

IhmVTKWidget* IhmFourWidget::getWidget(int i){
    return this->qWidget[i];
}
