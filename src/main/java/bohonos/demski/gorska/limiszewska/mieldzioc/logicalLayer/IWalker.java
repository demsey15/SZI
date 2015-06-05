package bohonos.demski.gorska.limiszewska.mieldzioc.logicalLayer;

import java.util.List;

/**
 * Created by Dominik on 2015-06-05.
 */
public interface IWalker {
    /**
     * Metoda, ktora w odpowieniej kolejnosci przemieszcza kelnera przez wszystkie stoly i zdejmuje
     * potrawy dostarczone do danego stolika z listy zamowionych potraw i listy potraw na tacy kelnera.
     * @param tablesToGoThrow lista numerow stolow, ktore nalezy odwiedzic.
     */
    void goThroughTables(List<Integer> tablesToGoThrow);
}
