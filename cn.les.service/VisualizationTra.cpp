#include "VisualizationTra.h"

VisualizationTra::VisualizationTra()
{

}

void VisualizationTra::readFile(QString filepath){
    QFile fileName(filepath);
    if(!fileName.open(QIODevice::ReadOnly|QIODevice::Text)){
        qDebug()<<"sorry,can't open the file!"<<endl;
        return ;
    }

    //记录原始数据
    while(!fileName.atEnd()){
        QByteArray line=fileName.readLine();
        QString str(line);
        QStringList parame=str.split(" ");

        //!values are set here
        Track *p = new Track();
        p->set_trackNo(parame[0].toDouble(0));
        p->set_x(parame[1].toDouble(0));
        p->set_y(parame[2].toDouble(0));
        p->set_mcl(parame[3].toDouble(0)*10);

        p->set_speed(parame[4].toDouble(0));
        p->set_dir(parame[5].toDouble(0));
        p->set_ssr(parame[6].toDouble(0));
        p->set_msgLength(parame[7].toDouble(0));


        p->set_timestamped(parame[8]);
        p->set_ulstatus(parame[9].toDouble(0));
        p->set_warnmark(parame[10].toDouble(0));
        p->set_planno(parame[11].toDouble(0));

        p->set_timeout(parame[12].toDouble(0));
        p->set_longitude(parame[13].toDouble());
        p->set_latitude(parame[14].toDouble());
        p->set_acid(parame[15]);

       // std::cout<<"" <<p->get_longitude()<<" "<<p->get_latitude()<<" "<<p->get_mcl()<<std::endl;
        sourceTrajectoryPoints.append(p);
    }
}

void VisualizationTra::showPoints(vtkRenderer *render,QString color){
    points = vtkPoints::New();
    poly = vtkPolyVertex::New();
    grid = vtkUnstructuredGrid::New();
    mapper = vtkDataSetMapper::New();
    actor = vtkActor::New();

    if(sourceTrajectoryPoints.size()<=0){
        qDebug()<<"no points found!";
    }

    poly->GetPointIds()->SetNumberOfIds(sourceTrajectoryPoints.size());
    for(int cpt=0; cpt < sourceTrajectoryPoints.size(); cpt++){
//        double p[3] = { (sourceTrajectoryPoints.at(cpt)->get_longitude()*1000),
//                       (sourceTrajectoryPoints.at(cpt)->get_latitude()*1000),
//                       sourceTrajectoryPoints.at(cpt)->get_mcl()};
        double p[3] = { (sourceTrajectoryPoints.at(cpt)->get_x()),
                       (sourceTrajectoryPoints.at(cpt)->get_y()),
                       sourceTrajectoryPoints.at(cpt)->get_mcl()*80};
        points->InsertPoint(cpt,p[0],p[1],p[2]);
        poly->GetPointIds()->SetId(cpt,cpt);
    }

    grid->SetPoints(points);
    grid->InsertNextCell(poly->GetCellType(),poly->GetPointIds());
    mapper->SetInputData(grid);

    this->actor->SetMapper(mapper);
    this->actor->GetProperty()->SetOpacity(1);
    this->actor->GetProperty()->SetPointSize(5);
    if(color.contains("1")){
        this->actor->GetProperty()->SetColor(1,0,0);
    }else if(color.contains("2")){
        this->actor->GetProperty()->SetColor(0,1,0);
    }else if(color.contains("3")){
        this->actor->GetProperty()->SetColor(0,0,1);
    }else if(color.contains("4")){
        this->actor->GetProperty()->SetColor(1,0,1);
    }else if(color.contains("5")){
        this->actor->GetProperty()->SetColor(1,1,1);
    }else if(color.contains("6")){
        this->actor->GetProperty()->SetColor(0,1,1);
    }else if(color.contains("7")){
        this->actor->GetProperty()->SetColor(0.6,0.4,1);
    }else if(color.contains("0")){
        this->actor->GetProperty()->SetColor(0.8,0.4,0);
    }else if(color.contains("9")){
        this->actor->GetProperty()->SetColor(0.7,0.1,0.5);
    }else if(color.contains("8")){
        this->actor->GetProperty()->SetColor(0.3,0.2,0.5);
    }else {
        this->actor->GetProperty()->SetColor(0.8,0.5,0.5);
    }

    render->AddActor(actor);
    render->SetBackground(0.4,0.5,0.6);
    grid1 = grid;
}

vtkUnstructuredGrid *VisualizationTra::getGrid(){
    return this->grid1;
}
