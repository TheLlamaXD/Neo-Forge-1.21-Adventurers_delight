package net.dongurs.delightfull.entity.client.koi;


import java.util.Arrays;
import java.util.Comparator;

public enum KoiVariant {
    DEFAULT(0),
    ALBINO(1),
    DARK(2),
    DARK_FADE(3);



    private static final KoiVariant[] BY_ID = Arrays.stream(values()).sorted(
            Comparator.comparingInt(KoiVariant::getId)).toArray(KoiVariant[]::new);

    private final int id;

    KoiVariant(int id){
        this.id = id;

    }


    public int getId() {
        return id;
    }

    public static KoiVariant byId(int id){
        return BY_ID[id % BY_ID.length];
    }
}
