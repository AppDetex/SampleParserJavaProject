package com.appdetex.sampleparserjavaproject;

import java.util.Objects;

public class PlayStoreApp {
    private final String title;

    public PlayStoreApp(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlayStoreApp that = (PlayStoreApp) o;
        return Objects.equals(title, that.title);
    }

    @Override
    public int hashCode() {

        return Objects.hash(title);
    }

    @Override
    public String toString() {
        return "PlayStoreApp{" +
                "title='" + title + '\'' +
                '}';
    }
}
