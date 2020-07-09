package ru.progwards.java2.lessons.recurtion.hanoyTower;

import java.util.LinkedList;

public class HanoyTower {
    private LinkedList<Ring>[] tower;
    private final String separator = System.lineSeparator();
    private String information = " ";
    private final int size;
    private final int start;
    private int indStart;
    private final boolean needTrace;
    private boolean firstStep = true;
    private boolean secondStep = true;
    private boolean thirdStep = true;

    public String getInformation() {
        return information;
    }

    public HanoyTower(int size, int pos, boolean needTrace) {
        this.size = size;
        this.start = pos;
        this.needTrace = needTrace;
        this.indStart = start;
        tower = new LinkedList[size];
        this.fill(pos);
    }

    private void setTrace() {
        StringBuilder sb = new StringBuilder(this.separator);
        for (int i = size - 1; i >= 0; i--) {
            for (int j = 0; j < size; j++) {
                sb.append(tower[j].isEmpty() || tower[j].size() <= i || tower[j].get(i) == null ? "  I  " : tower[j].get(i).getName());
            }
            sb.append(this.separator);
        }
        sb.append("=================");
        if (!this.information.equals(sb.toString())) {
            this.information = sb.toString();
            if (this.needTrace) {
                System.out.println(information);
            }
        }
    }

    private void fill(int pos) {
        for (int i = 0; i < size; i++) {
            tower[i] = new LinkedList<>();
        }

        for (int i = this.size - 1; i >= 0; i--) {
            tower[pos].addLast(new Ring(i + 1));
        }
    }

    private int checkingIntoArray(int x) {
        return x == this.size - 1 ? 0 : x + 1;
    }

    public void start() {
        this.find(checkingIntoArray(this.start));
    }

    private int move(int pos) {
        Ring temp = tower[pos].pollLast();
        pos = this.checkingIntoArray(pos);
        tower[pos].addLast(temp);
        return pos;
    }

    private void find(int pos) {
        this.setTrace();
        if (tower[(start + 1) % size].size() == size) {
            System.out.println();
            return;
        }
        if (tower[pos].isEmpty() && firstStep) { // First step - destroy the tower
            tower[pos].addLast(tower[indStart].pollLast());
            pos = checkingIntoArray(pos);
            find(pos);
        }
        firstStep = false;
        pos = checkingIntoArray(pos);

        if (secondStep) { // second step - prepare a new place for building
            pos = this.move(pos);
            this.setTrace();
            indStart = this.move(indStart);
            this.setTrace();
            tower[start].addLast(tower[pos].pollLast());
            this.setTrace();
            secondStep = false;
            find(pos);
        }

        if (thirdStep) { // third step - build the tower
            if (tower[pos].size() != 0 && tower[pos].getLast().getValue() + 1 == tower[indStart].getLast().getValue()) {
                tower[indStart].addLast(tower[pos].pollLast());
            }
            thirdStep = (tower[indStart].getLast().getValue() != 1);
            find(pos);
        }
    }
}