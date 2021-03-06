package com.design.arabic.model.hex;


import com.design.common.DesignHelper;
import com.design.common.FinalPointTransition;
import com.design.common.Grid;
import com.design.common.Line;
import com.design.common.model.Style;
import com.design.arabic.model.DesignSupplier;
import com.design.arabic.model.Hex;
import com.design.arabic.model.Payload;
import com.design.arabic.model.TileSupplier;
import com.googlecode.totallylazy.Sequence;

import java.awt.*;

import static com.design.common.FinalPointTransition.K;
import static com.design.common.FinalPointTransition.fpt;
import static com.design.common.Polygon.Type.HOR;
import static com.design.common.Polygon.Type.VER;
import static com.design.common.RatioHelper.P6.H;
import static com.design.common.RatioHelper.P6.P;
import static com.design.arabic.model.Hex.Vertex.*;
import static com.design.arabic.model.Hex.diagonals;
import static com.design.arabic.model.Hex.perimeter;
import static com.googlecode.totallylazy.Sequences.sequence;

public class TileStar3b {

    private static final double KA = 1;
    private static final double KE = H;
    private static final double EB = KE;
    private static final double EA = KA / 2;
    private static final double AD = KA / 2;
    private static final double AB = EB - EA;
    private static final double AF = AB * P;
    private static final double DF = AD - AF;
    private static final double BC = 2 * DF;
    private static final double AI = EA * P;
    private static final double EI = EA * H;
    private static final double IL = EI;
    private static final double KL = KA - IL - AI;

    public static final FinalPointTransition E = fpt(H, DR_V);
    public static final FinalPointTransition G = fpt(KL, DR_H);
    public static final FinalPointTransition L = fpt(KL, RIGHT);

    @TileSupplier
    public static Payload getPayloadSimple() {
        Style whiteBold = new Style.Builder(Color.WHITE, 2).build();
        return new Payload
                .Builder(
                "hex_tile_star_03b",
                Hex.ALL_VERTEX_INDEXES
        ).withPathsFull(whiteBold, getFullPath())
                .withGridConf(Grid.Configs.HEX_VER2.getConfiguration())
                .build();
    }


    private static Sequence<Line> getFullPath() {
        return sequence(
                Line.line(L, E, G)
        );
    }


    @DesignSupplier
    public static DesignHelper getDesignHelper() {
        Style blue = new Style.Builder(Color.BLUE, 1).build();
        Style gray = new Style.Builder(Color.GRAY, 1).build();
        Style green = new Style.Builder(Color.GREEN, 1).build();
        Style red = new Style.Builder(Color.RED, 2).build();

        return new DesignHelper(Hex.ALL_VERTEX_INDEXES, "hex_tile_star_03b_design")
                .withGrid(Grid.Configs.HEX_VER.getConfiguration())
                .addFullPaths(red, getFullPath())
//                .addEquations(equations)
                .addImportantVertexes(TileStar3b.class)
                .addSinglePathsLines(
                        gray,
                        perimeter(1, HOR).apply(K),
                        diagonals(1, HOR).apply(K)
                )
                .addSinglePathsLines(
                        green,
                        perimeter(H, VER).apply(K)
                )
                .addSinglePathsLines(
                        blue,
                        perimeter(KL, HOR).apply(K)
                )
                ;

    }


}
