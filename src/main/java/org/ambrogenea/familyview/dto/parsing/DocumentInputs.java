package org.ambrogenea.familyview.dto.parsing;

import java.io.InputStream;

import org.ambrogenea.familyview.dto.tree.TreeModel;

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
