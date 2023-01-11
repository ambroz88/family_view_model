package cz.ambrogenea.familyvision.domain;

/**
 *
 * @author Jiri Ambroz <ambroz88@seznam.cz>
 */
public class Residence {

    private String city;
    private String date;
    private int number;

    public Residence() {
        initEmpty();
    }

    private void initEmpty() {
        city = "";
        date = "";
        number = -1;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        int lastCommaIndex = city.indexOf(" - ");
        int lastSpaceIndex = city.lastIndexOf(" ");
        if (lastCommaIndex != -1) {
            //removing part of the city e.g Praha - Strahov
            this.city = city.substring(0, lastCommaIndex);
            try {
                this.number = Integer.parseInt(city.substring(lastSpaceIndex + 1));
            } catch (NumberFormatException e) {
            }
        } else if (lastSpaceIndex != -1) {
            this.city = city.substring(0, lastSpaceIndex);
            try {
                this.number = Integer.parseInt(city.substring(lastSpaceIndex + 1));
            } catch (NumberFormatException e) {
                this.city = city;
            }
        } else {
            this.city = city;
        }
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

}
