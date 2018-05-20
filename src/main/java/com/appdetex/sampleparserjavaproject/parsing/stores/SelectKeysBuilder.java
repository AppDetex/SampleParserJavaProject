package com.appdetex.sampleparserjavaproject.parsing.stores;

import com.appdetex.sampleparserjavaproject.config.AppleStoreSelectKeys;
import com.appdetex.sampleparserjavaproject.config.GooglePlaySelectKeys;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

@Component
@RequiredArgsConstructor
public class SelectKeysBuilder {

    private final GooglePlaySelectKeys googlePlaySelectKeys;
    private final AppleStoreSelectKeys appleStoreSelectKeys;

    public SelectKeys buildSelectKeys(StoreEnum storeEnum) {
        switch(storeEnum) {
            case GOOGLE_PLAY:
                return googlePlaySelectKeys;
            case APPLE:
                return appleStoreSelectKeys;
            default:
                throw new NotImplementedException();
        }
    }
}
