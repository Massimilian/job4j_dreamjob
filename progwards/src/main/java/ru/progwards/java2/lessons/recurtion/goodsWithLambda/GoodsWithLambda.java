package ru.progwards.java2.lessons.recurtion.goodsWithLambda;

import java.time.Duration;
import java.time.Instant;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class GoodsWithLambda {
    private List<Goods> list;

    public void setGoods(List<Goods> goods) {
        if (this.list == null) {
            this.list = goods;
        } else {
            this.list = Stream.of(this.list, goods).flatMap(Collection::stream).collect(Collectors.toList());
        }
    }

    public List<Goods> getGoods() {
        return this.list;
    }

    public List<Goods> sortByName() {
        return this.list.stream().sorted(Comparator.comparing(Goods::getName)).collect(Collectors.toList());
    }

    public List<Goods> sortByNumber() {
        return this.list.stream().sorted(Comparator.comparing(o -> o.getNumber().toLowerCase())).collect(Collectors.toList());
    }

    public List<Goods> sortByPartNumber() {
        return this.list.stream().sorted(Comparator.comparing(o -> o.getNumber().toUpperCase().substring(0, 3))).collect(Collectors.toList());
    }

    public List<Goods> sortByAvailabilityAndNumber() {
        return this.sortByNumber().stream().sorted(Comparator.comparingInt(Goods::getAvailable)).collect(Collectors.toList());
    }

    public List<Goods> expiredAfter(Instant date) {
        return this.list.stream().sorted(Comparator.comparingInt(o -> (int) o.getExpired().getEpochSecond())).dropWhile(o -> Duration.between(date, o.getExpired()).isNegative()).collect(Collectors.toList());
    }

    public List<Goods> сountLess(int count) {
        return this.list.stream().filter(goods -> goods.getAvailable() < count).sorted(Comparator.comparingInt(Goods::getAvailable)).collect(Collectors.toList());
    }

    public List<Goods> сountBetween(int count1, int count2) {
        return this.list.stream().filter(goods -> goods.getAvailable() < count2 && goods.getAvailable() > count1).sorted(Comparator.comparingInt(Goods::getAvailable)).collect(Collectors.toList());
    }
}
