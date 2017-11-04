#include <vtkAutoInit.h>
#include "MainWidget.h"
#include <QApplication>
#include <vtkAutoInit.h>
#include "IhmMainWidget.h"


VTK_MODULE_INIT(vtkRenderingOpenGL2)
VTK_MODULE_INIT(vtkInteractionStyle)
VTK_MODULE_INIT(vtkRenderingFreeType)

int main(int argc, char *argv[])
{
    QApplication a(argc, argv);
    //MainWidget w;
   // w.show();

    IhmMainWidget* widget = new IhmMainWidget();
    widget->show();


    return a.exec();
}



