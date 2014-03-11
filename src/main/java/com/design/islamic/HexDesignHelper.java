package com.design.islamic;

import com.design.common.PolygonTools;
import com.design.islamic.model.Tile;
import com.design.islamic.model.hex.*;
import com.google.common.base.Function;

import java.awt.geom.Point2D;
import java.util.List;

import static com.design.common.PolygonTools.*;
import static com.design.common.view.SvgFactory.*;
import static com.design.islamic.GenericTools.concatEdges;
import static com.google.common.collect.Iterables.transform;
import static com.google.common.collect.Lists.newArrayList;
import static java.lang.Math.cos;
import static java.util.Arrays.asList;

public class HexDesignHelper {

    public static String newStarDesign1(final Point2D centre, final double r) {

        StringBuilder builder = new StringBuilder();

        List<Point2D> edges = cloneAndTranslateScalePoints(centre, r, hexPoints);

        final List<Point2D> outsideCentres = cloneAndTranslateScalePoints(centre, r * HEX_DIST_NEW_CENTRE, hexPointsAlt);

        List<Point2D> pointsLayerMiddle = cloneAndTranslateScalePoints(centre, 0.5 * r, hexPoints);

        List<Point2D> edgesAltLayer1 = cloneAndTranslateScalePoints(centre, r * HEX_DIST_OUTER_CIRCLE, hexPointsAlt);

        List<Point2D> edgesAltLayer2 = cloneAndTranslateScalePoints(centre, r * HEX_DIST_DIAGONAL, hexPointsAlt);

        List<Point2D> edgesAltLayer3 = cloneAndTranslateScalePoints(centre, r * HEX_DIST3, hexPointsAlt);

        String black = newStyle("black", 2, 1);
        String blue = newStyle("blue", 1, 1);
        final String gray = newStyle("gray", 1, 1);
        final String green = newStyle("green", 1, 1);

        builder.append(newCircle(centre, r, black));
//        builder.add(drawPolygon(edges, blue));
        builder.append(highlightPoints(outsideCentres));
        builder.append(drawPolygon(outsideCentres, gray));

        builder.append(transform(outsideCentres, new Function<Point2D, String>() {
            @Override
            public String apply(Point2D centre) {
                return newCircle(centre, r, gray);
            }
        }));

        builder.append(transform(outsideCentres, new Function<Point2D, String>() {
            @Override
            public String apply(Point2D endPoint) {
                return newPolyline(asList(centre, endPoint), gray);
            }
        }));

//

        builder.append(drawPolylines(generateCombsOfPoints(edges), gray));

        builder.append(drawPolygon(edgesAltLayer1, gray));
        builder.append(drawPolygon(pointsLayerMiddle, gray));

        builder.append(drawPolygon(newHexStarTile(centre, r, HEX_DIST_OUTER_CIRCLE), green));
        builder.append(drawPolygon(newHexStarTile(centre, r, HEX_DIST_DIAGONAL), green));
        builder.append(drawPolygon(newHexStarTile(centre, r, HEX_DIST3), green));

        builder.append(highlightPoints(edgesAltLayer1));
        builder.append(highlightPoints(edgesAltLayer2));
        builder.append(highlightPoints(edgesAltLayer3));

        return builder.toString();

    }

    public static String newStarDesign2(final Point2D centre, final double r) {

        StringBuilder builder = new StringBuilder();

        final String gray = newStyle(GRAY, 1, 1);
        final String green = newStyle(GREEN, 2, 1);
        final String red = newStyle(RED, 2, 1);

        final double newR = r * cos(HEX_PHI / 2);

        List<Point2D> edges = cloneAndTranslateScalePoints(centre, r, hexPointsAlt);

        List<Point2D> edgesAltLayer2 = cloneAndTranslateScalePoints(centre, r * HEX_DIST_HEIGHT, hexPoints);

        List<List<Point2D>> layer2 = generateCombsOfPoints(edgesAltLayer2);

        List<List<Point2D>> layer3 = asList(
                asList(edges.get(0), edges.get(3)),
                asList(edges.get(1), edges.get(4)),
                asList(edges.get(2), edges.get(5))
        );

        List<Point2D> edgesAltLayer4 = cloneAndTranslateScalePoints(centre, newR * HEX_DIST_DIAGONAL, hexPointsAlt);

        List<Point2D> edgesLayer5 = concatEdges(edgesAltLayer2, edgesAltLayer4);

        builder.append(drawPolygon(edges, gray));
        builder.append(drawPolylines(layer2, gray));
        builder.append(drawPolylines(layer3, gray));
        builder.append(drawPolygon(edgesLayer5, green));

        builder.append(highlightPoints(edges));
        builder.append(highlightPoints(edgesAltLayer2));
        builder.append(highlightPoints(edgesAltLayer4));

//        out.add(drawPolygon(newHexStarTileRotated(centre, r, HEX_DIST_DIAGONAL), red));

        return builder.toString();

    }

