package com.design.islamic.model.hex;

import com.design.common.DesignHelper;
import com.design.common.Grid;
import com.design.common.Polygon;
import com.design.common.RatioHelper.P6;
import com.design.common.model.Style;
import com.design.islamic.model.DesignSupplier;
import com.design.islamic.model.Hex;
import com.design.islamic.model.PayloadSimple;
import com.design.islamic.model.TileSupplier;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;

import java.awt.*;
import java.util.List;

import static com.design.common.Polygon.Type.HOR;
import static com.design.common.Polygon.Type.VER;
import static com.design.islamic.model.Hex.Corner.*;
import static com.design.islamic.model.Hex.instruction;
import static java.util.Arrays.asList;

public class Tile4 {

    private static double KA = 0.5 / P6.H;
    private static double KB = KA * KA;
    private static double KC = 2 * KB;
    private static double CD = 1 - KC;

    @TileSupplier
    public static PayloadSimple getPayloadSimple() {
        Polygon inner = Hex.hex(KA, HOR);
        Polygon outer = Hex.hex(CD, VER, Hex.centreTransform(1, DR_V));
        Style whiteBold = new Style.Builder(Color.WHITE, 2).build();

        return new PayloadSimple.Builder("hex_tile_04",
                Hex.ALL_VERTEX_INDEXES
        )
                .withPathsFullFromLines(asList(
                        asList(
                                instruction(outer, UP),
                                instruction(inner, DR_H)
                        ),
                        asList(
                                instruction(outer, DL_V),
                                instruction(inner, RIGHT)
                        )

                ), whiteBold)
                .build();
    }

    @DesignSupplier
    public static DesignHelper getDesignHelper() {

        Style blue = new Style.Builder(Color.BLUE, 1).build();
        Style gray = new Style.Builder(Color.GRAY, 1).build();
        Style green = new Style.Builder(Color.GREEN, 1).build();
        Style red = new Style.Builder(Color.RED, 2).build();

        Polygon main = Hex.hex(1, VER);
        Polygon hexKA = Hex.hex(KA, Polygon.Type.HOR);
        Polygon hexKA_framed = hexKA.getFramed();
        Polygon hexKB = Hex.hex(KB, VER);
        Polygon outer = Hex.hex(hexKB.getRatio(), VER, Hex.centreTransform(1, VER));
//        Polygon innerReg = inner.getRegistered();

//        Polygon outer = Hex.hex(RATIO_2, Polygon.Type.HOR, centreTransform(RATIO_1, Polygon.Type.VER));

        List<String> equations = asList(
                "i=0.5/h",
                "KA=i",
                "KB=i*i",
                "KC=KA/h=2*KB",
                "DC=DE=KB",
                "DC=1-KC"
        );

        return new DesignHelper(Hex.ALL_VERTEX_INDEXES, "hex_tile_04_design")
                .withGrid(Grid.Configs.HEX_VER.getConfiguration())
                .addFullPaths(getPayloadSimple().getPathsFull(), red)
                .addEquations(equations)
                .addImportantPoints(asList(
                        Triple.of(hexKA, RIGHT.getVertex(), "A"),
                        Triple.of(hexKB, DR_V.getVertex(), "B"),
                        Triple.of(hexKA_framed, DR_V.getVertex(), "C"),
                        Triple.of(main, DR_V.getVertex(), "D"),
                        Triple.of(outer, DL_V.getVertex(), "E")
                ))
                .addSinglePaths(asList(
                        Pair.of(main, Hex.PERIMETER),
                        Pair.of(main, Hex.DIAGONALS),
                        Pair.of(main, Hex.INNER_TRIANGLES),
                        Pair.of(hexKA, Hex.INNER_TRIANGLES),
                        Pair.of(outer, Hex.PERIMETER)
                ), gray)
                .addSinglePaths(asList(
                        Pair.of(hexKA, Hex.PERIMETER)
                ), green)
                .addSinglePaths(asList(
                        Pair.of(hexKA_framed, Hex.PERIMETER),
                        Pair.of(hexKB, Hex.PERIMETER)
                ), blue)
                ;

    }

}