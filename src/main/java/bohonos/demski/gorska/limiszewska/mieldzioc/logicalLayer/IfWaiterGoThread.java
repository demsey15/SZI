package bohonos.demski.gorska.limiszewska.mieldzioc.logicalLayer;

/**
 * Created by Agnieszka on 2015-06-08.
 */
import bohonos.demski.gorska.limiszewska.mieldzioc.logicalLayer.neuralNetwork.StayOrGo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class IfWaiterGoThread implements Runnable {

    public void run() {
        try {
            StayOrGo stayOrGo = new StayOrGo(0.2, 0.6, 0.13);
        } catch (IOException e) {
            e.printStackTrace();
        }

        while (true){

        }
    }
}
