package com.appdetex.sampleparserjavaproject;

import java.util.Objects;

public class PlayStoreApp {
    private final String title;
    private final String description;

    public PlayStoreApp(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlayStoreApp that = (PlayStoreApp) o;
        return Objects.equals(title, that.title) &&
                Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {

        return Objects.hash(title, description);
    }

    @Override
    public String toString() {
        return "PlayStoreApp{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
