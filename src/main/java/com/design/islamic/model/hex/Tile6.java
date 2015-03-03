package com.design.islamic.model.hex;

import com.design.common.Polygon;
import com.design.islamic.model.Hex;
import com.design.islamic.model.tiles.HexGrid;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;

import java.awt.geom.Point2D;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static com.design.common.view.SvgFactory.*;
import static com.design.islamic.model.Hex.HEIGHT_RATIO;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

public class Tile6 extends TileBasic {

    public static double RATIO_1 = 0.5 / HEIGHT_RATIO;

    public Tile6(Pair<Point2D, Double> initialConditions) {

        super(initialConditions);

    }

    @Override
    protected Stream<Pair<Polygon, List<List<Polygon.Vertex>>>> getMainLinesSingle() {
        Polygon main = Hex.hex(0.5, Polygon.Type.VER);
        return Stream.of(
                Pair.of(main, Hex.PERIMETER)
        );
    }

    @Override
    protected Stream<Triple<Polygon, Polygon, List<Polygon.Vertex>>> getMainStarsFull() {
        Polygon main = Hex.hex(1, Polygon.Type.VER);
        Polygon inner = Hex.hex(0.5, Polygon.Type.VER);
        Polygon outer = Hex.hex(0.5 * HEIGHT_RATIO, Polygon.Type.HOR, centreTransform(1, Polygon.Type.VER));

        return Stream.of(
                Triple.of(inner, outer, asList((Polygon.Vertex) Hex.Vertex.FOUR, Hex.Vertex.FIVE)),
                Triple.of(main, outer, asList((Polygon.Vertex) Hex.Vertex.FOUR, Hex.Vertex.FIVE))
        );
    }

    public String design1() {
        String black = newStyle("black", 1, 1);
        String blue = newStyle("blue", 1, 1);
        String gray = newStyle("gray", 1, 1);
        String green = newStyle("green", 1, 1);
        String red = newStyle("red", 2, 1);

        List<Point2D> hexGrid = HexGrid.grid(initialConditions.getLeft(), initialConditions.getRight() / 4.0, HexGrid.TYPE.VER, 12);

        Polygon main = Hex.hex(1, Polygon.Type.VER);
        Polygon inner1 = Hex.hex(RATIO_1, Polygon.Type.HOR);
        Polygon inner2 = Hex.hex(0.5, Polygon.Type.VER);
        Polygon outer = Hex.hex(0.5, Polygon.Type.VER, centreTransform(1, Polygon.Type.VER));
        Polygon outerReg = outer.getRegistered();

        List<Pair<Point2D, String>> importantPoints = Stream.of(
                Triple.of(inner2, Hex.Vertex.ONE, "A"),
                Triple.of(main, Hex.Vertex.ONE, "B"),
                Triple.of(outerReg, Hex.Vertex.FOUR, "C"),
                Triple.of(outerReg, Hex.Vertex.FIVE, "D")
        ).map(importantPoint).collect(toList());

        importantPoints.add(Pair.of(initialConditions.getLeft(), "K"));
        List<String> equations = asList(
                "KA=AB=0.5",
                "KB=1",
                "BC=BD=AB*h=0.5*h"
        );

        IntStream.range(0, equations.size())
                .forEach(i -> importantPoints.add(Pair.of(new Point2D.Double(1000, (i + 1) * 50), equations.get(i))));

        importantPoints.stream().map(drawText());
        importantPoints.stream().map(Pair::getLeft).map(highlightPoint());

        return
                Stream.of(
                        Stream.of(
                                highlightPoints("black", 2).apply(hexGrid)
                        ),
                        Stream.of(
                                Pair.of(main, Hex.PERIMETER),
                                Pair.of(main, Hex.INNER_TRIANGLES),
                                Pair.of(main.getRegistered(), Hex.INNER_TRIANGLES),
                                Pair.of(outer, Hex.PERIMETER),
                                Pair.of(outerReg, Hex.PERIMETER)
                        ).map(toLines.andThen(toPolylines(gray))),
                        Stream.of(
                                Pair.of(inner2, Hex.PERIMETER)
                        ).map(toLines.andThen(toPolylines(green))),
                        Stream.of(
                                Pair.of(inner1, Hex.PERIMETER)
                        ).map(toLines.andThen(toPolylines(blue))),
                        importantPoints.stream().map(drawText()),
                        importantPoints.stream().map(Pair::getLeft).map(highlightPoint()),
                        Stream.of(
                                getPayload().getPolylines()
                        ).map(toPolylines(red))

                ).flatMap(s -> s).collect(joining());
    }

}