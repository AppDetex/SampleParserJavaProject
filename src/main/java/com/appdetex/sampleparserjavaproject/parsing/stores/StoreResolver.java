package com.appdetex.sampleparserjavaproject.parsing.stores;

import com.appdetex.sampleparserjavaproject.config.StoreLocationConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

@Component
@RequiredArgsConstructor
public class StoreResolver {

    private final StoreLocationConfig storeLocations;

    public StoreEnum resolveStore(String websiteUrl) {
        UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl(websiteUrl).build();

        if(uriComponents.getHost().equals(storeLocations.getGooglePlayLocation())) {
            return StoreEnum.GOOGLE_PLAY;
        }

        if(uriComponents.getHost().equals(storeLocations.getAppleLocation())) {
            return StoreEnum.APPLE;
        }

        throw new NotImplementedException();
    }
}
