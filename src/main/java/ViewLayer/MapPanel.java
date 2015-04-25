package ViewLayer;

import bohonos.demski.gorska.limiszewska.mieldzioc.logicalLayer.Control;
import bohonos.demski.gorska.limiszewska.mieldzioc.logicalLayer.Coordinates;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.*;

/**
 * Created by Agnieszka on 2015-04-21.
 */

//Panel odpowiedzialny za wyœwietlenie mapy

public class MapPanel extends JPanel {

    int szerokosc;
    int wysokosc;
    int szerokoscPola;
    int wysokoscPola;
    Control control;
    java.util.List<Coordinates> wspolrzedneNaMapie;

    public MapPanel(int szerokosc, int wysokosc){

        this.szerokosc = szerokosc;
        this.wysokosc = wysokosc;

        szerokoscPola = calculateHeightForIcon();
        wysokoscPola = calculateHeightForIcon();


        this.setLayout(null);

        //wykorzystanie warstwy logicznej
        control = Control.getInstance();
        try {
            control.prepareMap();
            //wspolrzedneNaMapie =  control.getAllTablesCoordinates();


        } catch (IOException e) {
            e.printStackTrace();
        }

        this.setBackground(Color.white);
    }


    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        ImageIcon table = new ImageIcon("resources\\table.png");
        ImageIcon floor = new ImageIcon("resources\\floor.png");
        ImageIcon green = new ImageIcon("resources\\green.png");
        ImageIcon grey = new ImageIcon("resources\\grey.png");
        ImageIcon red = new ImageIcon("resources\\red.png");

        g.drawImage(table.getImage(), 0, 0, calculateWidthForIcon(), calculateHeightForIcon(), null);
        //przesuwanie : w prawo, w dol

        /*for ( Coordinates c : wspolrzedneNaMapie ) {

            //wrog.paintIcon(this, g, obliczX(p.podajPolozenieXPostaci()), obliczY(p.podajPolozenieYPostaci()));
            g.drawImage(wrog.getImage(), obliczX(p.podajPolozenieXPostaci()), obliczY(p.podajPolozenieYPostaci()), pXX, pYY, null);
        }

        */

    }

    public int calculateHeightForIcon(){
        return szerokosc * 6 / 8 / 21;
        //szerokosc - szerokosc ekranu
        //6/8 - taka czesc pola ma ma panel mapy
        // /21, bo mamy 21 obiektow w jednym wierszu mapy
    }

    public int calculateWidthForIcon(){
        return szerokosc * 6 / 8 / 21;
        //szerokosc - szerokosc ekranu
        //6/8 - taka czesc pola ma ma panel mapy
        // /21, bo mamy 21 obiektow w jednym wierszu mapyreturn 0;
    }

    public int calculateHeightPosition(int column){
        return column * wysokoscPola;
    }

    public int calculateWidthPosition(int row){
        return row * szerokoscPola;
    }
}
