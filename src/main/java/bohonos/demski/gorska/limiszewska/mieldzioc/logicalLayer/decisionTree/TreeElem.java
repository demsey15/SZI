package bohonos.demski.gorska.limiszewska.mieldzioc.logicalLayer.decisionTree;

import java.io.IOException;

/**
 * Created by Agnieszka on 2015-06-06.
 */
public abstract class TreeElem implements TreeFunctions{


    public void visitElem(Tree tree) throws IOException {
        if (question()) {
            this.yes(tree);
        }else {
            this.no(tree);
        }
    }
}
