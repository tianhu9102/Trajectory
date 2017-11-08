#include "IhmMainWidget.h"

//!--------------------------------------------------
//!
//! @brief IhmMainWidget::IhmMainWidget
//! @param parent
//!  2017-11-07  nanjing  success
//!
//!--------------------------------------------------
IhmMainWidget::IhmMainWidget(QWidget *parent) : QWidget(parent)
{
    this->initParameters();
    this->constructIhm();
    this->conncects();

}

void IhmMainWidget::initParameters(){
    QPalette p;//设置背景色
    p.setColor(QPalette::Base, QColor("#acb")); //ced9e6
    this->setPalette(p);

    this->setWindowTitle("航迹可视化与聚类分析");
    this->setWindowIcon(QIcon(":/images/flight.png"));  // images/flight.png

    //界面初始化
    treeWidget = new QTreeWidget();
    vtkWidget0 = new IhmVTKWidget();
    vtkWidget1 = new IhmVTKWidget();
    //vtkWidget0->resize(350,400);
    //vtkWidget1->resize(350,400);

    //数据路径初始值，用于在选取数据路径时打开对应目录
     this->dataDir = "D:/";
}

void IhmMainWidget::constructIhm(){
    //文件选取框
    QWidget* widgetL=new QWidget();
    QHBoxLayout* leftLayout = new QHBoxLayout(widgetL);

    treeWidget->setHeaderLabel("航迹数据目录");
    //treeWidget->setHeaderHidden(true);
    treeWidget->setContextMenuPolicy(Qt::CustomContextMenu);//右键 不可少否则右键无反应
    treeWidget->setColumnCount(1);
    treeWidget->columnWidth(50);
    //treeWidget->resize(100,400);
    leftLayout->addWidget(treeWidget);
    leftLayout->setSpacing(0);
    leftLayout->setMargin(0);

    //可视化界面
    QHBoxLayout* hLayout = new QHBoxLayout(this);
    hLayout->addWidget(widgetL);
    hLayout->addWidget(vtkWidget0);
    hLayout->addWidget(vtkWidget1);
    hLayout->setSpacing(1);
    hLayout->setMargin(0);
    this->resize(1200,500);
    this->update();
}

void IhmMainWidget::conncects(){
     QObject::connect(treeWidget,SIGNAL(customContextMenuRequested(QPoint)),this,SLOT(showRightMenu()));
     connect(treeWidget,SIGNAL(itemClicked(QTreeWidgetItem*,int)),this,SLOT(checkSelf(QTreeWidgetItem*,int)));
}

//!-------------------------------------------------------------------------------------
//! 根据复选框，在左侧窗口显示选中的航迹
//!-------------------------------------------------------------------------------------
/**
  * @brief IhmMainWidget::checkSelf
  * @param item
  * @param column
  */
 void IhmMainWidget::checkSelf(QTreeWidgetItem* item,int column){
     QTreeWidgetItem* p = item->parent();

     Qt::CheckState mm = item->checkState(0);

     render0 = vtkRenderer::New();
     render0->SetBackground(0.4,0.5,0.6);

     //判断被选中
     if(mm == Qt::Checked){
         //判断是父节点
        if(p==0){
            for(int i=0;i< item->childCount();i++){
                //父节点被选中，则所有子节点被选中
                item->child(i)->setCheckState(0,Qt::Checked);

                //qDebug()<<item->data(0,0).toString()+"/"+item->child(i)->data(0,0).toString();
                 this->displayLines(render0, item->data(0,0).toString()+"/"+item->child(i)->data(0,0).toString(),QString::number(i%9));
            }
        }else{ //判断是子节点被选中

            QVector<QString> *fileList = new QVector<QString>();
            int tmp=0;//变量，记录被选中的子节点个数
            for(int i=0;i< p->childCount();i++){
                if(p->child(i)->checkState(0) ==Qt::Checked){
                    tmp++;
                    fileList->append(p->child(i)->data(0,0).toString());
                }
            }
            qDebug()<<"++++++++++++++++++++++";
            for(int i=0;i<tmp;i++){
                qDebug()<<p->data(0,0).toString()+"/"+fileList->at(i);
               // this->displayLines(render0, fileList->at(i),QString::number(i%9));
                 this->displayLines(render0, p->data(0,0).toString()+"/"+fileList->at(i),QString::number(i%9));
            }

        }

        vtkWidget0->getRenWin()->AddRenderer(render0);
        vtkWidget0->getRenWin()->Render();
       // vtkWidget0->getRender()->ResetCamera();
       // vtkWidget0->update();

     }else if(mm == Qt::Unchecked){

         if(p==0){
            for(int i=0;i< item->childCount();i++){
               if( item->child(i)->checkState(0) ==Qt::Checked){
                    item->child(i)->setCheckState(0,Qt::Unchecked);

                    render0 = vtkRenderer::New();
                    render0->SetBackground(0.4,0.5,0.6);

                    vtkWidget0->getRenWin()->AddRenderer(render0);
                    vtkWidget0->getRenWin()->Render();
                    vtkWidget0->update();

               }
            }
         }else{

         }

     }
 }

