package org.ambrogenea.familyview.domain;

public class Marriage {

    private final Position position;
    private final String date;
    private final int length;

    public Marriage(Position position, String date, int length) {
        this.position = position;
        this.date = date;
        this.length = length;
    }

    public String getDate() {
        return date;
    }

    public Position getPosition() {
        return position;
    }

    public int getLength() {
        return length;
    }
}
