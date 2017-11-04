#include "IhmVTKWidget.h"

IhmVTKWidget::IhmVTKWidget(QWidget *parent) : QWidget(parent)
{
    setAttribute(Qt::WA_DeleteOnClose);

    qWidget = new QVTKWidget;
    render = vtkRenderer::New();
    render->SetBackground(0.4,0.5,0.6);
    renWin = vtkRenderWindow::New();
    renWin->AddRenderer(render);
    qWidget->SetRenderWindow(renWin);
    qWidget->update();

    QHBoxLayout* layout = new QHBoxLayout(this);
    layout->addWidget(qWidget);
    layout->setSpacing(0);
    layout->setMargin(0);
 }

QVTKWidget *IhmVTKWidget::getVTKWidget(){
    return this->qWidget;
}

vtkRenderer* IhmVTKWidget::getRender(){
    return this->render;
}

vtkRenderWindow* IhmVTKWidget::getRenWin(){
  return this->renWin;
}