    public static String newStarDesign3(final Point2D centre, final double r) {

        Tile3 tile3 = new Tile3(centre, r);

        StringBuilder builder = new StringBuilder();

        final String gray = newStyle(GRAY, 1, 1);
        final String blue = newStyle(BLUE, 1, 1);
        final String green = newStyle(GREEN, 2, 1);
        final String red = newStyle(RED, 2, 1);

        final double newR = r * HEX_DIST_EQ1;
        double newHeight = HEX_DIST_EQ1 * HEX_DIST_HEIGHT;
        List<Point2D> edges = newHexagonRot(centre, r);

        List<Point2D> innerEdges = newHexagonRot(centre, newR);

//        List<Point2D> insideStar = buildStarRotatedEdges(centre, newR, HEX_DIST_DIAGONAL);

        List<Point2D> edgesInnerHeights = newHexagon(centre, newR * HEX_DIST_HEIGHT);

        List<Point2D> innerStar = newHexStarTileRotated(centre, newR, HEX_DIST_DIAGONAL);

        double extConfR = r - newR;

        List<List<Point2D>> extConfigs = buildExtConfigForDesign3(innerEdges, extConfR);

        List<List<Point2D>> layerExt0 = generateCombsOfPoints(newHexagonRot(innerEdges.get(0), extConfR));
        List<Point2D> layerExt0_1 = newHexagon(innerEdges.get(0), extConfR * HEX_DIST_DIAGONAL_ROTATED);
        List<Point2D> layerExt0_2 = newHexagonRot(edgesInnerHeights.get(0), newR * HEX_DIST_DIAGONAL_ROTATED * 0.5);
        List<Point2D> layerExt0_3 = newHexagonRot(edgesInnerHeights.get(5), newR * HEX_DIST_DIAGONAL_ROTATED * 0.5);

        builder.append(drawPolygon(edges, gray));
        builder.append(drawPolylines(layerExt0, gray));

        builder.append(drawPolygon(layerExt0_1, blue));
        builder.append(drawPolygon(layerExt0_2, blue));
        builder.append(drawPolygon(layerExt0_3, blue));
        builder.append(drawPolygon(innerStar, blue));

        builder.append(drawPolylines(tile3.getLines(), red));
        builder.append(drawPolylines(tile3.getLines2(), red));

//        out.add(drawPolygon(innerEdges, gray));

//        out.add(drawPolygon(insideStar, gray));
//        out.add(drawPolygon(polygon1, gray));
//        out.add(drawPolygon(polygon2, gray));

//        builder.append(drawPolygons(
//                extPolygons,
//                gray));

        builder.append(drawPolygon(
                innerEdges, green));

//        out.add(drawPolygon(
//                edgesLayer8,gray));

        builder.append(drawPolylines(
                extConfigs, red));

//        builder.append(drawPolylines(layerExt, gray));

//        builder.append(highlightPoints(edges));
//        builder.append(highlightPoints(edgesAltLayer2));
        builder.append(highlightPoints(innerEdges));
        builder.append(highlightPoints(edgesInnerHeights));
        builder.append(highlightPoints(extConfigs.get(0)));
//        builder.append(highlightPoints(heightsEdgesLayer8));

        return builder.toString();

    }

