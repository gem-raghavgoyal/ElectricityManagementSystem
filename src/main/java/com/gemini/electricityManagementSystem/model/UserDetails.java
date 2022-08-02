package com.gemini.electricityManagementSystem.model;

public class UserDetails {
    private String name;
    private String address;
    private String city;
    private String state;
    private String pincode;

    public UserDetails(){}
    public UserDetails(Builder builder) {
        this.name = builder.name;
        this.address = builder.address;
        this.city = builder.city;
        this.state = builder.state;
        this.pincode = builder.pincode;
    }

    public static class Builder{
        private String name;
        private String address;
        private String city;
        private String state;
        private String pincode;

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setAddress(String address) {
            this.address = address;
            return this;
        }

        public Builder setCity(String city) {
            this.city = city;
            return this;
        }

        public Builder setState(String state) {
            this.state = state;
            return this;
        }

        public Builder setPincode(String pincode) {
            this.pincode = pincode;
            return this;
        }

        public static Builder newInstance(){
            return new Builder();
        }

        public UserDetails build(){
            return new UserDetails(this);
        }
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getPincode() {
        return pincode;
    }
}
