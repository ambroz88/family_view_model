package cz.ambrogenea.familyvision.model.dto.parsing;

import cz.ambrogenea.familyvision.model.dto.tree.TreeModel;

import java.io.InputStream;

/**
 *
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public class DocumentInputs {

    private TreeModel treeModel;
    private int generationsShown;
    private InputStream imageStream;

    public TreeModel getTreeModel() {
        return treeModel;
    }

    public void setTreeModel(TreeModel treeModel) {
        this.treeModel = treeModel;
    }

    public int getGenerationsShown() {
        return generationsShown;
    }

    public void setGenerationsShown(int generationsShown) {
        this.generationsShown = generationsShown;
    }

    public InputStream getImageStream() {
        return imageStream;
    }

    public void setImageStream(InputStream imageStream) {
        this.imageStream = imageStream;
    }

}
