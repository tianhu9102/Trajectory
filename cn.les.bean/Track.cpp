#include "Track.h"

Track::Track()
{
    this->trackNo=0;
    this->x=0.0;
    this->y=0.0;
    this->mcl=0;
    this->speed=0.0;
    this->dir=0.0;
    this->ssr=0.0;
    this->msg_Length=0;
    this->timestamped="";
    this->ulstatus=0;
    this->warnmark=0;
    this->planno=0;
    this->timeout=0;
    this->longitude=0.0;
    this->latitude=0.0;
    this->acid="";
}


int Track::get_trackNo(){
    return this->trackNo;
}

double Track::get_x(){
    return this->x;
}

double Track::get_y(){
    return this->y;
}

int Track::get_mcl(){
    return this->mcl;
}

double Track::get_speed(){
    return this->speed;
}

double Track::get_dir(){
    return this->dir;
}

double Track::get_ssr(){
    return this->ssr;
}

int Track::get_msgLength(){
    return this->msg_Length;
}

QString Track::get_timestamped(){
    return this->timestamped;
}

int Track::get_ulstatus(){
    return this->ulstatus;
}

int Track::get_warnmark(){
    return this->warnmark;
}

int Track::get_planno(){
    return this->planno;
}

int Track::get_timeout(){
    return this->timeout;
}

double Track::get_longitude(){
    return this->longitude;
}

double Track::get_latitude(){
    return this->latitude;
}

QString Track::get_acid(){
    return this->acid;
}


void Track::set_trackNo(int trackNo){
     this->trackNo = trackNo;
}
void Track::set_x(double x){
    this->x=x;
}

void Track::set_y(double y){
    this->y=y;
}

void Track::set_mcl(int mcl){
    this->mcl=mcl;
}

void Track::set_speed(double speed){
    this->speed=speed;
}

void Track::set_dir(double dir){
    this->dir=dir;
}

void Track::set_ssr(double ssr){
    this->ssr=ssr;
}

void Track::set_msgLength(int msg_Length){
    this->msg_Length=msg_Length;
}

void Track::set_timestamped(QString timestamped){
    this->timestamped=timestamped;
}

void Track::set_ulstatus(int ulstatus){
    this->ulstatus=ulstatus;
}

void Track::set_warnmark(int warnmark){
    this->warnmark=warnmark;
}

void Track::set_planno(int planno){
    this->planno=planno;
}

void Track::set_timeout(int timeout){
    this->timeout=timeout;
}

void Track::set_longitude(double longitude){
    this->longitude=longitude;
}

void Track::set_latitude(double latitude){
    this->latitude=latitude;
}

void Track::set_acid(QString acid){
    this->acid=acid;
}
