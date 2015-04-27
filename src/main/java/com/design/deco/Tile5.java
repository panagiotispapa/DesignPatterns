package com.design.deco;

import com.design.common.DesignHelper;
import com.design.common.Grid;
import com.design.common.Polygon;
import com.design.common.model.Path;
import com.design.common.model.Style;
import com.design.islamic.model.*;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;

import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.IntStream;

import static com.design.common.Polygon.Type.HOR;
import static com.design.common.Polygon.Type.VER;
import static com.design.common.RatioHelper.P4.H;
import static com.design.islamic.model.Rect.Corner.*;
import static com.design.islamic.model.Rect.*;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;

public class Tile5 {

    private static final double KA = 1.0;
    private static final double Ratio_m = H / 2.0;

    @TileSupplier
    public static PayloadSimple getPayloadSimple() {

        Polygon main = Rect.rect(1, VER);

        Style whiteBold = new Style.Builder(Color.WHITE, 2).build();


        return new PayloadSimple.Builder("deco_tile_05",
                Hex.ALL_VERTEX_INDEXES
        )
                .withPathsSingleFromLines(asList(
                        asList(
                                d(1),
                                dl(1, 2),
                                dl(2, 2),
                                d(2),
                                u(1)
                        ),
                        asList(
                                ul(1, 1),
                                ur(1, 2)
                        ),
                        asList(
                                instruction(Rect.rect(Ratio_m, VER, centreTransform(Ratio_m, RIGHT)), LEFT),
                                r(2)
                        ),
                        asList(
                                d(2),
                                dr(2, 1),
                                r(1)
                        ),
                        asList(
                                dr(1, 1),
                                dr(1, 2),
                                dr(2, 2)
                        ),
                        asList(
                                ul(2, 1),
                                dl(1, 1)
                        ),
                        asList(
                                l(1),
                                l(2),
                                ul(2, 2),
                                ur(2, 1),
                                ur(1, 1)
                        ),
                        asList(
                                ur(2, 2),
                                r(2)
                        )

                ), whiteBold)
                .withGridConf(Grid.Configs.RECT2.getConfiguration())
                .build();
    }

    private static Pair<Polygon, Polygon.Vertex> dl(int timesDown, int timesLeft) {
        return instruction(Rect.rect(timesLeft * Ratio_m, VER, centreTransform(timesDown * Ratio_m, DOWN)), LEFT);
    }

    private static Pair<Polygon, Polygon.Vertex> dr(int timesDown, int timesRight) {
        return instruction(Rect.rect(timesRight * Ratio_m, VER, centreTransform(timesDown * Ratio_m, DOWN)), RIGHT);
    }

    private static Pair<Polygon, Polygon.Vertex> ul(int timesUp, int timesLeft) {
        return instruction(Rect.rect(timesLeft * Ratio_m, VER, centreTransform(timesUp * Ratio_m, UP)), LEFT);
    }

    private static Pair<Polygon, Polygon.Vertex> ur(int timesUp, int timesRight) {
        return instruction(Rect.rect(timesRight * Ratio_m, VER, centreTransform(timesUp * Ratio_m, UP)), RIGHT);
    }

    private static Pair<Polygon, Polygon.Vertex> l(int times) {
        return instruction(Rect.rect(times * Ratio_m, VER), LEFT);
    }

    private static Pair<Polygon, Polygon.Vertex> r(int times) {
        return instruction(Rect.rect(times * Ratio_m, VER), RIGHT);
    }

    private static Pair<Polygon, Polygon.Vertex> u(int timesUp) {
        return instruction(Rect.rect(timesUp * Ratio_m, VER), UP);
    }

    private static Pair<Polygon, Polygon.Vertex> d(int timesUp) {
        return instruction(Rect.rect(timesUp * Ratio_m, VER), DOWN);
    }


    @DesignSupplier
    public static DesignHelper getDesignHelper() {
        Style red = new Style.Builder(Color.RED, 2).build();
        Style gray = new Style.Builder(Color.GRAY, 1).build();
        Style green = new Style.Builder(Color.GREEN, 1).build();
        Style blue = new Style.Builder(Color.BLUE, 1).build();

        Polygon main = Rect.rect(1, HOR);

        List<String> equations = Arrays.asList(
                "KB = KA / 5.0"
        );

        Function<Polygon, List<Path>> diagVer = Path.fromListOfVertexes.apply(asList(
                asList(
                        UP.getVertex(),
                        DOWN.getVertex()
                )
        ));
        Function<Polygon, List<Path>> diagHor = Path.fromListOfVertexes.apply(asList(
                asList(
                        LEFT.getVertex(),
                        RIGHT.getVertex()
                )
        ));

        return new DesignHelper(Hex.ALL_VERTEX_INDEXES, "deco_tile_05_design")
                .addSinglePathsList(getPayloadSimple().getPathsSingle(), red)
                .addEquations(equations)
                .addImportantPoints(asList(
                        Triple.of(main, DR.getVertex(), "A")
                ))
                .addSinglePaths(asList(
                        Pair.of(main, PERIMETER)
                ), gray)
                .addSinglePaths(IntStream.range(0, 2).mapToObj(i -> Rect.rect(H, VER, centreTransform(Ratio_m * i, RIGHT))).collect(toList()), diagVer, gray)
                .addSinglePaths(IntStream.range(1, 2).mapToObj(i -> Rect.rect(H, VER, centreTransform(Ratio_m * i, LEFT))).collect(toList()), diagVer, gray)
                .addSinglePaths(IntStream.range(0, 2).mapToObj(i -> Rect.rect(H, VER, centreTransform(Ratio_m * i, UP))).collect(toList()), diagHor, gray)
                .addSinglePaths(IntStream.range(1, 2).mapToObj(i -> Rect.rect(H, VER, centreTransform(Ratio_m * i, DOWN))).collect(toList()), diagHor, gray)
                ;
    }

}