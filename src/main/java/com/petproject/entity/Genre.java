package com.petproject.entity;

public enum Genre {
    ROMANCE("Romance"),
    DETECTIVE("Detective"),
    FANTASY("Fantasy"),
    SCIENCE_FICTION("Science Fiction"),
    SELF_IMPROVEMENT("Self Improvement"),
    CLASSICS("Classics"),
    SCIENCE_AND_EDUCATION("Science and Education");

    private final String displayName;

    Genre(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    /**
     * Returns the genre name as a string.
     */
    public static String getDisplayNameForGenre(String genre) {
        try {
            return Genre.valueOf(genre.toUpperCase()).getDisplayName();
        } catch (IllegalArgumentException e) {
            return genre;
        }
    }
}
