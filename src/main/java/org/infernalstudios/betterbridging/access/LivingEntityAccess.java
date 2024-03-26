package org.infernalstudios.betterbridging.access;

import net.minecraft.core.Direction;

public interface LivingEntityAccess {

    Direction getDirection();

    void setDirection(Direction direction);

    static LivingEntityAccess get(Object object) {
        return (LivingEntityAccess) object;
    }
}
