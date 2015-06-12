package ViewLayer;



import bohonos.demski.gorska.limiszewska.mieldzioc.logicalLayer.Control;
import bohonos.demski.gorska.limiszewska.mieldzioc.logicalLayer.Coordinates;
import bohonos.demski.gorska.limiszewska.mieldzioc.logicalLayer.OnMoveListener;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.*;
import java.util.List;

/**
 * Created by Agnieszka on 2015-04-21.
 */

//Panel odpowiedzialny za wyœwietlenie mapy

public class MapPanel extends JPanel implements OnMoveListener{

    int szerokosc;
    int wysokosc;
    int szerokoscPola;
    int wysokoscPola;
    Control control;
    java.util.List<Coordinates> wspolrzedneNaMapie;
    java.util.List<ImageIcon> numeryStolikow;
    ImageIcon table, floor, green, grey, red, waiter;
    int waiterXpos, waiterYpos; //pozycja w px
    public Coordinates waiterCoordinates  = new Coordinates(0,0); //pozycja na mapie

   // private Thread runner = null;
    private int krok = 1;


    public MapPanel(int szerokosc, int wysokosc){

        this.szerokosc = szerokosc;
        this.wysokosc = wysokosc;

        szerokoscPola = calculateHeightForIcon();
        wysokoscPola = calculateHeightForIcon();

        setPreferredSize(new Dimension(szerokoscPola*21, wysokoscPola*17));

        waiterXpos = 0;
        waiterYpos = 0;


        this.setLayout(null);

        //wykorzystanie warstwy logicznej
        control = Control.getInstance();
        try {
            control.prepareMap();
            wspolrzedneNaMapie =  control.getAllCoordinates();
            control.registerOnWaiterMoveListener(this);
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.setBackground(Color.white);

        table = new ImageIcon("resources\\table.png");
        floor = new ImageIcon("resources\\floor.png");
        green = new ImageIcon("resources\\green.png");
        grey = new ImageIcon("resources\\grey.png");
        red = new ImageIcon("resources\\red.png");
        waiter = new ImageIcon("resources\\waiter.png");

        numeryStolikow = new ArrayList<ImageIcon>();
        for (int i = 1; i <= 20; i++){
            numeryStolikow.add(new ImageIcon("resources\\nr" + Integer.toString(i) +".png"));
        }

    }


    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);


        for ( Coordinates c : wspolrzedneNaMapie ) {
            g.drawImage(selectIcon(control.getObjectId(c)).getImage(), calculateWidthPosition(c.getColumn()), calculateHeightPosition(c.getRow()), szerokoscPola, wysokoscPola, null);
            int nr = control.getTableNumber(c);

            if (nr>=1) {
                g.drawImage(numeryStolikow.get(nr - 1).getImage(), calculateWidthPosition(c.getColumn()), calculateHeightPosition(c.getRow()), szerokoscPola, wysokoscPola, null);
            }

        }

        g.drawImage(waiter.getImage(), waiterXpos, waiterYpos, szerokoscPola, wysokoscPola, null);


    }

    public int calculateHeightForIcon(){
        return szerokosc * 5 / 8 / 21;
        //szerokosc - szerokosc ekranu
        //5/8 - taka czesc pola ma ma panel mapy
        // /21, bo mamy 21 obiektow w jednym wierszu mapy
    }

    /*public int calculateWidthForIcon(){
        return wysokosc * 14 / 15 / 17 ; // * 6 / 8 / 21;
    }*/

    public int calculateHeightPosition(int column){
        return column * wysokoscPola ;//+ (wysokosc * 14 / 15 - 17 * wysokoscPola) /3;
    }

    public int calculateWidthPosition(int row){
        return row * szerokoscPola  ;// + (szerokosc * 6 / 8 - szerokoscPola * 21) / 2;
    }

    private ImageIcon selectIcon(int number){
        switch (number) {
            /*
            public static final int FREE_FIELD = 0;
            public static final int TABLE = 1;
            public static final int GREEN_CHAIR = 2;
            public static final int RED_CHAIR = 3;
            public static final int CHAIR = 4;
            */
            //chair - jaki to obrazek?
            case 0: return floor;
            case 1: return table;
            case 2: return green;
            case 3: return red;
            case 4: return green;
            default: return floor;

        }
    }

    public void onMove(List<Coordinates> path) {
        //implementacja ruchu
        for (Coordinates c : path){
            waiterCoordinates = c;
            System.out.println("Coordinates: Row = " + c.getRow() + " Column = " + c.getColumn());
            play();
        }
    }

    private void play()
    {
            int bx = calculateWidthPosition(waiterCoordinates.getColumn());
            int by = calculateHeightPosition(waiterCoordinates.getRow());


            if ( waiterXpos < bx ) { waiterXpos = waiterXpos + krok; }
            if ( waiterXpos > bx ) { waiterXpos = waiterXpos - krok; }
            if ( waiterYpos < by ) { waiterYpos = waiterYpos + krok; }
            if ( waiterYpos > by ) { waiterYpos = waiterYpos - krok; }

            System.out.println("Pozycja kelnera: " + waiterXpos + " " + waiterYpos);
            try
            {
                Thread.sleep(100); //ustawianie predkosci ruchu bohatera
                repaint();
                System.out.println("Przemalowa³em");
            }
            catch (InterruptedException e)
            {
                System.out.print("przerwano");
            }

      //      if ( waiterXpos == bx && waiterYpos == by) { this.stop(); }

        /*while(runner != null){

        }
        if (runner==null)
        {
            runner=new Thread(this);
            runner.start();
        }
        */
    }
/*
    void stop()
    {
        if (runner!=null)
        {
            runner=null;
        }
    }
*/
/*
    public void run() {
        Thread ten=Thread.currentThread();
        while (runner==ten)
        {
            int bx = calculateWidthPosition(waiterCoordinates.getColumn());
            int by = calculateHeightPosition(waiterCoordinates.getRow());


            if ( waiterXpos < bx ) { waiterXpos = waiterXpos + krok; }
            if ( waiterXpos > bx ) { waiterXpos = waiterXpos - krok; }
            if ( waiterYpos < by ) { waiterYpos = waiterYpos + krok; }
            if ( waiterYpos > by ) { waiterYpos = waiterYpos - krok; }


            try
            {
                Thread.sleep(100); //ustawianie predkosci ruchu bohatera
                repaint();
            }
            catch (InterruptedException e)
            {
                System.out.print("przerwano");
            }

            if ( waiterXpos == bx && waiterYpos == by) { this.stop(); }
        }
*/

    }
