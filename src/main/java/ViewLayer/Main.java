package ViewLayer;

import bohonos.demski.gorska.limiszewska.mieldzioc.logicalLayer.Coordinates;
import bohonos.demski.gorska.limiszewska.mieldzioc.logicalLayer.Table;

import java.io.IOException;

/**
 * Created by Agnieszka on 2015-04-20.
 */
public class Main {
    public static void main(String[] args) throws IOException {

        MainFrame okno = MainFrame.getInstance();


        //testy randomów - author Marta
        System.out.println("");
        System.out.println("TESTY");

        Coordinates a = new Coordinates(2,3);


        Table m = new Table(1, a);
        for (Integer i=0; i<10; i++){

            try {
                m.randOrder();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

}

