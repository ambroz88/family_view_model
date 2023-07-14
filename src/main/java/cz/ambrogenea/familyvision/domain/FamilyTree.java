package cz.ambrogenea.familyvision.domain;

import cz.ambrogenea.familyvision.utils.IdGenerator;

/**
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public class FamilyTree {
    private long id;
    private String treeName;

    public FamilyTree(String treeName) {
        this.id = IdGenerator.generate(FamilyTree.class.getSimpleName());
        this.treeName = treeName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTreeName() {
        return treeName;
    }

    public void setTreeName(String treeName) {
        this.treeName = treeName;
    }
}
