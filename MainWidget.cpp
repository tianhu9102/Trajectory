#include "MainWidget.h"

MainWidget::MainWidget(QWidget *parent)
    : QWidget(parent)
{
   this->resize(800,500);
   this->setWindowTitle("航迹分析");
   this->setWindowIcon(QIcon(":/images/logo.png"));


    //testVtk.test(); //测试vtk库OK (2017-9-26)
    this->testOracle();

    QString path1="D:/data/kghj_flight/history1/";
    this->testTrajectory(path1);
}

MainWidget::~MainWidget()
{

}

/**
 * @brief MainWidget::testTrajectory
 * 功能：测试航迹数据，三维可视化显示
 *
 */
void MainWidget::testTrajectory(QString path){

    QString dir(path);
    FunListDir* fListDir= new FunListDir();
    fListDir->readDir(dir);
    qDebug()<<fListDir->getSize();

    vtkRenderer *render = vtkRenderer::New();

    QString color[10]={"1","2","3","4","5","6","7","8","9","0"};
    for(int tmp=0;tmp<fListDir->getSize();tmp++){
        VisualizationTra vTra;
        vTra.readFile(fListDir->getFileName(tmp));
        int ti = tmp%10;

        vTra.showPoints(render,color[ti]);
        qDebug()<<fListDir->getFileName(tmp);
    }

    vtkRenderWindow* renderwindow = vtkRenderWindow::New();
    renderwindow->AddRenderer(render);
    renderwindow->SetSize(800,450);

    vtkRenderWindowInteractor* renderWindowInteractor = vtkRenderWindowInteractor::New();
    renderWindowInteractor->SetRenderWindow(renderwindow);

    vtkSmartPointer<vtkAxesActor> axes =
      vtkSmartPointer<vtkAxesActor>::New();
    axes->SetXAxisLabelText("Longitude");
    axes->SetYAxisLabelText("Latitude");
    axes->SetZAxisLabelText("Height");

    vtkSmartPointer<vtkOrientationMarkerWidget> widget =
      vtkSmartPointer<vtkOrientationMarkerWidget>::New();
    widget->SetOutlineColor( 0.9300, 0.5700, 0.1300 );
    widget->SetOrientationMarker( axes );
    widget->SetInteractor( renderWindowInteractor );
    widget->SetViewport( 0.0, 0.0, 0.4, 0.4 );
    widget->SetEnabled( 1 );
    widget->InteractiveOn();

    render->ResetCamera();
    renderwindow->Render();
    renderWindowInteractor->Start();
}

/**
 * @brief MainWidget::testMysql
 * 功能：测试连接oracle数据库
 *
 * http://www.cnblogs.com/majianming/p/5925105.html
 * http://blog.csdn.net/u014491932/article/details/71074620
 */
void MainWidget::testOracle(){
    qDebug()<<"Available drivers:";
    QStringList drivers=QSqlDatabase::drivers();
    foreach(QString driver,drivers)
    qDebug()<<driver;

    //连接数据库
    QSqlDatabase db = QSqlDatabase::addDatabase("QOCI");
    db.setHostName("127.0.0.1"); //127.0.0.1   192.168.24.27 kghj kghj
    db.setUserName("scott");
    db.setPassword("tiger");
    db.setDatabaseName("orcl");
    db.setPort(1521);

    qDebug()<<"hostname"<<db.hostName()<<db.userName()<<db.password()<<db.databaseName();

    if (db.open()) {
        std::cout<< " Oracle test ok!"<<std::endl;
        //查询语句
        //QSqlQuery query(" SELECT * FROM emp ");
        QSqlQuery query(" SELECT longitude FROM radar where acid like 'AAL137%' order by timestamped ");

        while (query.next())
        {
            double t1 = query.value(0).toDouble();
            //std::cout<<t1<<std::endl;
        }
    }else{
        std::cout<< "no"<<std::endl;
        return;
    }
}
