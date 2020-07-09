package ru.progwards.java2.lessons.recurtion.hanoiTower;

import org.junit.Assert;
import org.junit.Test;
import ru.progwards.java2.lessons.recurtion.hanoyTower.HanoyTower;

public class HanoyTowerTest {
    HanoyTower ht;
    String separator = System.lineSeparator();

    @Test
    public void WhenTryToBuildHanoyTowerWithTenRingsThenDoIt() {
        ht = new HanoyTower(10, 9);
        ht.start();
        String nulls = "  I    I    I    I    I    I    I    I    I  ";
        StringBuilder result = new StringBuilder(separator);
        result.append(String.format("%s%s", "<001>", nulls));
        result.append(separator);
        result.append(String.format("%s%s", "<002>", nulls));
        result.append(separator);
        result.append(String.format("%s%s", "<003>", nulls));
        result.append(separator);
        result.append(String.format("%s%s", "<004>", nulls));
        result.append(separator);
        result.append(String.format("%s%s", "<005>", nulls));
        result.append(separator);
        result.append(String.format("%s%s", "<006>", nulls));
        result.append(separator);
        result.append(String.format("%s%s", "<007>", nulls));
        result.append(separator);
        result.append(String.format("%s%s", "<008>", nulls));
        result.append(separator);
        result.append(String.format("%s%s", "<009>", nulls));
        result.append(separator);
        result.append(String.format("%s%s", "<010>", nulls));
        result.append(separator);
        result.append("=================");
        Assert.assertEquals(ht.getInformation(), result.toString());
    }
}