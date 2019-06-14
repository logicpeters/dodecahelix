package com.ddxlabs.girgrat.entity.world;

import com.artemis.MdxWorld;
import com.artemis.WorldConfiguration;
import com.artemis.WorldConfigurationBuilder;
import com.ddxlabs.girgrat.entity.system.*;
import org.mini2Dx.core.di.annotation.Autowired;
import org.mini2Dx.core.di.annotation.Singleton;

/**
 * Created on 2/14/2017.
 */
@Singleton
public class MdxWorldBuilder implements WorldBuilder {

    @Autowired
    MoveSphereSystem moveEntitySystem;

    @Autowired
    RenderSpriteSphereSystem renderSystem;

    @Autowired
    RotateSphereSystem rotateSystem;

    @Autowired
    BoundaryBounceSystem boundaryBounceSystem;

    @Autowired
    SphereCollisionSystem entityCollisionSystem;

    public MdxWorld build() {
        WorldConfiguration worldConfiguration = new WorldConfigurationBuilder()
        .with(
            moveEntitySystem,
            renderSystem,
            rotateSystem,
            boundaryBounceSystem,
            entityCollisionSystem
        )
        .build();
        return new MdxWorld(worldConfiguration);
    }
}