    private static List<List<Point2D>> buildExtConfigForDesign3(final List<Point2D> centres, final double r) {

        List<List<Point2D>> out = newArrayList();
        //List<Point2D> edges = cloneAndTranslateScalePoints(centre, r, hexPoints);

        int index = 0;
        for (Point2D centre : centres) {
            List<Point2D> heights = newHexagon(centre, r * HEX_DIST_HEIGHT);
            out.add(
                    asList(
                            heights.get(toHexIndex(1 + index)),
                            centre,
                            heights.get(toHexIndex(0 + index))
                    )
            );
            index++;
        }

        return out;

//        return asList(
//                heights.get(index),
//                centre,
//                heights.get((index + 5) % 6)
//        );

    }

    public static String newStarDesign4(final Point2D centre, final double r) {

        StringBuilder builder = new StringBuilder();

        final String gray = newStyle(GRAY, 1, 1);
        final String green = newStyle(GREEN, 2, 1);
        final String red = newStyle(RED, 2, 1);
        final String blue = newStyle(BLUE, 2, 1);

        List<Point2D> layer1Edges = newHexagonRot(centre, r);
        double layer2r = r * HEX_DIST_DIAGONAL;
        List<Point2D> layer2Edges = newHexagon(centre, layer2r);
        double layer3r = layer2r * HEX_DIST_DIAGONAL;
        List<Point2D> layer3Edges = newHexagonRot(centre, layer3r);

        List<Point2D> layerExtEdges = newHexagon(centre, layer3r * HEX_DIST_NEW_CENTRE);

        List<Point2D> layerExtPol1Edges = newHexagonRot(layerExtEdges.get(0), layer3r);

        builder.append(
                drawPolylines(
                        generateCombsOfPoints(layer1Edges),
                        gray)
        );

        builder.append(drawPolylines(newHexHeightsRot(centre, r),
                gray)
        );

        builder.append(
                drawPolylines(
                        generateCombsOfPoints(layer2Edges),
                        gray)
        );
//
        builder.append(
                drawPolygon(layer3Edges, green)
        );
//
        builder.append(
                drawPolylines(generateCombsOfPoints(layerExtPol1Edges), gray)
        );
//
        builder.append(
                drawPolylines(Tile4.buildExtConf(centre, layer3r), red)
        );

        builder.append(highlightPoints(layerExtEdges));

        return builder.toString();
    }

    public static String newStarDesign5(final Point2D centre, final double r) {

        StringBuilder builder = new StringBuilder();

        final String gray = newStyle(GRAY, 1, 1);
        final String green = newStyle(GREEN, 2, 1);
        final String red = newStyle(RED, 2, 1);
        final String blue = newStyle(BLUE, 2, 1);

        List<Point2D> layer1Edges = newHexagonRot(centre, r);

        double layer2R = r * HEX_DIST_DIAGONAL;

        List<Point2D> layer2Edges = newHexagon(centre, layer2R);

        builder.append(
                drawPolylines(generateCombsOfPoints(layer1Edges), gray)
        );

        double layer3R = layer2R * HEX_DIST_DIAGONAL;

        List<Point2D> layer3Edges = newHexagonRot(centre, layer3R);

        List<Point2D> outerEdges = newHexagon(centre, layer3R * HEX_DIST_NEW_CENTRE);

        List<List<Point2D>> lines = Tile5.buildOuterLines(outerEdges, layer3R);

        builder.append(
                drawPolylines(generateCombsOfPoints(layer2Edges), gray)
        );
        builder.append(
                drawPolygon(layer2Edges, blue)
        );

        builder.append(
                drawPolygon(layer3Edges, green)
        );

        builder.append(highlightPoints(outerEdges));
        builder.append(drawPolylines(lines, red));

        return builder.toString();
    }

    public static String newStarDesign6(final Point2D centre, final double r) {

        StringBuilder builder = new StringBuilder();

        final String gray = newStyle(GRAY, 1, 1);
        final String green = newStyle(GREEN, 2, 1);
        final String red = newStyle(RED, 2, 1);
        final String blue = newStyle(BLUE, 2, 1);

        List<Point2D> layer1Edges = newHexagonRot(centre, r);

        double newR = r * HEX_DIST_DIAGONAL_ROTATED;

        List<Point2D> layer2Edges = newHexagonRot(centre, newR);
        List<Point2D> outerEdges = newHexagon(centre, newR * HEX_DIST_NEW_CENTRE);

        List<Point2D> outerInnerHex = newHexagonRot(outerEdges.get(0), newR * HEX_DIST_DIAGONAL_ROTATED);

        builder.append(
                drawPolylines(generateCombsOfPoints(layer1Edges), gray)
        );

        builder.append(
                drawPolygons(newHexInnerTrianglesRot(centre, r), gray)
        );

        builder.append(drawPolygon(outerInnerHex, gray));

        builder.append(highlightPoints(layer2Edges));
        builder.append(highlightPoints(outerEdges));

        builder.append(drawPolylines(Tile6.buildOuterLines(outerEdges, newR), red));

        return builder.toString();

    }

