package com.samsung.gridprime.control;

import java.util.HashSet;

/**
 * Created by michelcalacina on 26/02/17.
 */

public class CacheControl {


    private HashSet primeContents = null;
    private HashSet notPrimeContents = null;

    public CacheControl() {
        this.primeContents = new HashSet();
        this.notPrimeContents = new HashSet();
    }

    public boolean containPrime(int value) {
        return primeContents.contains(value);
    }

    public boolean containNotPrime(int value) {
        return notPrimeContents.contains(value);
    }

    public void setPrimeContent(int value) {
        this.primeContents.add(value);
    }

    public void setNotPrimeContent(int value) {
        this.notPrimeContents.add(value);
    }
}
