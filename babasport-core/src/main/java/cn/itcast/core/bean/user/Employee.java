package cn.itcast.core.bean.user;

import java.io.Serializable;

public class Employee implements Serializable {
    /**
     * �û���
     */
    private String username;

    /**
     * ����
     */
    private String password;

    /**
     * ѧ��
     */
    private String degree;

    /**
     * ����
     */
    private String email;

    /**
     * �Ա� 
     */
    private Integer gender;

    /**
     * ͼƬ(ͷ��)
     */
    private String imgUrl;

    /**
     * �ֻ�
     */
    private String phone;

    /**
     * ��ʵ����
     */
    private String realName;

    /**
     * ��ҵѧУ
     */
    private String school;

    /**
     * �Ƿ�ɾ�� 1:δɾ�� 0:ɾ��
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

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree == null ? null : degree.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl == null ? null : imgUrl.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName == null ? null : realName.trim();
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school == null ? null : school.trim();
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
        sb.append(", degree=").append(degree);
        sb.append(", email=").append(email);
        sb.append(", gender=").append(gender);
        sb.append(", imgUrl=").append(imgUrl);
        sb.append(", phone=").append(phone);
        sb.append(", realName=").append(realName);
        sb.append(", school=").append(school);
        sb.append(", isDel=").append(isDel);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}