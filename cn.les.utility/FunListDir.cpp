#include "FunListDir.h"


/**
 * @brief FunListDir::FunListDir
 *
 */
FunListDir::FunListDir()
{
    fileInfog = new QList<QFileInfo>();
}

//!------------------------------------------------
//!------------------------------------------------
/**
 * 读取文件夹下面的文件
 * @brief FunListDir::readDir
 * @param dirStr
 */
void FunListDir::readDir(QString dirStr){
    QDir *dir = new QDir(dirStr);  //设置路径目录
    dir->setFilter(QDir::Files | QDir::Hidden | QDir::NoSymLinks);
    dir->setSorting(QDir::Size | QDir::Reversed);

    QStringList filter;
    filter<<"*.txt";
    dir->setNameFilters(filter);

    fileInfog =new QList<QFileInfo>(dir->entryInfoList(filter));

//    for(int i=0;i<getSize();i++){
//        //qDebug()<<i<<fileInfog->at(i).fileName();
//    }

}

QList<QFileInfo> *FunListDir::getFileInfog(){
    return this->fileInfog;
}

int FunListDir::getSize(){
    return fileInfog->length();
}

QString FunListDir::getFileName(int pos){
    return fileInfog->at(pos).filePath();
}

//!------------------------------------------------
//!------------------------------------------------
/**
 *读取当前工程路径
 * @brief FunListDir::getProjectPath
 * @return
 */
QString FunListDir::getProjectPath(){
    QString path;

    return path;
}
