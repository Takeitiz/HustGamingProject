package com.hust.common.entity.order;

public enum OrderStatus {

    NEW {
        @Override
        public String defaultDescription() {
            return "Order was placed by the customer";
        }

    },
    PAID {
        @Override
        public String defaultDescription() {
            return "Customer has paid this order";
        }
    };
    public abstract String defaultDescription();
}
