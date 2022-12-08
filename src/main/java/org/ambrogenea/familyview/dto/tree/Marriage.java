package org.ambrogenea.familyview.dto.tree;

import org.ambrogenea.familyview.enums.LabelType;

public class Marriage {

    private final Position position;
    private final String date;
    private final LabelType labelType;

    public Marriage(Position position, String date, LabelType labelType) {
        this.position = position;
        this.date = date;
        this.labelType = labelType;
    }

    public String getDate() {
        return date;
    }

    public Position getPosition() {
        return position;
    }

    public LabelType getLabelType() {
        return labelType;
    }
}
