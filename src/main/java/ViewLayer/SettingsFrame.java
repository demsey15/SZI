package ViewLayer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

/**
 * Created by Agnieszka on 2015-04-21.
 */

//Panel z wyborem algorytmów

public class SettingsFrame extends JPanel {

    private JCheckBox algorytmAdama, algorytmAndrzeja, algorytmDominika, algorytmMarty, algorytmAgnieszki;
    private JButton start;
    private MapPanel mapPanel;

    public SettingsFrame(final MapPanel mapPanel){

        this.mapPanel = mapPanel;
        setBackground(Color.white);
        java.util.List<JCheckBox> allCheckBox = new ArrayList();
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        allCheckBox.add(algorytmAdama = new JCheckBox("Algorytm Adama"));
        allCheckBox.add(algorytmAndrzeja = new JCheckBox("Algorytm Andrzeja"));
        allCheckBox.add(algorytmDominika = new JCheckBox("Algorytm Dominika"));
        allCheckBox.add(algorytmMarty = new JCheckBox("Algorytm Marty"));
        allCheckBox.add(algorytmAgnieszki = new JCheckBox("Algorytm Agnieszki"));

        for (JCheckBox chB : allCheckBox){
            chB.setBackground(Color.white);
            add(chB);
        }

        start = new JButton("START");
        start.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mapPanel.waiterCoordinates.setColumn(1);
                mapPanel.waiterCoordinates.setRow(1);
                mapPanel.play();
                mapPanel.run();
            }
        });



        add(start);

    }
}
