package com.appdetex.sampleparserjavaproject.googlePlay;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

class AppParserTest {
    private final AppParser appParser = new AppParser();

    @ParameterizedTest
    @EnumSource(TestValue.class)
    void testParse(TestValue testValue) {
        assertEquals(testValue.expectedValue, appParser.parse(getInput(testValue.filename)));
    }

    enum TestValue {
        MINECRAFT("com.mojang.minecraftpe.html",
                new AppData("Minecraft",
                        "Explore infinite worlds and build everything from the simplest of homes to the grandest of castles. Play in creative mode with unlimited resources or mine deep into the world in survival mode, crafting weapons and armor to fend off dangerous mobs. Create, explore and survive alone or with friends on mobile devices or Windows 10.",
                        "Mojang",
                        "$6.99",
                        4.5f)),
        PIXEL_DUNGEON("com.watabou.pixeldungeon.html",
                new AppData("Pixel Dungeon",
                        "Pixel Dungeon is a traditional roguelike * game with pixel-art graphics and simple interface.",
                        "watabou",
                        "0",
                        4.2f)),
        GO("uk.co.aifactory.go.html",
                new AppData("Go",
                        "★ Top Developer (awarded 2013 / 2015) ★", "AI Factory Limited",
                        "$3.99",
                        4.6f));

        String filename;
        AppData expectedValue;

        TestValue(String filename, AppData expectedValue) {
            this.filename = filename;
            this.expectedValue = expectedValue;
        }
    }

    private static String getInput(String filename) {
        try {
            return Resources.toString(Resources.getResource(filename), Charsets.UTF_8);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
