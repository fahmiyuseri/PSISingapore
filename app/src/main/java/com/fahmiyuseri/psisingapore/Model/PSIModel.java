package com.fahmiyuseri.psisingapore.Model;

/**
 * Created by IRSB on 15/3/2018.
 */

public class PSIModel {
    public PSIModel(String name, int index) {
        this.name = name;
        this.index = index;
    }

    private String name;
    private int index;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
