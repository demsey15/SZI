package ViewLayer;

import bohonos.demski.gorska.limiszewska.mieldzioc.logicalLayer.IfWaiterGoThread;
import bohonos.demski.gorska.limiszewska.mieldzioc.logicalLayer.OrdersFactory;
import bohonos.demski.gorska.limiszewska.mieldzioc.logicalLayer.decisionTree.CurrentCreatingMeal;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Agnieszka on 2015-04-21.
 */

//Panel z wyborem algorytmów

public class SettingsFrame extends JPanel {

    private JButton start;
    private MapPanel mapPanel;
    private static JRadioButton Andrzej;
    private static JRadioButton Dominik;

    private static ExecutorService threads = Executors.newCachedThreadPool();

    public SettingsFrame(final MapPanel mapPanel){

        this.mapPanel = mapPanel;
        setBackground(Color.white);
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        Andrzej = new JRadioButton("Algorytm Andrzeja");
        Dominik = new JRadioButton("Algorytm Dominika");

        ButtonGroup algorithmsButtons = new ButtonGroup();
        algorithmsButtons.add(Andrzej);
        algorithmsButtons.add(Dominik);

        this.add(Andrzej);
        this.add(Dominik);


        start = new JButton("START");
        /*start.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mapPanel.waiterCoordinates.setColumn(1);
                mapPanel.waiterCoordinates.setRow(1);
                mapPanel.play();
                mapPanel.run();
            }
        });*/ //to powodowalo ze klient sie ruszal po przycisnieciu start, teraz jest juz to niepotrzebne

        start.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (getSelectedAlgorithms().equals("Andrzej") || getSelectedAlgorithms().equals("Dominik")){
                    threads.execute(new OrdersFactory()); //sluzy do losowania zamowien
                    //---------------------------------------------------------------------
                    //takie sobie przyjmuje zalozenie ze mam 5 dzialajacych kucharzy
                    threads.execute(new CurrentCreatingMeal());
                    threads.execute(new CurrentCreatingMeal());
                    threads.execute(new CurrentCreatingMeal());
                    threads.execute(new CurrentCreatingMeal());
                    threads.execute(new CurrentCreatingMeal());
                    //---------------------------------------------------------------------
                    threads.execute(new IfWaiterGoThread());
                }

            }
        });

        add(start);

    }

    public static String getSelectedAlgorithms(){
        if (Andrzej.isSelected()) {
            return "Andrzej";
        }else if (Dominik.isSelected()){
            return "Dominik";
        }else{
            return "Brak";
        }

    }


}
