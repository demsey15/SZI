package ViewLayer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        algorytmAdama = new JCheckBox("Algorytm Adama");
        algorytmAndrzeja = new JCheckBox("Algorytm Andrzeja");
        algorytmDominika = new JCheckBox("Algorytm Dominika");
        algorytmMarty = new JCheckBox("Algorytm Marty");
        algorytmAgnieszki = new JCheckBox("Algorytm Agnieszki");

        start = new JButton("START");
        start.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mapPanel.waiterCoordinates.setColumn(1);
                mapPanel.waiterCoordinates.setRow(1);
                mapPanel.play();
                mapPanel.run();
            }
        });

        add(algorytmAdama);
        add(algorytmAndrzeja);
        add(algorytmDominika);
        add(algorytmMarty);
        add(algorytmAgnieszki);

        add(start);

    }
}
