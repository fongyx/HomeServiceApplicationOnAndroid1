package com.example.fong_.homeserviceapplicationonandroid.Model;

public class Services {


    private String serviceID;
    private String customerName;
    private String customerID;
    private String contractorName;
    private String contractorID;
    private String ServiceStatus;

    private String Category;
    private String ServiceAddress;
    private String ServiceDate;
    private String ServiceTime;
    private String ServiceDescription;
    private String FixedPrice;
    private String BiddingPrice;
    private String rating;

    public Services() {
    }

    public Services(String serviceID, String customerName) {
        this.serviceID = serviceID;
        this.customerName = customerName;
    }

    public Services(String customerName, String contractorName, String serviceStatus, String category, String serviceAddress, String serviceDate, String serviceTime, String serviceDescription, String fixedPrice, String biddingPrice) {
        this.customerName = customerName;
        this.contractorName = contractorName;
        ServiceStatus = serviceStatus;
        Category = category;
        ServiceAddress = serviceAddress;
        ServiceDate = serviceDate;
        ServiceTime = serviceTime;
        ServiceDescription = serviceDescription;
        FixedPrice = fixedPrice;
        BiddingPrice = biddingPrice;
    }

    public Services(String serviceID, String customerName, String contractorName, String serviceStatus, String category, String serviceAddress, String serviceDate, String serviceTime, String serviceDescription, String fixedPrice, String biddingPrice) {
        this.serviceID = serviceID;
        this.customerName = customerName;
        this.contractorName = contractorName;
        ServiceStatus = serviceStatus;
        Category = category;
        ServiceAddress = serviceAddress;
        ServiceDate = serviceDate;
        ServiceTime = serviceTime;
        ServiceDescription = serviceDescription;
        FixedPrice = fixedPrice;
        BiddingPrice = biddingPrice;
    }

    public Services(String serviceID, String customerName,String customerID, String contractorName, String contractorID, String serviceStatus, String category, String serviceAddress, String serviceDate, String serviceTime, String serviceDescription, String fixedPrice, String biddingPrice, String rating) {
        this.serviceID = serviceID;
        this.customerName = customerName;
        this.customerID = customerID;
        this.contractorName = contractorName;
        this.contractorID = contractorID;
        ServiceStatus = serviceStatus;
        Category = category;
        ServiceAddress = serviceAddress;
        ServiceDate = serviceDate;
        ServiceTime = serviceTime;
        ServiceDescription = serviceDescription;
        FixedPrice = fixedPrice;
        BiddingPrice = biddingPrice;
        this.rating = rating;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public String getContractorID() {
        return contractorID;
    }

    public void setContractorID(String contractorID) {
        this.contractorID = contractorID;
    }

    public String getServiceID() {
        return serviceID;
    }

    public void setServiceID(String serviceID) {
        this.serviceID = serviceID;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getContractorName() {
        return contractorName;
    }

    public void setContractorName(String contractorName) {
        this.contractorName = contractorName;
    }

    public String getServiceStatus() {
        return ServiceStatus;
    }

    public void setServiceStatus(String serviceStatus) {
        ServiceStatus = serviceStatus;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getServiceAddress() {
        return ServiceAddress;
    }

    public void setServiceAddress(String serviceAddress) {
        ServiceAddress = serviceAddress;
    }

    public String getServiceDate() {
        return ServiceDate;
    }

    public void setServiceDate(String serviceDate) {
        ServiceDate = serviceDate;
    }

    public String getServiceTime() {
        return ServiceTime;
    }

    public void setServiceTime(String serviceTime) {
        ServiceTime = serviceTime;
    }

    public String getServiceDescription() {
        return ServiceDescription;
    }

    public void setServiceDescription(String serviceDescription) {
        ServiceDescription = serviceDescription;
    }

    public String getFixedPrice() {
        return FixedPrice;
    }

    public void setFixedPrice(String fixedPrice) {
        FixedPrice = fixedPrice;
    }

    public String getBiddingPrice() {
        return BiddingPrice;
    }

    public void setBiddingPrice(String biddingPrice) {
        BiddingPrice = biddingPrice;
    }
}
