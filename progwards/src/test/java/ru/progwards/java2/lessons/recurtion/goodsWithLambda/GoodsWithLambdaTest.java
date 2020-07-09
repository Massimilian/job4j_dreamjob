package ru.progwards.java2.lessons.recurtion.goodsWithLambda;

import org.junit.Assert;
import org.junit.Test;

import java.time.Instant;
import java.util.List;

public class GoodsWithLambdaTest {
    GoodsWithLambda gwl = new GoodsWithLambda();
    List<Goods> goods = List.of(new Goods("Third good", "C3", 1323, 1.0, Instant.now().plusSeconds(315360)),
            new Goods("First good", "E1", 213, 100.0, Instant.now().plusSeconds(31536000)),
            new Goods("Second good", "b2", 23, 1000.0, Instant.now().plusSeconds(6307200)),
            new Goods("Forth good", "d4", 233, 10.0, Instant.now().plusSeconds(3153600)));
    Goods special = new Goods("Special good", "b1", 23, 1000.0, Instant.now().plusSeconds(3153600));

    @Test
    public void whenTryToAddValuesThenReturnThem() {
        gwl.setGoods(goods);
        Assert.assertEquals(gwl.getGoods().size(), 4);
    }

    @Test
    public void whenTryTopSortGoodsByNameThenDoIt() {
        gwl.setGoods(goods);
        List<Goods> temp = gwl.sortByName();
        Assert.assertEquals(temp.get(0).getName(), "First good");
    }

    @Test
    public void whenTryToSortGoodsByNumberThenDoIt() {
        gwl.setGoods(goods);
        List<Goods> temp = gwl.sortByNumber();
        Assert.assertEquals(temp.get(0).getName(), "Second good");
    }

    @Test
    public void whenTryToSortGoodsByShortNumberThenDoIt() {
        gwl.setGoods(goods);
        List<Goods> temp = gwl.sortByPartNumber();
        Assert.assertEquals(temp.get(0).getName(), "Second good");
    }

    @Test
    public void whenTryToSortGoodsByCountIfEqualByArticulNumber() {
        gwl.setGoods(goods);
        gwl.setGoods(List.of(special));
        List<Goods> temp = gwl.sortByAvailabilityAndNumber();
        Assert.assertEquals(gwl.getGoods().get(4).getName(), "Special good");
        Assert.assertEquals(temp.get(0).getName(), "Special good");
    }

    @Test
    public void whenTryToSortGoodsByDateThenDoItAndReturnListOfGoodsAfterDate() {
        gwl.setGoods(goods);
        List<Goods> temp = gwl.expiredAfter(Instant.now().plusSeconds(6307200));
        Assert.assertEquals(temp.size(), 1);
    }

    @Test
    public void whenTryTiTakeGoodsWithOnlySpecialCountThenDoIt() {
        gwl.setGoods(goods);
        List<Goods> temp = gwl.сountLess(215);
        Assert.assertEquals(temp.size(), 2);
        Assert.assertEquals(temp.get(0).getName(), "Second good");
    }

    @Test
    public void whenTryToTakeListOfGoodsWithCountBetweenTwoPointsThenDoIt() {
        gwl.setGoods(goods);
        List<Goods> temp = gwl.сountBetween(23, 300);
        Assert.assertEquals(temp.size(), 2);
    }
}
