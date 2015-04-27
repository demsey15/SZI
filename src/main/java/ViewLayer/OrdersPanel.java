package ViewLayer;

import javafx.util.Pair;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
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

    public OrdersPanel(String[][] list, String panelTitle, int screenWidth, int screenHight){

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setPreferredSize(new Dimension(screenWidth /8 + screenWidth/18, screenHight/2));
        this.setBackground(Color.white);

        title = new JLabel(panelTitle);
        this.add(title);

        model = new DefaultTableModel(list, columnNames);
        orderListTable = new JTable(model);
        orderListTable.setPreferredSize(new Dimension(250,100));
        TableColumnModel columnModel = orderListTable.getColumnModel();
        columnModel.getColumn(0).setWidth(200/*3*screenWidth/8/4*/);
        columnModel.getColumn(1).setWidth(50/*screenWidth/8/4*/);

        scrollPane = new JScrollPane(orderListTable);
        orderListTable.setFillsViewportHeight(true);

        this.add(scrollPane);


    }
    public void setOrdersList(String[][] list){
        model.setDataVector(list, columnNames);
        model.fireTableDataChanged();

    }


}
