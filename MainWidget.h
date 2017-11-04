#ifndef MAINWIDGET_H
#define MAINWIDGET_H

#include <QWidget>
#include "Track.h"
#include "TestVTK.h"
#include "VisualizationTra.h"
#include "IhmFourWidget.h"
#include "FunListDir.h"
#include <vtkAxesActor.h>
#include <vtkOrientationMarkerWidget.h>
#include <vtkTextProperty.h>
#include <vtkTransform.h>

#include<QSqlDriver>
#include<QSqlDatabase>
#include<QDebug>
#include<QtSql>
#include <QSqlQuery>

class MainWidget : public QWidget
{
    Q_OBJECT

public:
    MainWidget(QWidget *parent = 0);
    ~MainWidget();

    void testOracle();
    void testTrajectory(QString path);

private:
    Track track;
    TestVTK testVtk;
};

#endif // MAINWIDGET_H
