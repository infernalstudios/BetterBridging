package org.infernalstudios.betterbridging.access;

public interface LivingEntityAccess {

    int getShrinkCount();

    void setShrinkCount(int count);

    static LivingEntityAccess get(Object object) {
        return (LivingEntityAccess) object;
    }
}

