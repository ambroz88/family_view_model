package cz.ambrogenea.familyvision.domain;

import cz.ambrogenea.familyvision.utils.IdGenerator;

import java.util.Objects;

public class City {
    private final Long id;
    private final String originalName;
    private String name;
    private String alternativeName;
    private String shortName;
    private String district;
    private String region;
    private String country;

    public City(String originalName) {
        this.id = IdGenerator.generate(City.class.getSimpleName());
        this.originalName = originalName;
        this.alternativeName = "";
        this.shortName = "";
        String[] nameParts = originalName.split(",");
        if (nameParts.length == 4) {
            name = nameParts[0];
            district = nameParts[1];
            region = nameParts[2];
            country = nameParts[3];
        } else if (nameParts.length == 3) {
            name = nameParts[0];
            district = "";
            region = nameParts[1];
            country = nameParts[2];
        } else if (nameParts.length == 2) {
            name = nameParts[0];
            district = nameParts[1];
            region = "";
            country = "";
        } else if (nameParts.length == 1) {
            name = nameParts[0];
            district = "";
            region = "";
            country = "";
        }
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOriginalName() {
        return originalName;
    }

    public String getAlternativeName() {
        return alternativeName;
    }

    public void setAlternativeName(String alternativeName) {
        this.alternativeName = alternativeName;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public boolean contains(String name) {
        if (name == null) {
            return false;
        }
        return Objects.equals(name, this.name) ||
                Objects.equals(name, this.originalName) ||
                Objects.equals(name, this.shortName) ||
                Objects.equals(name, this.alternativeName) ||
                Objects.equals(name, this.district) ||
                Objects.equals(name, this.region)
                ;
    }

    public boolean isTheSame(String originalName) {
        String[] nameParts = originalName.split(",");
        String name = nameParts[0];
        String district = "";
        String region = "";
        if (nameParts.length == 4) {
            district = nameParts[1];
            region = nameParts[2];
        } else if (nameParts.length == 3) {
            district = "";
            region = nameParts[1];
        } else if (nameParts.length == 2) {
            district = nameParts[1];
            region = "";
        } else if (nameParts.length == 1) {
            district = "";
            region = "";
        }
        return ((Objects.equals(originalName, this.originalName))
                || (Objects.equals(name, this.name) &&
                (Objects.equals(district, this.district) || Objects.equals(region, this.region)))
                || Objects.equals(name, this.alternativeName));
    }
}
