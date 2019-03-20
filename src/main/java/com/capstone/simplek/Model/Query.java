package com.capstone.simplek.Model;

public class Query {

    private boolean bus;
    private boolean esl;
    private boolean disability;

    public Query() {
    }

    public Query(boolean bus, boolean esl, boolean disability) {
        this.bus = bus;
        this.esl = esl;
        this.disability = disability;
    }

    public boolean isBus() {
        return bus;
    }

    public void setBus(boolean bus) {
        this.bus = bus;
    }

    public boolean isEsl() {
        return esl;
    }

    public void setEsl(boolean esl) {
        this.esl = esl;
    }

    public boolean isDisability() {
        return disability;
    }

    public void setDisability(boolean disability) {
        this.disability = disability;
    }

}// class