package com.appdetex.sampleparserjavaproject.domain;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ItemPropertiesTest {

	private ItemProperties ip;
	
	@Before
	public void setupItemProperties() {
		ip = new ItemProperties("Title", "Publisher", "Description", "0", 0.0);
	}
	
	@After
	public void cleanup() {
		ip = null;
	}
	
	@Test
	public void testGetPrice() {
		assertTrue(ip.getPrice().equals("free"));
	}

}
