package cn.itcast.core.bean.product;

import java.io.Serializable;

import cn.itcast.core.web.Constants;

public class Img implements Serializable {
    /**
     * ID
     */
    private Long id;

    /**
     * ��ƷID
     */
    private Long productId;

    /**
     * ͼƬURL
     */
    private String url;

    /**
     * �Ƿ�Ĭ��:0�� 1:��
     */
    private Boolean isDef;

    private static final long serialVersionUID = 1L;
    public String getAllUrl(){
    	return Constants.IMG_URL+url;
    } 
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public Boolean getIsDef() {
        return isDef;
    }

    public void setIsDef(Boolean isDef) {
        this.isDef = isDef;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", productId=").append(productId);
        sb.append(", url=").append(url);
        sb.append(", isDef=").append(isDef);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}