    public static String newDesign7(final Point2D centre, final double r) {

        StringBuilder builder = new StringBuilder();

        final String gray = newStyle(GRAY, 1, 1);
        final String green = newStyle(GREEN, 2, 1);
        final String red = newStyle(RED, 2, 1);
        final String blue = newStyle(BLUE, 2, 1);

        Tile7 tile7 = new Tile7(centre, r);

        List<Point2D> outerHex = newHexagon(centre, r);
        List<Point2D> outerHexRotated = newHexagonRot(centre, r);

        double newR = r * HEX_DIST_DIAGONAL_ROTATED;

        List<Point2D> innerHex = newHexagon(centre, newR);
        List<Point2D> innerHexRot = newHexagonRot(centre, newR);

        List<Point2D> innerHex2 = newHexagon(centre, newR * HEX_DIST_DIAGONAL_ROTATED);
        List<Point2D> innerHexRot2 = newHexagonRot(centre, newR * HEX_DIST_DIAGONAL_ROTATED);

        List<Point2D> heightPoints = newHexagonRot(centre, r * HEX_DIST_HEIGHT);

        double rSmall = r * (1 - HEX_DIST_HEIGHT);

        List<Point2D> outerHexSmall = newHexagonRot(heightPoints.get(0), rSmall);

        builder.append(drawPolygon(outerHex, gray));
        builder.append(drawPolygon(outerHexRotated, gray));

        builder.append(drawPolylines(newHexHeights(centre, r), gray));
        builder.append(drawPolylines(newHexHeightsRot(centre, r), gray));

        builder.append(drawPolygons(newHexInnerTriangles(centre, r), gray));
        builder.append(drawPolygons(newHexInnerTrianglesRot(centre, r), gray));

        builder.append(drawPolygons(newHexInnerTriangles(centre, newR), gray));
        builder.append(drawPolygons(newHexInnerTrianglesRot(centre, newR), gray));

        builder.append(drawPolygon(innerHex, blue));
        builder.append(drawPolygon(innerHexRot, blue));

        builder.append(drawPolygon(innerHex2, blue));
        builder.append(drawPolygon(innerHexRot2, blue));

        builder.append(drawPolygon(outerHexSmall, blue));
        builder.append(drawPolylines(newHexHeightsRot(heightPoints.get(0), rSmall), gray));

        builder.append(highlightPoints(heightPoints));

        builder.append(drawPolylines(tile7.getLines(), red));

        return builder.toString();

    }

    public static String newDesign8(final Point2D centre, final double r) {

        StringBuilder builder = new StringBuilder();

        final String gray = newStyle(GRAY, 1, 1);
        final String green = newStyle(GREEN, 2, 1);
        final String red = newStyle(RED, 2, 1);
        final String blue = newStyle(BLUE, 2, 1);

        Tile8 tile8 = new Tile8(centre, r);

        List<Point2D> mainHex = newHexagon(centre, r);
        List<Point2D> mainHexRot = newHexagonRot(centre, r);

        List<Point2D> mainHeights = newHexagonRot(centre, r * HEX_DIST_HEIGHT);
        List<Point2D> mainHeightsRot = newHexagon(centre, r * HEX_DIST_HEIGHT);

        List<Point2D> mainHeightsConcat = concatEdges(mainHeightsRot, mainHeights);

        builder.append(drawPolygon(mainHex, gray));
        builder.append(drawPolygon(mainHexRot, gray));
        builder.append(drawPolygon(mainHeights, gray));
        builder.append(drawPolygon(mainHeightsRot, gray));
        builder.append(drawPolygon(mainHeightsConcat, gray));
//        builder.append(drawPolygons(newHexHeights(centre,r), gray));
        builder.append(drawPolylines(newHexDiag(centre, r), gray));
        builder.append(drawPolylines(newHexDiagRot(centre, r), gray));
        builder.append(drawPolygon(newHexagon(centre, tile8.getInnerR()), gray));
        builder.append(drawPolygon(newHexagonRot(centre, tile8.getInnerR()), gray));

//        builder.append(highlightPoints(tile8.getPointsA()));
//        builder.append(highlightPoints(tile8.getPointsB()));
//        builder.append(highlightPoints(tile8.getPointsC()));
//        builder.append(highlightPoints(tile8.getHeights()));
//        builder.append(highlightPoints(tile8.getPointsD()));
//        builder.append(highlightPoints(tile8.getPointsE()));
//        builder.append(highlightPoints(tile8.getPointsF()));
        builder.append(highlightPoints(tile8.getPointsG()));
//        builder.append(highlightPoints(tile8.getPointsH()));
        builder.append(drawPolylines(tile8.getLines(), red));

        return builder.toString();
    }

