package com.samsung.gridprime.async;

import com.samsung.gridprime.control.PrimeControl;
import com.samsung.gridprime.util.Utils;

/**
 * Created by michelcalacina on 26/02/17.
 */

public class LoadPrimeTask implements Runnable {

    private LoadPrimeCallBack callBack;
    private PrimeControl primeControl;

    public LoadPrimeTask(LoadPrimeCallBack callBack) {
        this.callBack = callBack;
        primeControl = new PrimeControl();
    }

    @Override
    public void run() {
        callBack.onPostExecute(primeControl.generateRandomPrimes());
    }
}
