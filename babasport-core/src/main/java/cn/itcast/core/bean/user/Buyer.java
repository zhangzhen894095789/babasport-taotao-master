package cn.itcast.core.bean.user;

import java.io.Serializable;
import java.util.Date;

public class Buyer implements Serializable {
    /**
     * �û���
     */
    private String username;

    /**
     * ����
     */
    private String password;

    /**
     * �Ա�:0:����,1:��,2:Ů
     */
    private Integer gender;

    /**
     * ����
     */
    private String email;

    /**
     * ��ʵ����
     */
    private String realName;

    /**
     * ע��ʱ��
     */
    private Date registerTime;

    /**
     * ʡID
     */
    private String province;

    /**
     * ��ID
     */
    private String city;

    /**
     * ��ID
     */
    private String town;

    /**
     * ��ַ
     */
    private String addr;

    /**
     * �Ƿ���ɾ��:1:δ,0:ɾ����
     */
    private Boolean isDel;

    private static final long serialVersionUID = 1L;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName == null ? null : realName.trim();
    }

    public Date getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(Date registerTime) {
        this.registerTime = registerTime;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province == null ? null : province.trim();
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town == null ? null : town.trim();
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr == null ? null : addr.trim();
    }

    public Boolean getIsDel() {
        return isDel;
    }

    public void setIsDel(Boolean isDel) {
        this.isDel = isDel;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", username=").append(username);
        sb.append(", password=").append(password);
        sb.append(", gender=").append(gender);
        sb.append(", email=").append(email);
        sb.append(", realName=").append(realName);
        sb.append(", registerTime=").append(registerTime);
        sb.append(", province=").append(province);
        sb.append(", city=").append(city);
        sb.append(", town=").append(town);
        sb.append(", addr=").append(addr);
        sb.append(", isDel=").append(isDel);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}