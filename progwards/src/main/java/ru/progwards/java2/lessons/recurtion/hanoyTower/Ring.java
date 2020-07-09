package ru.progwards.java2.lessons.recurtion.hanoyTower;

import java.util.Objects;

public class Ring {
    private String name;
    private int value;

    public Ring(int value) {
        this.name = value < 10? String.format("%s%s%s", "<00", String.valueOf(value), ">") : String.format("%s%s%s", "<0", String.valueOf(value), ">");
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ring ring = (Ring) o;
        return value == ring.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
