package com.design.islamic;

import com.design.islamic.model.tiles.svg.SvgFactory;
import com.google.common.base.Function;
import com.google.common.collect.*;
import com.jamesmurty.utils.XMLBuilder;

import java.awt.geom.Point2D;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static com.design.islamic.GenericTools.*;
import static com.design.islamic.model.Centre.newCentre;
import static com.design.islamic.model.tiles.svg.SvgFactory.*;
import static com.google.common.collect.Iterables.concat;
import static com.google.common.collect.Iterables.retainAll;
import static com.google.common.collect.Iterables.transform;
import static java.lang.Math.*;
import static java.lang.String.format;

public final class Patterns {

    public final static int HEX_N = 6;

    public final static double HEX_PHI = (2.0 * PI) / HEX_N;
    public final static double HEX_DIST = calcDist(HEX_PHI);
    public final static double HEX_DIST_NEW_CENTRE = 2.0 * HEX_DIST;
    public final static double HEX_DIST_1 = HEX_DIST_NEW_CENTRE - 1;

    public static List<Point2D> hexPoints = computePoints(HEX_N, HEX_PHI);
    public static List<Point2D> hexPointsAlt = computePointsAlt(HEX_N, HEX_PHI);


    private Patterns() {

    }


    private static List<Point2D> computePoints(int n, double phi) {

        ImmutableList.Builder<Point2D> posBuilder = ImmutableList.builder();
        for (int k = 0; k < n; k++) {
            posBuilder.add(newCentre(
                    cos(phi * k),
                    sin(phi * k)
            ));
        }
        return posBuilder.build();
    }


    private static List<Point2D> computePointsAlt(int n, double phi) {

        ImmutableList.Builder<Point2D> posBuilder = ImmutableList.builder();
        for (int k = 0; k < n; k++) {
            posBuilder.add(newCentre(
                    cos(phi * (k + 0.5)),
                    sin(phi * (k + 0.5))
            ));
        }
        return posBuilder.build();
    }

    public static Set<Point2D> edgesFromCentres(Iterable<Point2D> centres, double r) {

        ImmutableSet.Builder<Point2D> builder = ImmutableSet.builder();

        builder.addAll(calculateHexEdges(centres, r));
        builder.addAll(centres);

        return builder.build();

    }

    public static Set<Point2D> calculateHexEdges(Iterable<Point2D> centres, double r) {
        ImmutableSet.Builder<Point2D> builder = ImmutableSet.builder();

        for (Point2D centre : centres) {
            builder.addAll(calculateHexEdges(centre, r));
        }

        return builder.build();
    }

    public static List<Point2D> calculateHexEdges(Point2D centre, double r) {
        List<Point2D> edges = clonePoints(Patterns.hexPoints);
        scalePoints(edges, r);
        translatePoints(edges, centre);

        return edges;

    }

    public static Set<Point2D> calculateNewCellCentres(Point2D centre, double r, int level) {
        return calculateNewCellCentres(calculateNewCellCentres(centre, r), r, level);
    }

    private static Set<Point2D> calculateNewCellCentres(Iterable<Point2D> centres, double r, int level) {

        if (level == 1) {
            return Sets.newHashSet(centres);
        } else {
            return calculateNewCellCentres(calculateNewCellCentres(centres, r), r, --level);
        }

    }


    private static double calcDist(double phi) {
        return cos(phi / 2.0);
    }


    private static List<Point2D> calculateNewCellCentres(Point2D centre, double r) {
        List<Point2D> newPoints = clonePoints(Patterns.hexPointsAlt);

        scalePoints(newPoints, r * HEX_DIST_NEW_CENTRE);
        translatePoints(newPoints, centre);
        newPoints.add(centre);

        return newPoints;
    }

    private static Set<Point2D> calculateNewCellCentres(Iterable<Point2D> centres, double r) {


        ImmutableSet.Builder<Point2D> builder = ImmutableSet.builder();

        for (Point2D centre : centres) {
            builder.addAll(calculateNewCellCentres(centre, r));
        }


        return builder.build();

    }

    public static XMLBuilder buildHexPattern1(Iterable<Point2D> centres, final double r, int width, int height) {

        final String style = newStyle("yellow", "green", 2, 1, 1);

        List<XMLBuilder> hexagons = ImmutableList.copyOf(transform(centres, new Function<Point2D, XMLBuilder>() {
            @Override
            public XMLBuilder apply(Point2D centre) {
                return newHexagon(centre, r, style);
            }
        }));

        return buildSvg(width, height,
                concat(
                        hexagons
//                        , highlightPoints(centres)
                )
        );
    }


