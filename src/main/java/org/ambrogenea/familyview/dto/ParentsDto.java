package org.ambrogenea.familyview.dto;

import org.ambrogenea.familyview.dto.tree.Position;

public class ParentsDto {
    private Position husbandPosition;
    private Position wifePosition;
    private int nextHeraldryY;

    public ParentsDto(Position husbandPosition, Position wifePosition, int nextHeraldryY) {
        this.husbandPosition = husbandPosition;
        this.wifePosition = wifePosition;
        this.nextHeraldryY = nextHeraldryY;
    }

    public Position getHusbandPosition() {
        return husbandPosition;
    }

    public void setHusbandPosition(Position husbandPosition) {
        this.husbandPosition = husbandPosition;
    }

    public Position getWifePosition() {
        return wifePosition;
    }

    public void setWifePosition(Position wifePosition) {
        this.wifePosition = wifePosition;
    }

    public int getNextHeraldryY() {
        return nextHeraldryY;
    }

    public void setNextHeraldryY(int nextHeraldryY) {
        this.nextHeraldryY = nextHeraldryY;
    }
}
