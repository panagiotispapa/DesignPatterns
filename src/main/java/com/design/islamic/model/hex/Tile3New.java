package com.design.islamic.model.hex;

import com.design.common.Polygon;
import com.design.islamic.model.DrawSegmentsInstructions;
import com.design.islamic.model.Hex;
import com.design.islamic.model.tiles.HexGrid;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;

import java.awt.geom.Point2D;
import java.util.List;
import java.util.stream.Stream;

import static com.design.common.view.SvgFactory.*;
import static com.design.islamic.model.Hex.HEIGHT_RATIO;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.joining;

public class Tile3New extends TileBasic {

    public static double RATIO_1 = HEIGHT_RATIO / (HEIGHT_RATIO + 0.5);
    public static double RATIO_2 = HEIGHT_RATIO * (1 - RATIO_1);

    public Tile3New(Pair<Point2D, Double> initialConditions) {

        super(initialConditions);

    }

//    @Override
//    protected Stream<Triple<Polygon, Polygon, DrawSegmentsInstructions.CombinedVertexes>> getMainStars() {
//
//        Polygon main = Hex.hex(RATIO_1, Polygon.Type.VER);
//        Polygon inner = Hex.hex(RATIO_1 * 0.5, Polygon.Type.VER);
//
//        return Stream.of(Triple.of(inner, main.getRegistered(), DrawSegmentsInstructions.CombinedVertexes.STAR));
//    }

    @Override
    protected Stream<Pair<Polygon, List<List<Polygon.Vertex>>>> getMainLinesSingle() {
        Polygon main = Hex.hex(RATIO_1, Polygon.Type.VER);
        return Stream.of(
                Pair.of(main, Hex.PERIMETER)
        );
    }

    @Override
    protected Stream<Triple<Polygon, Polygon, List<Polygon.Vertex>>> getMainStarsFull() {
        Polygon inner = Hex.hex(RATIO_1, Polygon.Type.VER);

        Polygon outer = Hex.hex(RATIO_2, Polygon.Type.HOR, Polygon.centreTransform(RATIO_1, Hex.Vertex.ONE, Polygon.Type.VER));

        return Stream.of(Triple.of(inner, outer, asList((Polygon.Vertex) Hex.Vertex.ONE, Hex.Vertex.TWO)));
    }

    public String design1() {
        String black = newStyle("black", 1, 1);
        String blue = newStyle("blue", 1, 1);
        String gray = newStyle("gray", 1, 1);
        String green = newStyle("green", 1, 1);
        String red = newStyle("red", 2, 1);

        List<Point2D> hexGrid = HexGrid.grid(initialConditions.getLeft(), initialConditions.getRight() / 4.0, HexGrid.TYPE.VER, 12);

        Polygon main = Hex.hex(1, Polygon.Type.VER);
        Polygon registered = main.getRegistered();
        Polygon inner = Hex.hex(RATIO_1, Polygon.Type.VER);
        Polygon innerReg = inner.getRegistered();

        Polygon outer = Hex.hex(RATIO_2, Polygon.Type.HOR, Polygon.centreTransform(RATIO_1, Hex.Vertex.ONE, Polygon.Type.VER));

        Point2D pointB = toVertex.apply(Pair.of(inner, Hex.Vertex.ONE));
        Point2D pointC = toVertex.apply(Pair.of(innerReg, Hex.Vertex.ONE));
        Point2D pointD = toVertex.apply(Pair.of(outer, Hex.Vertex.ONE));
        return
                Stream.of(
                        Stream.of(
                                highlightPoints("black", 2).apply(hexGrid)
                        ),
                        Stream.of(
                                Pair.of(main, Hex.PERIMETER),
                                Pair.of(main, Hex.DIAGONALS),
                                Pair.of(main.getRegistered(), Hex.DIAGONALS),
                                Pair.of(outer, Hex.DIAGONALS)
//                                Pair.of(inner, Hex.PERIMETER)
                        ).map(toLines.andThen(toPolylines(gray))),
                        Stream.of(
                                Pair.of(inner, Hex.PERIMETER)
                        ).map(toLines.andThen(toPolylines(green))),
                        Stream.of(
                                Pair.of(outer, Hex.PERIMETER)
                        ).map(toLines.andThen(toPolylines(blue))),
                        Stream.of(
                                Pair.of(initialConditions.getLeft(), "K"),
                                Pair.of(pointB, "B"),
                                Pair.of(pointC, "C"),
                                Pair.of(pointD, "D"),
//                                Pair.of((Point2D) new Point2D.Double(950, 100), "AB=AC"),
                                Pair.of((Point2D) new Point2D.Double(950, 50), "KB=h/(h+0.5)"),
                                Pair.of((Point2D) new Point2D.Double(950, 150), "BD=h*(1-KB)")
                        ).map(drawText()),
                        Stream.of(
                                asList(
                                        pointB,
                                        pointC,
                                        pointD
                                )
                        ).map(highlightPoints()),

                        Stream.of(
                                getPayload().getPolylines()
                        ).map(toPolylines(red))

                ).flatMap(s -> s).collect(joining());

    }



}