    public static XMLBuilder buildHexPattern2(Iterable<Point2D> centres, final double r, int width, int height) {

        final String style = newStyle("yellow", "green", 1, 1, 0);

        List<XMLBuilder> hexagons = ImmutableList.copyOf(transform(centres, new Function<Point2D, XMLBuilder>() {
            @Override
            public XMLBuilder apply(Point2D centre) {
                return newHexagon(centre, r, style);
            }
        }));


        final String style2 = newStyle("blue", "green", 1, 0, 1);

        List<XMLBuilder> circles = ImmutableList.copyOf(transform(edgesFromCentres(centres, r), new Function<Point2D, XMLBuilder>() {
            @Override
            public XMLBuilder apply(Point2D edge) {
                return SvgFactory.newCircle(edge, r, style2);
            }
        }));

        return buildSvg(width, height,
                concat(
                        hexagons
                        , circles
                )
        );
    }


    public static XMLBuilder buildHexPattern3(Iterable<Point2D> centres, final double r, int width, int height) {

        final String style = newStyle("blue", "blue", 1, 1, 1);

        List<XMLBuilder> hexagons = ImmutableList.copyOf(transform(centres, new Function<Point2D, XMLBuilder>() {
            @Override
            public XMLBuilder apply(Point2D centre) {
                return newHexagon(centre, r, style);
            }
        }));

        final String style2 = newStyle("yellow", "yellow", 1, 1, 1);
        final String style3 = newStyle("yellow", "yellow", 1, 1, 1);

        List<XMLBuilder> layer2 = ImmutableList.copyOf(transform(centres, new Function<Point2D, XMLBuilder>() {
            int index = 0;

            @Override
            public XMLBuilder apply(Point2D centre) {
                String style = index % 2 == 0 ? style2 : style3;
                index++;
                return newHexTile1(centre, r, style);
            }
        }));


        return buildSvg(width, height,
                concat(
                        hexagons,
                        layer2
//                        , highlightPoints(centres)
                )
        );
    }


    public static XMLBuilder newHexTile1(Point2D centre, double r, String style) {
        List<Point2D> edges = Patterns.calculateHexEdges(centre, r);


//        final double d1 = r * cos(Patterns.HEX_PHI / 2.0);
//        final double d2 = d1 - (r - d1);

        List<Point2D> edgesAlt = clonePoints(Patterns.hexPointsAlt);

        scalePoints(edgesAlt, r * HEX_DIST_1);
        translatePoints(edgesAlt, centre);


        ImmutableList.Builder<Point2D> points = ImmutableList.builder();

        for (int i = 0; i < edges.size(); i++) {
            points.add(edges.get(i));
            points.add(edgesAlt.get(i));
        }

        return newPolygon(points.build(), style);

    }

    public static List<XMLBuilder> newHexTile1_2(Point2D centre, double r, String style) {


        List<Point2D> edges = Patterns.calculateHexEdges(centre, r);

        List<Point2D> edgesAlt = clonePoints(Patterns.hexPointsAlt);

        scalePoints(edgesAlt, r * HEX_DIST_1);
        translatePoints(edgesAlt, edges.get(0));

        Point2D point1 = clonePoint(edgesAlt.get(3));
        Point2D point4 = clonePoint(edgesAlt.get(2));

        edgesAlt = clonePoints(Patterns.hexPointsAlt);

        scalePoints(edgesAlt, r * HEX_DIST_1);
        translatePoints(edgesAlt, edges.get(3));
        Point2D point2 = clonePoint(edgesAlt.get(5));
        Point2D point3 = clonePoint(edgesAlt.get(0));

        Point2D edgeUpL = clonePoint(edges.get(4));
        Point2D edgeUpR = clonePoint(edges.get(5));

        Point2D edgeDownL = clonePoint(edges.get(2));
        Point2D edgeDownR = clonePoint(edges.get(1));


        List<Point2D> groupA = Arrays.asList(edgeUpL, point2, clonePoint(centre), point1, edgeUpR);
        List<Point2D> groupB = Arrays.asList(edgeDownL, point3, clonePoint(centre), point4, edgeDownR);



        return Lists.newArrayList(newPolygon(groupA, style), newPolygon(groupB, style));



//        return Arrays.asList(point1, point2, point3, point4, edgeUpL, edgeUpR, edgeDownL, edgeDownR);

    }


    public static XMLBuilder newHexagon(Point2D centre, double r, String style) {
        List<Point2D> edges = Patterns.calculateHexEdges(centre, r);


        return newPolygon(edges, style);


    }


}