void IhmMainWidget::showRightMenu()
{
    QMenu *menu=new QMenu(treeWidget);
    QAction *addAction = new QAction("add",treeWidget);

    menu->addAction(addAction);
    connect(addAction, SIGNAL(triggered(bool)), this, SLOT(addDir()));//添加信号-槽函数
    menu->exec(QCursor::pos());
}

//!-------------------------------------------------------------------------------------
//! 目录一打开，就在右侧窗口显示所有航迹
//!-------------------------------------------------------------------------------------
/**
 * @brief IhmMainWidget::addDir
 */
void IhmMainWidget::addDir(){

     QString dir = QFileDialog::getExistingDirectory(this, tr("Open Directory"),
                                                       this->dataDir,
                                                     QFileDialog::ShowDirsOnly | QFileDialog::DontResolveSymlinks);

     if(dir!=NULL){
         fileList = new QVector<QString>(); //重新赋值
         QVector<vtkUnstructuredGrid*> *multiUNGrid =  new QVector<vtkUnstructuredGrid*>();

         render = vtkRenderer::New();
         render->SetBackground(0.4,0.5,0.6);//...........

         this->TraceDir(dir);//遍历

          QTreeWidgetItem *imageItem1 = new QTreeWidgetItem(treeWidget,QStringList(QString(dir)));
          imageItem1->setIcon(0,QIcon(":/images/flight.png"));
          //imageItem1->setBackgroundColor(0,QColor("#e5ebf4"));//设置背景颜色
          imageItem1->setCheckState(0,imageItem1->checkState(0));

          for(int i=0;i<fileList->length();i++){
              QTreeWidgetItem *imageItem1_1 = new QTreeWidgetItem(imageItem1,QStringList(QString(fileList->at(i))));
              imageItem1_1->setCheckState(0, imageItem1_1->checkState(0));
              imageItem1->addChild(imageItem1_1);

              //显示每一条轨迹 ...........
             vtkUnstructuredGrid* unGrid = this->displayLines(render,dir+"/"+fileList->at(i),QString::number(i%9));

             //添加包围框
             multiUNGrid->append(unGrid);
          }

            /*
          //添加地球背景 https://lorensen.github.io/VTKExamples/site/Cxx/Geovis/EarthSource/
          vtkSmartPointer<vtkEarthSource> earthSource = vtkSmartPointer<vtkEarthSource>::New();
          //earthSource->OutlineOff();
          earthSource->SetRadius(100000);
          earthSource->SetOnRatio(16);
         // earthSource->SetOutline();
         // earthSource->Update();
          //Create a mapper and actor
          vtkSmartPointer<vtkPolyDataMapper> mapper = vtkSmartPointer<vtkPolyDataMapper>::New();
          mapper->SetInputConnection(earthSource->GetOutputPort());
          vtkSmartPointer<vtkActor> actor = vtkSmartPointer<vtkActor>::New();
          actor->GetProperty()->SetColor(0.211, 0.41, 0.211);
          actor->SetMapper(mapper);
          render->AddActor(actor);*/

          //------------------添加包围框START-----------------
          int kk=0;
          for(int i=0;i<multiUNGrid->size();i++){
              for(int j=0;j<multiUNGrid->at(i)->GetPoints()->GetNumberOfPoints();j++){
                   kk++;
              }
          }
          vtkPoints* points = vtkPoints::New();
          vtkPolyVertex* poly = vtkPolyVertex::New();
          vtkUnstructuredGrid* grid = vtkUnstructuredGrid::New();
          poly->GetPointIds()->SetNumberOfIds(kk);
          int ji=0;
          for(int i=0;i<multiUNGrid->size();i++){
              for(int j=0;j<multiUNGrid->at(i)->GetPoints()->GetNumberOfPoints();j++){
                   double m[3];
                   multiUNGrid->at(i)->GetPoints()->GetPoint(j,m);
                   double p[3] = { m[0],m[1],m[2]};
                   points->InsertPoint(ji,p[0],p[1],p[2]);
                   poly->GetPointIds()->SetId(ji,ji);
                   ji++;
              }
          }
          grid->SetPoints(points);
          grid->InsertNextCell(poly->GetCellType(),poly->GetPointIds());
          //数据类型转换
          vtkSmartPointer<vtkGeometryFilter> geometryFilter = vtkSmartPointer<vtkGeometryFilter>::New();
          geometryFilter->SetInputData(grid);
          geometryFilter->Update();
          vtkPolyData* polydata = geometryFilter->GetOutput();

            //方法一
            /*
          vtkOutlineFilter* outline = vtkOutlineFilter::New();
          vtkPolyDataMapper* outlineMapper = vtkPolyDataMapper::New();
          vtkActor* outlineActor = vtkActor::New();
          outline->SetInputData(polydata);
          outlineMapper->SetInputConnection(outline->GetOutputPort());
          outlineActor->SetMapper(outlineMapper);
          outlineActor->GetProperty()->SetColor(1.0,1.0,1.0);
          render->AddActor(outlineActor);*/

          //方法二  参考 https://lorensen.github.io/VTKExamples/site/Cxx/Visualization/CubeAxesActor/
            vtkPolyDataMapper* mapper = vtkPolyDataMapper::New();
            vtkActor* superquadricActor = vtkActor::New();
            vtkCubeAxesActor* cubeAxesActor = vtkCubeAxesActor::New();
            mapper->SetInputData(polydata);
            superquadricActor->SetMapper(mapper);
           cubeAxesActor->SetBounds(polydata->GetBounds());
           cubeAxesActor->SetCamera(render->GetActiveCamera());
           cubeAxesActor->GetTitleTextProperty(0)->SetColor(1.0,0.0,1.0); //X轴标题颜色
           cubeAxesActor->GetLabelTextProperty(0)->SetColor(1.0,0.0,1.0); //X轴标签label颜色
           cubeAxesActor->GetTitleTextProperty(1)->SetColor(0.0,1.0,0.0);
           cubeAxesActor->GetLabelTextProperty(1)->SetColor(0.0,1.0,0.0);
           cubeAxesActor->GetTitleTextProperty(2)->SetColor(0.0,0.0,1.0);
           cubeAxesActor->GetLabelTextProperty(2)->SetColor(0.0,0.0,1.0);
           cubeAxesActor->DrawXGridlinesOn();
           cubeAxesActor->DrawYGridlinesOn();
           cubeAxesActor->DrawZGridlinesOn();
           cubeAxesActor->SetGridLineLocation(cubeAxesActor->VTK_GRID_LINES_FURTHEST);

           cubeAxesActor->XAxisMinorTickVisibilityOff();
           cubeAxesActor->YAxisMinorTickVisibilityOff();
           cubeAxesActor->ZAxisMinorTickVisibilityOff();

           render->AddActor(cubeAxesActor);
           render->AddActor(superquadricActor);
           render->GetActiveCamera()->Azimuth(30);
           render->GetActiveCamera()->Elevation(30);
           render->ResetCamera();
           //------------------添加包围框END-----------------


          //...........
          vtkWidget1->getRenWin()->AddRenderer(render);
          vtkWidget1->getRenWin()->Render();
          vtkWidget1->update();
     }
}


