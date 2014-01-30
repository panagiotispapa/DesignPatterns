package com.design.islamic.model;

import java.awt.geom.Point2D;

public class Centre extends Point2D.Double {

    public static Point2D newCentre(double x, double y) {
        return new Centre(x, y);
    }

    private Centre(double x, double y) {
        super(x, y);
    }

    @Override
    public int hashCode() {

        return (int) (x + y);

//        return HashCodeBuilder.reflectionHashCode(this);

    }

    @Override
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        } else {
            //            System.out.println("distance " + distance);

            return ((Point2D) other).distance(this) < 1.0;
        }

    }
}
