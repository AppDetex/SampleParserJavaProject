package com.appdetex.sampleparserjavaproject.domain;

/*
json example:
{
    "title": "Minecraft",
    "description": "Explore infinite worlds and build everything from the simplest of homes to the grandest of castles. Play in creative mode with unlimited resources or mine deep into the world in survival mode, crafting weapons and armor to fend off dangerous mobs. Create, explore and survive alone or with friends on mobile devices or Windows 10.",
    "publisher": "Mojang",
    "price": "$7.49",
    "rating": 4.6
}
 */
public class ScraperResponse {
    public String title;
    public String description;
    public String publisher;
    public String price;
    public String rating;
}
