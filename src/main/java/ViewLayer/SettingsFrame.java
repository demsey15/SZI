package ViewLayer;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Agnieszka on 2015-04-21.
 */

//Panel z wyborem algorytmów

public class SettingsFrame extends JPanel {

    private JCheckBox algorytmAdama, algorytmAndrzeja, algorytmDominika, algorytmMarty, algorytmAgnieszki;
    private JButton start;

    public SettingsFrame(){

        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        algorytmAdama = new JCheckBox("Algorytm Adama");
        algorytmAndrzeja = new JCheckBox("Algorytm Andrzeja");
        algorytmDominika = new JCheckBox("Algorytm Dominika");
        algorytmMarty = new JCheckBox("Algorytm Marty");
        algorytmAgnieszki = new JCheckBox("Algorytm Agnieszki");

        start = new JButton("START");

        add(algorytmAdama);
        add(algorytmAndrzeja);
        add(algorytmDominika);
        add(algorytmMarty);
        add(algorytmAgnieszki);

        add(start);

    }
}
