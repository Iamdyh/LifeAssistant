package cn.com.dyhdev.lifeassistant.entity;

/**
 * 项目名:     LifeAssistant
 * 包名:       cn.com.dyhdev.lifeassistant.entity
 * 文件名:     CourierDean
 * 作者:       dyh
 * 时间:       2018/2/17 14:49
 * 描述:       快递实体类
 */

public class CourierDean {



    private String datetime;   //时间
    private String remark;     //物流事件的描述
    private String zone;       ///* 快件当时所在区域，由于快递公司升级，现大多数快递不提供此信息 */

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }
}
