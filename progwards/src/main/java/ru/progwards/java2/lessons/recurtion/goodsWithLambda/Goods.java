package ru.progwards.java2.lessons.recurtion.goodsWithLambda;

import java.time.Instant;

public class Goods {
    private String name;
    private String number;
    private int available;
    private double price;
    private Instant expired;

    public Goods(String name, String number, int available, double price, Instant expired) {
        this.name = name;
        this.number = this.checkNumber(number);
        this.available = available;
        this.price = price;
        this.expired = expired;
    }

    private String checkNumber(String num) {
        if (num.length() < 3) {
            for (int i = 0; i < 3 - num.length(); i++) {
                num = String.format("%s%s", num, " ");
            }
        }
        return num;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getAvailable() {
        return available;
    }

    public void setAvailable(int available) {
        this.available = available;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Instant getExpired() {
        return expired;
    }

    public void setExpired(Instant expired) {
        this.expired = expired;
    }
}