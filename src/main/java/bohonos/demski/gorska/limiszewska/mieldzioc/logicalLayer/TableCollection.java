package bohonos.demski.gorska.limiszewska.mieldzioc.logicalLayer;

import java.util.ArrayList;

/**
 * Created by Marta Górska.
 */
public class TableCollection {

    private ArrayList<Table> table_collect;

    public TableCollection () {
        this.table_collect = new ArrayList<Table>();
    }

    //dodanie nowego stołu na koniec kolekcji
    public ArrayList<Table> addTable ( Table table ) {
        this.table_collect.add(table);
        return this.table_collect;
    }

    public Table getTable ( Integer id ) {
        return this.table_collect.get(id);
    }

    //zwroc liste stolików
    public ArrayList<Table> getAllTable() {
        return this.table_collect;
    }

}

