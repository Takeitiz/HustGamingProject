package com.hust.common.entity.order;

import com.hust.common.entity.Customer;
import com.hust.common.entity.IdBasedEntity;
import jakarta.persistence.*;
import java.util.*;

@Entity
@Table(name = "orders")
public class Order extends IdBasedEntity {
    @Column(name = "first_name", nullable = false, length = 45)
    protected String firstName;

    @Column(name = "last_name", nullable = false, length = 45)
    protected String lastName;

    @Column(name = "phone_number", nullable = false, length = 15)
    protected String phoneNumber;

    @Column(name = "address_line_1", nullable = false, length = 64)
    protected String addressLine1;

    @Column(name = "address_line_2", length = 64)
    protected String addressLine2;

    @Column(nullable = false, length = 45)
    protected String city;

    @Column(nullable = false, length = 45)
    protected String state;

    @Column(name = "postal_code", nullable = false, length = 10)
    protected String postalCode;

    @Column(nullable = false, length = 45)
    private String country;
    private Date orderTime;
    private float productCost;
    private float subtotal;
    private float tax;
    private float total;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<OrderDetail> orderDetails = new HashSet<>();

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("updatedTime ASC")
    private List<OrderTrack> orderTracks = new ArrayList<>();

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    public float getProductCost() {
        return productCost;
    }

    public void setProductCost(float productCost) {
        this.productCost = productCost;
    }

    public float getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(float subtotal) {
        this.subtotal = subtotal;
    }

    public float getTax() {
        return tax;
    }

    public void setTax(float tax) {
        this.tax = tax;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }
    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Set<OrderDetail> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(Set<OrderDetail> orderDetails) {
        this.orderDetails = orderDetails;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public void copyAddressFromCustomer() {
        setFirstName(customer.getFirstName());
        setLastName(customer.getLastName());
        setPhoneNumber(customer.getPhoneNumber());
        setAddressLine1(customer.getAddressLine1());
        setAddressLine2(customer.getAddressLine2());
        setCity(customer.getCity());
        setCountry(customer.getCountry().getName());
        setPostalCode(customer.getPostalCode());
        setState(customer.getState());
    }

    @Override
    public String toString() {
        return "Order [id=" + id + ", subtotal=" + subtotal + ", paymentMethod=" + paymentMethod + ", status=" + status
                + ", customer=" + customer.getFullName() + "]";
    }

    @Transient
    public String getDestination() {
        String destination =  city + ", ";
        if (state != null && !state.isEmpty()) destination += state + ", ";
        destination += country;

        return destination;
    }

    @Transient
    public String getShippingAddress() {
        String address = firstName;

        if (lastName != null && !lastName.isEmpty()) address += " " + lastName;

        if (!addressLine1.isEmpty()) address += ", " + addressLine1;

        if (addressLine2 != null && !addressLine2.isEmpty()) address += ", " + addressLine2;

        if (!city.isEmpty()) address += ", " + city;

        if (state != null && !state.isEmpty()) address += ", " + state;

        address += ", " + country;

        if (!postalCode.isEmpty()) address += ". Postal Code: " + postalCode;
        if (!phoneNumber.isEmpty()) address += ". Phone Number: " + phoneNumber;

        return address;
    }

    public List<OrderTrack> getOrderTracks() {
        return orderTracks;
    }

    public void setOrderTracks(List<OrderTrack> orderTracks) {
        this.orderTracks = orderTracks;
    }

    @Transient
    public String getRecipientName() {
        String name = firstName;
        if (lastName != null && !lastName.isEmpty()) name += " " + lastName;
        return name;
    }

    @Transient
    public String getRecipientAddress() {
        String address = addressLine1;

        if (addressLine2 != null && !addressLine2.isEmpty()) address += ", " + addressLine2;

        if (!city.isEmpty()) address += ", " + city;

        if (state != null && !state.isEmpty()) address += ", " + state;

        address += ", " + country;

        if (!postalCode.isEmpty()) address += ". " + postalCode;

        return address;
    }

    @Transient
    public boolean isCreditCard() {
        return paymentMethod.equals(PaymentMethod.CREDIT_CARD);
    }
    public boolean hasStatus(OrderStatus status) {
        for (OrderTrack aTrack : orderTracks) {
            if (aTrack.getStatus().equals(status)) {
                return true;
            }
        }

        return false;
    }

    @Transient
    public String getProductNames() {
        String productNames = "";

        productNames = "<ul>";

        for (OrderDetail detail : orderDetails) {
            productNames += "<li>" + detail.getProduct().getShortName() + "</li>";
        }

        productNames += "</ul>";

        return productNames;
    }
}