package cn.les.kghj;


public class Track {

    /**
     * 航班号
     */
    private String ACID;

    /**
     * 坐标系X轴坐标
     */
    private double x;

    /**
     * 坐标系y轴坐标
     */
    private double y;

    /**
     * 高度 10m为单位
     */
    private int mcl;

    /**
     * 速率
     */
    private double speed;

    /**
     * 方向角
     */
    private double dir;

    /**
     * 二次代码
     */
    private String SSR;

    /**
     * 报文长度
     */
    private int msg_length = 0;

    private String timestamp;

    private long ulStatus;

    /**
     * 告警标记位
     */
    private int warnMark;

    /**
     * plan coupled
     */
    private int planno;
    

    /**
     * refers to hjh in c proagram
     */
    private int trackNo;

    /**
     * 根据告警标记判的几个告警标记判断的告警标志
     * 低高度
     */
    private int msaw;

    /**
     * 冲突告警
     */
    private int ca;

    /**
     * 危险区
     */
    private int daiw;

    /**
     * 根据目标位置判断的扇区
     */
    private String sector;
    
    private double lonitude;
    
    private double latitude;
    
    private int timeout;
    
    

    public Track() {
        this.ACID = "";
        this.x=0;
        this.y=0;
        this.mcl=0;
        this.speed=0;
        this.dir=0;
        this.SSR="";
        this.ulStatus = 0;
        this.warnMark = 0;
        this.planno=0;
        this.timeout=0;
        this.trackNo=0;
        this.msaw=0;
        this.ca=0;
        this.daiw=0;
        this.lonitude=0;
        this.latitude=0;
    }

    public String getACID() {
        return ACID;
    }

    public void setACID(String ACID) {
        this.ACID = ACID;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public int getMcl() {
        return mcl;
    }

    public void setMcl(int mcl) {
        this.mcl = mcl;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getDir() {
        return dir;
    }

    public void setDir(double dir) {
        this.dir = dir;
    }

    public String getSSR() {
        return SSR;
    }

    public void setSSR(String SSR) {
        this.SSR = SSR;
    }

    public int getMsg_length() {
        return msg_length;
    }

    public void setMsg_length(int msg_length) {
        this.msg_length = msg_length;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public long getUlStatus() {
        return ulStatus;
    }

    public void setUlStatus(long ulStatus) {
        this.ulStatus = ulStatus;
    }

    public int getWarnMark() {
        return warnMark;
    }

    public void setWarnMark(int warnMark) {
        this.warnMark = warnMark;
    }

    public int getPlanno() {
        return planno;
    }

    public void setPlanno(int planno) {
        this.planno = planno;
    }

    public int getTrackNo() {
        return trackNo;
    }

    public void setTrackNo(int trackNo) {
        this.trackNo = trackNo;
    }

    public int getMsaw() {
        return msaw;
    }

    public void setMsaw(int msaw) {
        this.msaw = msaw;
    }

    public int getCa() {
        return ca;
    }

    public void setCa(int ca) {
        this.ca = ca;
    }

    public int getDaiw() {
        return daiw;
    }

    public void setDaiw(int daiw) {
        this.daiw = daiw;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

	public double getLonitude() {
		return lonitude;
	}

	public void setLonitude(double lonitude) {
		this.lonitude = lonitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public int getTimeout() {
		return timeout;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}
    
}

