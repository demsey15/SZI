package bohonos.demski.gorska.limiszewska.mieldzioc.logicalLayer.decisionTree;

import java.io.IOException;

/**
 * Created by Agnieszka on 2015-06-06.
 */
public interface TreeElemFunction {

    public boolean question() throws IOException;
    public void visitElem(TreeFactory tree) throws IOException;
    public void yes(TreeFactory tree);
    public void no(TreeFactory tree);
}
