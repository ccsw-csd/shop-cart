package com.cap.notification.request;

public class ShopOrderRequest {

    private Boolean success;

    private ShopOrderDataRequest data;

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
