#ifndef FUNLISTDIR_H
#define FUNLISTDIR_H

#include <QString>
#include <QDir>
#include <QStringList>
#include <QFileInfo>
#include <QList>
#include <qdebug.h>

class FunListDir
{
public:
    FunListDir();
    void readDir(QString dirStr);

    QList<QFileInfo> *getFileInfog();

    int getSize();

    QString getFileName(int pos);
    QString getProjectPath();

private:
    QList<QFileInfo> *fileInfog;

};

#endif // FUNLISTDIR_H
