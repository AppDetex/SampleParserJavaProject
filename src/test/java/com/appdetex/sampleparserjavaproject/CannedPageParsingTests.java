package com.appdetex.sampleparserjavaproject;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

/*
 * These tests use saved output of the urls used for testing. Live data will eventually drift
 * and start failing due to normal changes, especially price and rating.
 *
 * There is duplication across the two test fixtures right now, but I am making the
 * conscious decision to duplicate the test strings because overtime there could be
 * drift that you may or may not want to worry about across the fixtures.
 *
 * When the pages being parsed eventually change, new samples will need to be downloaded
 */
@Tag("canned")
class CannedPageParsingTests {

	@Test
	void test_sample_from_file() {
		var p = PageScraper.fromHtml(getHtmlFromResource("sample.html"));
		var ad = p.scrape();

		assertEquals("Minecraft", ad.getTitle());
		assertEquals("$7.49", ad.getPrice());
		assertEquals("Explore infinite worlds and build everything from the simplest of homes to the grandest of castles. Play in creative mode with unlimited resources or mine deep into the world in survival mode, crafting weapons and armor to fend off dangerous mobs. Create, explore and survive alone or with friends on mobile devices or Windows 10.", ad.getDescription());
		assertEquals(4.6,ad.getRating());
		assertEquals("Mojang", ad.getPublisher());
	}

	@Test
	void test_sample_from_file_but_in_french() {
		var p = PageScraper.fromHtml(getHtmlFromResource("sample_fr.html"));
		var ad = p.scrape();

		assertEquals("Minecraft", ad.getTitle());
		assertTrue(ad.getPrice().startsWith("7,49"));
		assertTrue(ad.getPrice().endsWith("$US"));
		assertEquals("Explorez des mondes infinis et créez sans limites, d'un abri de fortune au plus fabuleux des châteaux. Jouez en mode Créatif avec des ressources illimitées, ou creusez jusqu'au centre de la Terre pour fabriquer armes et armures afin de vaincre les dangereuses créatures du mode Survie. Craftez, créez et explorez seul ou entre amis, sur mobile comme sur Windows 10.", ad.getDescription());
		assertEquals(4.6,ad.getRating());
		assertEquals("Mojang", ad.getPublisher());
	}

	@Test
	void test_free_app_from_file() {
		var p = PageScraper.fromHtml(getHtmlFromResource("free_app.html"));
		var ad = p.scrape();

		assertEquals("Bed Wars", ad.getTitle());
		assertEquals("0", ad.getPrice());
		assertEquals("Bedwars is a teamwork PVP game, you will be battling your opponents on islands in the sky, protect your bed and try to destroy your opponents' bed to prevent them from respawning, beat all the opponents to win the game!", ad.getDescription());
		assertEquals(4.2,ad.getRating());
		assertEquals("Blockman GO Studio", ad.getPublisher());
	}

	@Test
	void test_free_app_from_file_in_french_auto_translate() {
		var p = PageScraper.fromHtml(getHtmlFromResource("free_app_fr.html"));
		var ad = p.scrape();

		assertEquals("Bed Wars", ad.getTitle());
		assertEquals("0", ad.getPrice());
		assertEquals("Bedwars is a teamwork PVP game, you will be battling your opponents on islands in the sky, protect your bed and try to destroy your opponents' bed to prevent them from respawning, beat all the opponents to win the game!", ad.getDescription());
		assertEquals(4.2,ad.getRating());
		assertEquals("Blockman GO Studio", ad.getPublisher());
	}

	private String getHtmlFromResource(String resourcePath) {
		try {
			ClassLoader classLoader = ClassLoader.getSystemClassLoader();
			try (InputStream is = classLoader.getResourceAsStream(resourcePath)) {
				if (is == null) return null;
				try (InputStreamReader isr = new InputStreamReader(is, StandardCharsets.UTF_8);
					 BufferedReader reader = new BufferedReader(isr)) {
					return reader.lines().collect(Collectors.joining(System.lineSeparator()));
				}
			}
		} catch (IOException ioe) {
			fail("Couldn't parse file",ioe);
		}
		fail("Nothing read");
		return null;
	}
}