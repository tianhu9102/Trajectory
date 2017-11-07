#include <vtkAutoInit.h>
#include "MainWidget.h"
#include <QApplication>
#include <vtkAutoInit.h>
#include "IhmMainWidget.h"
#include "StringImplement.h"


VTK_MODULE_INIT(vtkRenderingOpenGL2)
VTK_MODULE_INIT(vtkInteractionStyle)
VTK_MODULE_INIT(vtkRenderingFreeType)

int main(int argc, char *argv[])
{
    QApplication app(argc, argv);

    QString path = app.applicationFilePath();//项目可执行文件路径
    StringImplement sit;
    QString dataDir(sit.dataPath(path,"wai"));//项目数据所在位置

    IhmMainWidget* widget = new IhmMainWidget();
    widget->setDataPath(dataDir);//传递赋值
    widget->show();


    return app.exec();
}



