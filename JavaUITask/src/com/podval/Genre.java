package com.podval;

public enum Genre {
    FANTASY("Fantasy"),
    SCIENCE_FICTION("Science fiction"),
    MANUAL("Manual"),
    ARTISTIC_LITERATURE("Artistic literature"),
    UNKNOWN("Unknown");


    private String value;

    Genre(final String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return this.getValue();
    }

    public static Genre fromString(String name){
        String constantName = name.toUpperCase().replace(" ", "_");
        return Genre.valueOf(constantName);
    }
}
