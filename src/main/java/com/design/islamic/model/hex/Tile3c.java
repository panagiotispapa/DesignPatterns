package com.design.islamic.model.hex;

import com.design.common.*;
import com.design.common.Polygon;
import com.design.common.model.Style;
import com.design.islamic.model.DesignSupplier;
import com.design.islamic.model.Hex;
import com.design.islamic.model.PayloadSimple;
import com.design.islamic.model.TileSupplier;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

import static com.design.common.FinalPointTransition.K;
import static com.design.common.FinalPointTransition.fpt;
import static com.design.common.Polygon.Type.HOR;
import static com.design.common.Polygon.Type.VER;
import static com.design.common.RatioHelper.P6.H;
import static com.design.islamic.model.Hex.Corner.*;
import static com.design.islamic.model.Hex.*;
import static java.util.Arrays.asList;

public class Tile3c {


    private static final double KB = H;
    private static final double KC = KB * H;
    private static final double CA = 1 - KC;
    private static final double CB = 0.5 * KB;
    private static final double CD = CB;
    private static final double KD = 1 - CA - CD;

    public final static FinalPointTransition A = fpt(pt(1.0, RIGHT));
    public final static FinalPointTransition C = fpt(pt(KC, RIGHT));
    public final static FinalPointTransition D = fpt(pt(KD, RIGHT));
    public final static FinalPointTransition B = fpt(pt(H, DR_V));
    public final static FinalPointTransition E = fpt(pt(H, UR_V));

    @TileSupplier
    public static PayloadSimple getPayloadSimple() {
        Style whiteBold = new Style.Builder(Color.WHITE, 2).build();

        return new PayloadSimple.Builder("hex_tile_03c",
                Hex.ALL_VERTEX_INDEXES
        )
                .withPathsNewFull(whiteBold, getFullPath())
                .withGridConf(Grid.Configs.HEX_VER2.getConfiguration())
                .build();
    }

    @DesignSupplier
    public static DesignHelper getDesignHelper() {
        Style blue = new Style.Builder(Color.BLUE, 1).build();
        Style gray = new Style.Builder(Color.GRAY, 1).build();
        Style green = new Style.Builder(Color.GREEN, 1).build();
        Style red = new Style.Builder(Color.RED, 2).build();

        List<String> equations = Arrays.asList(
                "KB = h",
                "KC = h * KB",
                "BC = 0.5 * KB",
                "DC = BC",
                "CA = KA - KC",
                "KD = KA - KC - DC"
        );

        return new DesignHelper(Hex.ALL_VERTEX_INDEXES, "hex_tile_03c_design")
                .addFullPaths(red, getFullPath())
                .addEquations(equations)
                .addImportantVertexes(Tile3c.class)
//                .addImportantVertexes(
//                        ImportantVertex.of(main, RIGHT.getVertex(), "A"),
//                        ImportantVertex.of(main_Ver, DR_V.getVertex(), "B"),
//                        ImportantVertex.of(hexKC, RIGHT.getVertex(), "C"),
//                        ImportantVertex.of(hexKD, RIGHT.getVertex(), "D")
//                )
                .addSinglePathsLines(
                        gray,
                        perimeter(1.0, HOR).apply(K),
                        diagonals(1.0, HOR).apply(K),
                        perimeter(KB, VER).apply(K),
                        diagonals(KB, VER).apply(K)
                )
                .addSinglePathsLines(
                        green,
                        perimeter(KC, HOR).apply(K)
                )
                .addSinglePathsLines(
                        blue,
                        perimeter(KD, HOR).apply(K)
                )
                ;

    }

    private static List<PointsPath> getFullPath() {
        return asList(
                PointsPath.of(B, D, E)
        );
    }
}