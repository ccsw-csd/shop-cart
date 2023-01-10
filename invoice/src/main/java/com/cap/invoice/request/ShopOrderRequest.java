package com.cap.invoice.request;

public class ShopOrderRequest {

    private String groupId;

    private Boolean success;

    private ShopOrderDataRequest data;

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public ShopOrderDataRequest getData() {
        return data;
    }

    public void setData(ShopOrderDataRequest data) {
        this.data = data;
    }
}
