#include "StringImplement.h"


//!--------------------------------------------------
//!
//! \brief StringImplement::StringImplement
//!  2016-06-11  shenzhen  success
//!
//!--------------------------------------------------

StringImplement::StringImplement()
{

}

char* StringImplement::coremath(QString str){
    QString hubstr(str);
    char*  ch;
    QByteArray ba = hubstr.toLatin1();
    ch = ba.data();
    return ch;
}

QString StringImplement::dataPath(QString exePath, QString choice){

    // exePath 项目可执行文件路径
    QStringList qslist0 = exePath.split("/");
    //qDebug()<<exePath;
   // qDebug()<<qslist0.length();

    QString str_withexe( qslist0.at(qslist0.length()-1) );//str_withexe  项目名称.exe
    QStringList listexe = str_withexe.split(".");
    QString str_name(listexe.at(0));//str_name 项目名称
    //qDebug()<<str_name;

    QStringList listf = exePath.split(str_name);
    QString str_data_nei(listf.at(0)+str_name+"/kghj_flight"); //项目数据路径  str_data_nei


    QStringList  list2 = exePath.split("build");
    QString str_data_wai(list2.at(0)+str_name+"/kghj_flight"); //项目数据路径  str_data_wai

     QString str_data;//数据所在路径
     if(choice.contains("nei")){
        str_data = str_data_nei;

    }else{
        str_data = str_data_wai;

    }

    return str_data;
}


//!创建一个以时间为名称的文件  createDataFile("d:/data","data00",".txt");
void StringImplement::createDataFile(QString dir,QString sourceFilename, QString fileType){

    QDateTime datetime;
    QString timestr=datetime.currentDateTime().toString("yyyyMMddHHmmss");
    QString fileName = dir +"/"+ timestr +"_"+sourceFilename + fileType;//假设指定文件夹路径为D盘根目录
    qDebug()<<fileName;
    QFile file(fileName);
    file.open(QIODevice::WriteOnly | QIODevice::Text );
    QTextStream in(&file);
    in << "hb jk.";
    file.flush();
    file.close();
}

//!    输入为 D:\school\Grad_Data\0_img\image02.txt
//!    输出为 D:/school/Grad_Data/0_img/image02.txt
QString StringImplement::pathChange(QString path){
    QString newPath; //输出路径


    QStringList sList = path.split('\\');
    qDebug()<<sList.length()<<sList.at(0);

    newPath = path.replace('\\','/');

    qDebug()<<newPath;
    return newPath;

}
