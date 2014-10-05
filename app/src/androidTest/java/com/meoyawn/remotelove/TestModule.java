package com.meoyawn.remotelove;

import com.meoyawn.remotelove.api.model.RecentTracksHolderTest;
import dagger.Module;

/**
 * Created by adelnizamutdinov on 10/5/14
 */
@Module(addsTo = LoveModule.class,
        injects = {
            RecentTracksHolderTest.class
        })
public class TestModule {
}
