package com.antonromanov.arnote.services;

public class MultiThreadConsolidatedCalculation implements Runnable {

    int j = 0;

    @Override
    public void run() {
        for (int i = 0; i < 1000; i++) {
            try {
                Thread.sleep(200);
                j = j + 1;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
