package com.capstone.simplek.Model;

public class Query {

    private boolean daycare;
    private boolean language;
    private boolean disability;
    private boolean transportation;
    private boolean financial;

    public Query() {
    }

    public Query(boolean daycare, boolean language, boolean disability, boolean transportation, boolean financial) {
        this.daycare = daycare;
        this.language = language;
        this.disability = disability;
        this.transportation = transportation;
        this.financial = financial;
    }

    public boolean isDaycare() {
        return daycare;
    }

    public void setDaycare(boolean daycare) {
        this.daycare = daycare;
    }

    public boolean isLanguage() {
        return language;
    }

    public void setLanguage(boolean language) {
        this.language = language;
    }

    public boolean isDisability() {
        return disability;
    }

    public void setDisability(boolean disability) {
        this.disability = disability;
    }

    public boolean isTransportation() {
        return transportation;
    }

    public void setTransportation(boolean transportation) {
        this.transportation = transportation;
    }

    public boolean isFinancial() {
        return financial;
    }

    public void setFinancial(boolean financial) {
        this.financial = financial;
    }

}// class