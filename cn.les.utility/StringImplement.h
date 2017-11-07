#ifndef STRINGIMPLEMENT_H
#define STRINGIMPLEMENT_H

#include <QString>
#include <QByteArray>
#include <qdebug.h>
#include <QApplication>

#include <qdatetime.h>
#include <qfile.h>
#include <qdir.h>



class StringImplement
{
public:
    StringImplement();
    char* coremath(QString str);

    QString dataPath(QString exePath, QString choice);

    void createDataFile(QString dir, QString sourceFilename, QString fileType);

    QString pathChange(QString path);

private:


};

#endif // STRINGIMPLEMENT_H
