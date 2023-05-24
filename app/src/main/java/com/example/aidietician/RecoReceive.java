package com.example.aidietician;

public class RecoReceive {
    String bf,lun,din;
    int bfcal,luncal,dincal;

    public RecoReceive(String bf, String lun, String din, int bfcal, int luncal, int dincal) {
        this.bf = bf;
        this.lun = lun;
        this.din = din;
        this.bfcal = bfcal;
        this.luncal = luncal;
        this.dincal = dincal;
    }

    @Override
    public String toString() {
        return "RecoReceive{" +
                "bf='" + bf + '\'' +
                ", lun='" + lun + '\'' +
                ", din='" + din + '\'' +
                ", bfcal=" + bfcal +
                ", luncal=" + luncal +
                ", dincal=" + dincal +
                '}';
    }

    public int getDincal() {
        return dincal;
    }

    public void setDincal(int dincal) {
        this.dincal = dincal;
    }

    public int getLuncal() {
        return luncal;
    }

    public void setLuncal(int luncal) {
        this.luncal = luncal;
    }

    public int getBfcal() {
        return bfcal;
    }

    public void setBfcal(int bfcal) {
        this.bfcal = bfcal;
    }

    public String getDin() {
        return din;
    }

    public void setDin(String din) {
        this.din = din;
    }

    public String getLun() {
        return lun;
    }

    public void setLun(String lun) {
        this.lun = lun;
    }

    public String getBf() {
        return bf;
    }

    public void setBf(String bf) {
        this.bf = bf;
    }
}
