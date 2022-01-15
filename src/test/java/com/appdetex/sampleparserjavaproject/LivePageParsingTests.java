package com.appdetex.sampleparserjavaproject;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/*
	These tests fetch live data.

	Failures can arise from page layout changes or data changes.

	In the case of page layout changes, all tests will likely fail. For data changes, it is likely that
	pairs of tests will fail.
 */
@Tag("live")
class LivePageParsingTests {

	@Test
	void test_live_with_sample_url() {
		var p = PageScraper.fromUrl("https://play.google.com/store/apps/details?id=com.mojang.minecraftpe&hl=en-US");
		var ad = p.scrape();

		assertEquals("Minecraft", ad.getTitle());
		assertEquals("$7.49", ad.getPrice());
		assertEquals("Explore infinite worlds and build everything from the simplest of homes to the grandest of castles. Play in creative mode with unlimited resources or mine deep into the world in survival mode, crafting weapons and armor to fend off dangerous mobs. Create, explore and survive alone or with friends on mobile devices or Windows 10.", ad.getDescription());
		assertEquals(4.6,ad.getRating());
		assertEquals("Mojang", ad.getPublisher());
	}

	@Test
	void test_live_with_sample_url_but_in_french() {
		var p = PageScraper.fromUrl("https://play.google.com/store/apps/details?id=com.mojang.minecraftpe&hl=fr-FR");
		var ad = p.scrape();

		assertEquals("Minecraft", ad.getTitle());
		assertTrue(ad.getPrice().startsWith("7,49"));
		assertTrue(ad.getPrice().endsWith("$US"));
		assertEquals("Explorez des mondes infinis et créez sans limites, d'un abri de fortune au plus fabuleux des châteaux. Jouez en mode Créatif avec des ressources illimitées, ou creusez jusqu'au centre de la Terre pour fabriquer armes et armures afin de vaincre les dangereuses créatures du mode Survie. Craftez, créez et explorez seul ou entre amis, sur mobile comme sur Windows 10.", ad.getDescription());
		assertEquals(4.6,ad.getRating());
		assertEquals("Mojang", ad.getPublisher());
	}

	@Test
	void test_live_with_free_app() {
		var p = PageScraper.fromUrl("https://play.google.com/store/apps/details?id=com.sandboxol.indiegame.bedwar&hl=en-US");
		var ad = p.scrape();

		assertEquals("Bed Wars", ad.getTitle());
		assertEquals("0", ad.getPrice());
		assertEquals("Bedwars is a teamwork PVP game, you will be battling your opponents on islands in the sky, protect your bed and try to destroy your opponents' bed to prevent them from respawning, beat all the opponents to win the game!", ad.getDescription());
		assertEquals(4.2,ad.getRating());
		assertEquals("Blockman GO Studio", ad.getPublisher());
	}

	@Test
	void test_live_with_free_app_in_french_auto_translate() {
		var p = PageScraper.fromUrl("https://play.google.com/store/apps/details?id=com.sandboxol.indiegame.bedwar&hl=fr-FR");
		var ad = p.scrape();

		assertEquals("Bed Wars", ad.getTitle());
		assertEquals("0", ad.getPrice());
		assertEquals("Bedwars is a teamwork PVP game, you will be battling your opponents on islands in the sky, protect your bed and try to destroy your opponents' bed to prevent them from respawning, beat all the opponents to win the game!", ad.getDescription());
		assertEquals(4.2,ad.getRating());
		assertEquals("Blockman GO Studio", ad.getPublisher());
	}
}