package com.zjc.drivingSchoolT.eventbus;


import com.zjc.drivingSchoolT.db.model.OrderItem;

/**
 * Created by Administrator on 2015/7/14.
 */
public class StudyDistributionEvent {
    private OrderItem orderItem;

    public StudyDistributionEvent(OrderItem orderItem) {
        this.orderItem = orderItem;
    }

    public OrderItem getOrderItem() {
        return orderItem;
    }

    public void setOrderItem(OrderItem orderItem) {
        this.orderItem = orderItem;
    }
}
