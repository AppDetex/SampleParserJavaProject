package com.appdetex.sampleparserjavaproject.validation

import com.appdetex.sampleparserjavaproject.AppStore.GooglePlayStore

/**
 * Example valid Google Play Store url:<br/>
 *
 * https://play.google.com/store/apps/details?id=com.mojang.minecraftpe&hl=en-US
 */
internal class GooglePlayStoreUrlValidator(appStore: GooglePlayStore) : AbstractStoreUrlValidator(appStore)
