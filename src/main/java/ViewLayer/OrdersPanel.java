package ViewLayer;

import javafx.util.Pair;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.Vector;

/**
 * Created by Agnieszka on 2015-04-21.
 */

//Panel który wyœwietla listê zamówionych potraw wraz z numerem stolika

public class OrdersPanel extends JPanel {

    private JScrollPane scrollPane;
    private JTable orderListTable;
    String [] columnNames = {"Danie", "Nr stolika"};
    DefaultTableModel model;


    private JLabel title;

    public OrdersPanel(String[][] list, String panelTitle){

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        title = new JLabel(panelTitle);
        this.add(title);

        model = new DefaultTableModel(list, columnNames);
        orderListTable = new JTable(model);

        scrollPane = new JScrollPane(orderListTable);
        orderListTable.setFillsViewportHeight(true);

        this.add(scrollPane);


    }
    public void setOrdersList(String[][] list){
        model.setDataVector(list, columnNames);
        model.fireTableDataChanged();

    }


}
