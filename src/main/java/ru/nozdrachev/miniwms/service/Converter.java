package ru.nozdrachev.miniwms.service;

import java.math.BigDecimal;

public enum Converter {

    apple(UnitOfMeasurement.KILOGRAM, 1, 0.9, 10, 30,
            2.7, 4.5, 9, 50),
    milk(UnitOfMeasurement.LITRE, 1.1, 1, 11, 33,
            3, 5, 10, 55),
    banana(UnitOfMeasurement.KILOGRAM, 1, 0.75, 10, 30,
            2.25, 3.75, 7.5, 50),
    juice(UnitOfMeasurement.LITRE, 1.4, 1, 14, 42,
            3, 5, 10, 70);


    private final UnitOfMeasurement base;
    private final double indexFromKilogram;
    private final double indexFromLittre;
    private final double indexFromBox10Kg;
    private final double indexFromBox30Kg;
    private final double indexFromBottle3Littre;
    private final double indexFromBottle5Littre;
    private final double indexFromBottle10Littre;
    private final double indexFromBag;

    Converter(UnitOfMeasurement base, double indexFromKilogram, double indexFromLittre,
              double indexFromBox10Kg, double indexFromBox30Kg, double indexFromBottle3Littre,
              double indexFromBottle5Littre, double indexFromBottle10Littre, double indexFromBag) {
        this.base = base;
        this.indexFromKilogram = indexFromKilogram;
        this.indexFromLittre = indexFromLittre;
        this.indexFromBox10Kg = indexFromBox10Kg;
        this.indexFromBox30Kg = indexFromBox30Kg;
        this.indexFromBottle3Littre = indexFromBottle3Littre;
        this.indexFromBottle5Littre = indexFromBottle5Littre;
        this.indexFromBottle10Littre = indexFromBottle10Littre;
        this.indexFromBag = indexFromBag;
    }

    public static BigDecimal returnsBase(UnitOfMeasurement unit, BigDecimal count, String name) {
        Converter value = Converter.valueOf(name);
        BigDecimal result = new BigDecimal(0);
        switch (unit) {
            case KILOGRAM:
                result = count.multiply(new BigDecimal(value.indexFromKilogram));
                break;
            case LITRE:
                result = count.multiply(new BigDecimal(value.indexFromLittre));
                break;
            case BOX10KG:
                result = count.multiply(new BigDecimal(value.indexFromBox10Kg));
                break;
            case BOX30KG:
                result = count.multiply(new BigDecimal(value.indexFromBox30Kg));
                break;
            case BOTTLE3LITTRE:
                result = count.multiply(new BigDecimal(value.indexFromBottle3Littre));
                break;
            case BOTTLE5LITTRE:
                result = count.multiply(new BigDecimal(value.indexFromBottle5Littre));
                break;
            case BOTTLE10LITTRE:
                result = count.multiply(new BigDecimal(value.indexFromBottle10Littre));
                break;
            case BAG:
                result = count.multiply(new BigDecimal(value.indexFromBag));
                break;
        }
        return result;
    }

}