    public static String newDesign9(final Point2D centre, final double r) {

        StringBuilder builder = new StringBuilder();

        Tile9 tile9 = new Tile9(centre, r);

        final String gray = newStyle(GRAY, 1, 1);
        final String green = newStyle(GREEN, 2, 1);
        final String red = newStyle(RED, 2, 1);
        final String blue = newStyle(BLUE, 2, 1);

        List<Point2D> outer = newHexagon(centre, r);
        List<Point2D> outerRot = newHexagonRot(centre, r);

        double rH = r * HEX_DIST_HEIGHT;

        builder.append(drawPolygon(outer, gray));
        builder.append(drawPolygon(outerRot, gray));
//        builder.append(drawPolylines(newHexHeightsRot(centre, r), gray));
//        builder.append(drawPolygons(newHexInnerTrianglesRot(centre, r), gray));
        builder.append(drawPolylines(newHexDiagRot(centre, r), gray));
        builder.append(drawPolylines(newHexDiag(centre, r), gray));

        builder.append(drawPolylines(concateOuterHexagons(outer, outerRot), gray));

//        out.add(drawPolygon(outerLayer2, gray));
//        out.add(drawPolygon(outerLayer2Rot, gray));

        builder.append(drawPolylines(concateOuterHexagons(tile9.getOuterLayer1(), tile9.getOuterLayer1Rot()), gray));
        builder.append(drawPolylines(concateOuterHexagons(tile9.getOuterLayer2(), tile9.getOuterLayer2Rot()), gray));
//        builder.append(highlightPoints(heightPoints));

        builder.append(drawPolygon(tile9.getInnerLayer1(), gray));
        builder.append(drawPolygon(tile9.getInnerLayer1Rot(), gray));

        builder.append(drawPolylines(buildLines(
                newHexEdge(centre, r * Tile9.INNER_R2, 0),
                asList(newHexEdgeRot(outerRot.get(5), r * Tile9.INNER_R1, 1))), gray));

        builder.append(drawPolylines(buildLines(
                newHexEdge(centre, r * Tile9.INNER_R2, 5),
                asList(newHexEdgeRot(outerRot.get(5), r * Tile9.INNER_R1, 3))), gray));

        builder.append(drawPolylines(buildLines(
                newHexEdgeRot(centre, r * Tile9.INNER_R2, 0),
                asList(newHexEdge(outer.get(0), r * Tile9.INNER_R1, 1))), gray));

        builder.append(drawPolylines(buildLines(
                newHexEdgeRot(centre, r * Tile9.INNER_R2, 5),
                asList(newHexEdge(outer.get(0), r * Tile9.INNER_R1, 5))), gray));

        builder.append(drawPolygons(newHexInnerTrianglesFull(centre, r * Tile9.INNER_R2), gray));
        builder.append(drawPolygons(newHexInnerTrianglesFullRot(centre, r * Tile9.INNER_R2), gray));

        builder.append(drawPolylines(concateOuterHexagons(
                newHexagon(outer.get(0), r * (1.0 - Tile9.OUTER_R1)),
                newHexagonRot(outer.get(0), r * (1.0 - Tile9.OUTER_R1))
        ), gray));

        builder.append(drawPolylines(concateOuterHexagons(
                newHexagon(outer.get(0), r * (1.0 - Tile9.OUTER_R2)),
                newHexagonRot(outer.get(0), r * (1.0 - Tile9.OUTER_R2))
        ), gray));

        for (Point2D edge : outerRot) {
            builder.append(drawPolygon(newHexagonRot(edge, r * Tile9.INNER_R1), blue));
        }

//        out.add(drawPolygon(newHexagonRot(outerRot.get(2), r * Tile9.INNER_R1), blue));
//        out.add(drawPolygon(newHexagonRot(outerRot.get(3), r * Tile9.INNER_R1), blue));
//        out.add(drawPolygon(newHexagonRot(outerRot.get(4), r * Tile9.INNER_R1), blue));
        builder.append(drawPolygon(newHexagonRot(centre, r * (1.0 - Tile9.INNER_R1)), gray));

//        builder.append(drawPolylines(Tile9.calcOuterLines1(centre, r), red));

        builder.append(drawPolygon(newHexagonRot(centre, r), red));
        builder.append(drawPolylines(tile9.getLines(), red));

        builder.append(highlightPoints(newHexagon(centre, r * Tile9.OUTER_R4)));
        builder.append(highlightPoints(newHexagonRot(centre, r * Tile9.OUTER_R4)));

//        builder.append(highlightPoints(tile9.getPointsA()));
//        builder.append(highlightPoints(tile9.getPointsB()));
//        builder.append(highlightPoints(tile9.getPointsC()));
//        builder.append(highlightPoints(tile9.getPointsD()));
//        builder.append(highlightPoints(tile9.getPointsE()));
//        builder.append(highlightPoints(tile9.getPointsF()));
//        builder.append(highlightPoints(tile9.getPointsG()));
//        builder.append(highlightPoints(tile9.getPointsH()));
//        builder.append(highlightPoints(tile9.getPointsI()));
//        builder.append(highlightPoints(tile9.getPointsJ()));
//        builder.append(highlightPoints(tile9.getPointsK()));
//        builder.append(highlightPoints(tile9.getPointsL()));
//        builder.append(highlightPoints(tile9.getPointsM()));
//        builder.append(highlightPoints(tile9.getPointsN()));
//        builder.append(highlightPoints(tile9.getPointsO()));
//        builder.append(highlightPoints(tile9.getPointsP()));
//        builder.append(highlightPoints(tile9.getPointsQ()));
//        builder.append(highlightPoints(tile9.getPointsR()));
//        builder.append(highlightPoints(tile9.getPointsS()));
//        builder.append(highlightPoints(tile9.getInnerLayer1()));
//        builder.append(highlightPoints(tile9.getInnerLayer1Rot()));
        builder.append(highlightPoints(tile9.getOuterLayer1()));
        builder.append(highlightPoints(tile9.getOuterLayer1Rot()));
        builder.append(highlightPoints(tile9.getOuterLayer2()));
        builder.append(highlightPoints(tile9.getOuterLayer2Rot()));

        return builder.toString();

    }

