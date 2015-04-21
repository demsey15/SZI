package ViewLayer;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 * Created by Agnieszka on 2015-04-21.
 */

//Panel odpowiedzialny za wyœwietlanie Menu restauracji

public class MenuPanel extends JPanel {
    private JScrollPane scrollPane;
    private JTable orderListTable;
    String [] columnNames = {"Danie"};
    DefaultTableModel model;


    private JLabel title;

    public MenuPanel(String[][] list){

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        title = new JLabel("MENU:");
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
