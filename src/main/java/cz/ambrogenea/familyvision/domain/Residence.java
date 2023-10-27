package cz.ambrogenea.familyvision.domain;

/**
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
        this.city = city;
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
