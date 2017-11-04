#ifndef TRACK_H
#define TRACK_H

#include <QString>
#include <iostream>

using namespace std;

class Track
{
public:
    Track();

    int get_trackNo();
    double get_x();
    double get_y();
    int get_mcl();
    double get_speed();
    double get_dir();
    double get_ssr();
    int get_msgLength();
    QString get_timestamped();
    int get_ulstatus();
    int get_warnmark();
    int get_planno();
    int get_timeout();
    double get_longitude();
    double get_latitude();
    QString get_acid();

    void set_trackNo(int trackNo);
    void set_x(double x);
    void set_y(double y);
    void set_mcl(int mcl);
    void set_speed(double speed);
    void set_dir(double dir);
    void set_ssr(double ssr);
    void set_msgLength(int msg_Length);
    void set_timestamped(QString timestamped);
    void set_ulstatus(int ulstatus);
    void set_warnmark(int warnmark);
    void set_planno(int planno);
    void set_timeout(int timeout);
    void set_longitude(double longitude);
    void set_latitude(double latitude);
    void set_acid(QString acid);

private:
    int trackNo;
    double x;
    double y;
    int mcl;
    double speed;
    double dir;
    double ssr;
    int msg_Length;
    QString timestamped;
    int ulstatus;
    int warnmark;
    int planno;
    int timeout;
    double longitude;
    double latitude;
    QString acid;
};

#endif // TRACK_H
