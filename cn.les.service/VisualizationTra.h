#ifndef VISUALIZATIONTRA_H
#define VISUALIZATIONTRA_H

#include "QString"
#include "QFile"
#include "Track.h"
#include <qdebug.h>

#include <vtkPoints.h>
#include <vtkPolyVertex.h>
#include <vtkUnstructuredGrid.h>
#include <vtkDataSetMapper.h>
#include <vtkActor.h>
#include <vtkProperty.h>
#include <vtkRenderer.h>
#include <vtkRenderWindow.h>
#include <vtkRenderWindowInteractor.h>
#include <QVector>


class VisualizationTra
{
public:
    VisualizationTra();
    void readFile(QString filepath);
    void showPoints(vtkRenderer *render, QString color);
    vtkUnstructuredGrid *getGrid();

private:
    QVector<Track*> sourceTrajectoryPoints;

    vtkPoints *points;
    vtkPolyVertex *poly;
    vtkUnstructuredGrid *grid;
    vtkUnstructuredGrid *grid1;
    vtkDataSetMapper *mapper;
    vtkActor *actor;

};

#endif // VISUALIZATIONTRA_H