void IhmMainWidget::delIem(QTreeWidgetItem*,int){
    qDebug()<<"del...?";
}

void IhmMainWidget::TraceDir(QString path)
{
    QDir dir(path);
    if(!dir.exists() || !dir.makeAbsolute())
    {
        return ;
    }

    QFileInfoList list = dir.entryInfoList();
    for(QFileInfo info : list)
    {
        if(info.fileName() == "." || info.fileName() == "..")
        {
            //排除 .和..
            continue;
        }
        if(info.isDir())
        {
            //如果是目录，则进行递归遍历
            TraceDir(info.fileName());
        }
        else
        {
            fileList->append(info.fileName());  //info.filePath()
            //普通文件，则直接输出
        }
    }
}

vtkUnstructuredGrid* IhmMainWidget::displayLines(vtkRenderer* render,QString filename,QString color){
    VisualizationTra vTra;
    vTra.readFile(filename);
    vTra.showPoints(render,color);

    //添加vtkUnstructuredGrid列表
    vtkUnstructuredGrid *listUnGrid =  vTra.getGrid();
    return listUnGrid;

    /*  //https://lorensen.github.io/VTKExamples/site/Cxx/PolyData/Outline/
    vtkSmartPointer<vtkOutlineFilter> outline =
        vtkSmartPointer<vtkOutlineFilter>::New();
    outline->SetInputData(vTra.getGrid());
    vtkSmartPointer<vtkPolyDataMapper> outlineMapper =
       vtkSmartPointer<vtkPolyDataMapper>::New();
     outlineMapper->SetInputConnection(outline->GetOutputPort());
     vtkSmartPointer<vtkActor> outlineActor =
       vtkSmartPointer<vtkActor>::New();
     outlineActor->SetMapper(outlineMapper);
     outlineActor->GetProperty()->SetColor(0,0,0);
     render->AddActor(outlineActor);

    /*
    double bounds[6];
    vTra.getGrid()->GetBounds(bounds);
    std::cout  << "xmin: " << bounds[0] << " "
                << "xmax: " << bounds[1] << "; "
                << "ymin: " << bounds[2] << " "
                << "ymax: " << bounds[3] << "; "
                << "zmin: " << bounds[4] << " "
                << "zmax: " << bounds[5] << std::endl; */

}

void IhmMainWidget::setDataPath(QString path){
    this->dataDir = path;
}
