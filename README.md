#Small scraping tool

Small scraping tool for parsing mobile app information from the Google Play app store's webpage.
Accepts an URL for a Google Play app page as a command line parameter, and returns a JSON-formatted string containing relevant data for the app.
The data we're interested in are: app title, first paragraph of the description, publisher name, price, and rating (average review score).


For example, for the app "Minecraft" the scraper will be provided the URL:
https://play.google.com/store/apps/details?id=com.mojang.minecraftpe&hl=en-US
and will output a string looking something like:

    {
        "title": "Minecraft",
        "description": "Explore infinite worlds and build everything from the simplest of homes to the grandest of castles. Play in creative mode with unlimited resources or mine deep into the world in survival mode, crafting weapons and armor to fend off dangerous mobs. Create, explore and survive alone or with friends on mobile devices or Windows 10.",
        "publisher": "Mojang",
        "price": "$7.49",
        "rating": 4.5
    }

## How to use it
To run it, pass the URL delemited with quotes ("). For example:
    "https://play.google.com/store/apps/details?id=com.mojang.minecraftpe&hl=en-US"

