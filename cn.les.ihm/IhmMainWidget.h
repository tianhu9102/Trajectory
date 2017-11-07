#ifndef IHMMAINWIDGET_H
#define IHMMAINWIDGET_H

#include <QObject>
#include <QWidget>
#include <QDebug>
#include <QIcon>
#include <QStyle>
#include <QHBoxLayout>
#include <QVBoxLayout>
#include <QTreeWidget>
#include <QTreeWidgetItem>
#include "IhmVTKWidget.h"
#include "IhmFourWidget.h"

#include <qfiledialog.h>
#include <QMessageBox.h>
#include <QAction>
#include <QMenu>
#include "VisualizationTra.h"


class IhmMainWidget : public QWidget
{
    Q_OBJECT
public:
    explicit IhmMainWidget(QWidget *parent = 0);
    void initParameters();
    void constructIhm();
    void conncects();
    void TraceDir(QString path);
    void setDataPath(QString path);

    vtkUnstructuredGrid *displayLines(vtkRenderer *render, QString filename, QString color);


private:
    IhmVTKWidget* vtkWidget0;
    IhmVTKWidget* vtkWidget1;

    QTreeWidget* treeWidget;
    QTreeWidgetItem* item0;

    QVector<QString> *fileList;//记录文件
    vtkRenderer* render;

    vtkRenderer* render0;

    QString dataDir;//项目数据路径



signals:

public slots:

    void showRightMenu();
    void addDir();

    void delIem(QTreeWidgetItem*,int);
    void checkSelf(QTreeWidgetItem*item, int column);


};

#endif // IHMMAINWIDGET_H
