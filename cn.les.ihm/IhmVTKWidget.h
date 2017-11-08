#ifndef IHMVTKWIDGET_H
#define IHMVTKWIDGET_H

#include <QWidget>
#include <QVTKWidget.h>
#include <vtkRenderer.h>
#include <vtkRenderWindow.h>
#include <QHBoxLayout>
#include <vtkRenderWindowInteractor.h>
#include <vtkEarthSource.h>
#include <vtkPolyDataMapper.h>
#include <vtkCursor3D.h>
#include <vtkCubeAxesActor.h>
#include <vtkTextProperty.h>
#include <vtkOutlineFilter.h>
#include <vtkGeometryFilter.h>
#include <vtkCamera.h>



class IhmVTKWidget : public QWidget
{
    Q_OBJECT
public:
    explicit IhmVTKWidget(QWidget *parent = 0);

    QVTKWidget *getVTKWidget();
    vtkRenderer* getRender();
    vtkRenderWindow* getRenWin();

private:
    QVTKWidget *qWidget;
    vtkRenderer* render;
    vtkRenderWindow* renWin ;

signals:

public slots:
};

#endif // IHMVTKWIDGET_H
