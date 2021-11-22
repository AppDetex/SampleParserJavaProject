package com.appdetex.sampleparserjavaproject.validation

import com.appdetex.sampleparserjavaproject.model.AppStore.UnknownAppStore

/**
 * Example Unknown App Store url:<br/>
 *
 *  If no recognized app store domain is passed, we will attempt to
 *  do our very best with the little data given by returning an UnknownAppStore.
 *
 * https://some.appstore.com/whatever/path/was/passed?id=some.app.id
 */
internal class UnknownAppStoreUrlValidator(appStore: UnknownAppStore) : AbstractStoreUrlValidator(appStore)
