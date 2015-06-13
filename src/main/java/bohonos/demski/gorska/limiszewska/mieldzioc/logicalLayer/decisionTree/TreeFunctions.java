package bohonos.demski.gorska.limiszewska.mieldzioc.logicalLayer.decisionTree;

import java.io.IOException;

/**
 * Created by Agnieszka on 2015-06-06.
 */
public interface TreeFunctions {

    public boolean question() throws IOException;
    public void visitElem(Tree tree) throws IOException;
    public void yes(Tree tree);
    public void no(Tree tree);
}