    public static String newDesign11(final Point2D centre, final double r) {

        StringBuilder builder = new StringBuilder();

        Tile11 tile = new Tile11(centre, r);

        final String gray = newStyle(GRAY, 1, 1);
        final String green = newStyle(GREEN, 2, 1);
        final String red = newStyle(RED, 2, 1);
        final String blue = newStyle(BLUE, 2, 1);

        builder.append(drawPolygon(newHexagonRot(centre, r), gray));

        for (Point2D edge : tile.getMainHexRot()) {
            builder.append(drawPolygon(newHexagon(edge, tile.getInnerR()), gray));
        }

        builder.append((newCircle(centre, tile.getInnerR(), gray)));
        for (Point2D edge : tile.getMainHeights()) {
            builder.append((newCircle(edge, r / 2.0, gray)));
        }

        builder.append(drawPolylines(newHexHeightsRot(centre, r), gray));
        builder.append(drawPolylines(newHexDiagRot(centre, r), gray));

        builder.append(highlightPoints(tile.getMainHeights()));
        builder.append(highlightPoints(tile.getPointsA()));
        builder.append(highlightPoints(tile.getPointsB()));
        builder.append(highlightPoints(tile.getPointsC()));
        builder.append(highlightPoints(tile.getPointsD()));
//        builder.append(highlightPoints(newHexagon(centre,tile.getInnerR2())));

        builder.append(drawPolylines(tile.getLines(), red));

        return builder.toString();
    }

    public static String newDesign12(final Point2D centre, final double r) {

        StringBuilder builder = new StringBuilder();

        Tile tile = new Tile12(centre, r);

        final String gray = newStyle(GRAY, 1, 1);
        final String green = newStyle(GREEN, 2, 1);
        final String red = newStyle(RED, 2, 1);
        final String blue = newStyle(BLUE, 2, 1);

//        final double newR = ((r * RECT_DIST_HEIGHT) / 6.0)/HEX_DIST_HEIGHT;
        final double newR = ((r * RECT_DIST_HEIGHT) / 5.0);

        builder.append(drawPolygon(newRectRot(centre, r), gray));
        builder.append(drawPolygon(newRectRot(centre, r * Tile12.RATIO_W), gray));
//        builder.append(drawPolygon(newHexagonRot(centre, r), gray));
        builder.append(drawPolygon(newHexagonRot(centre, newR), gray));
        builder.append(drawPolylines(newHexDiagRot(centre, r), gray));

        builder.append(drawPolygon(newHexagonRot(centre, 2 * newR), gray));
        builder.append(drawPolygon(newHexagonRot(centre, 3 * newR), gray));
        builder.append(drawPolygon(newHexagonRot(centre, 4 * newR), gray));
        builder.append(drawPolygon(newHexagonRot(centre, 5 * newR), gray));
        builder.append(drawPolygon(newHexagonRot(centre, 6 * newR), gray));
        builder.append(drawPolygon(newHexagonRot(centre, 7 * newR), gray));
//        builder.append(drawPolygon(newHexStarTileRotated(centre, 2 * newR, HEX_DIST_DIAGONAL), blue));

//        builder.append(drawPolygon(newHexagonRot(newEdgeAt(centre, 2*newR, HEX_RADIANS_ROT.get(2)), newR), gray));

        builder.append(drawPolygons(tile.getPayload().getPolygons(), red));
        builder.append(drawPolylines(tile.getPayload().getPolylines(), red));

        return builder.toString();

    }

    public static String newDesign13(final Point2D centre, final double r) {

        StringBuilder builder = new StringBuilder();

        Tile tile = new Tile13(centre, r);

        final String gray = newStyle(GRAY, 1, 1);
        final String green = newStyle(GREEN, 2, 1);
        final String red = newStyle(RED, 2, 1);
        final String blue = newStyle(BLUE, 2, 1);

        double newR = r / 3.0;
        double newR2 = newR * PolygonTools.HEX_DIST_NEW_CENTRE;

        List<Point2D> layerOut = newHexagon(centre, 2.0 * newR);

        builder.append(drawPolygon(newHexagon(centre, r), gray));
        builder.append(drawPolygon(newHexagon(centre, newR), gray));

        builder.append(drawPolygon(layerOut, gray));
        builder.append(drawPolygon(newHexagonRot(centre, newR2), gray));

        builder.append(drawPolygon(newHexagon(layerOut.get(0), newR), gray));

        builder.append(drawPolylines(tile.getPayload().getPolylines(), red));

        return builder.toString();
    }

    public static String newDesign14(final Point2D centre, final double r) {


        final String gray = newStyle(GRAY, 1, 1);
        final String green = newStyle(GREEN, 2, 1);
        final String red = newStyle(RED, 2, 1);
        final String blue = newStyle(BLUE, 2, 1);


        Tile14 tile = new Tile14(centre, r);


        StringBuilder builder = new StringBuilder();

        double newR = r / 4.0;


        builder.append(drawPolygon(newHexagon(centre, r),gray));
        builder.append(drawPolylines(newHexDiag(centre, r), gray));

        for (int i = 1; i < 4; i++) {
            builder.append(drawPolygon(newHexagon(centre, i*newR),gray));
        }


        builder.append(drawPolylines(tile.getPayload().getPolylines(), red));

        builder.append(highlightPoints(tile.getPointsA()));
        builder.append(highlightPoints(tile.getPointsB()));
        builder.append(highlightPoints(tile.getPointsC()));

        return builder.toString();
    }
}
