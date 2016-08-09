package com.design.common.model;

import com.design.common.InitialConditions;
import com.design.common.PointsPath;
import com.design.common.view.SvgFactory;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.tuple.Pair;

import java.awt.geom.Point2D;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

import static com.design.common.view.SvgFactory.toSVGString;
import static java.util.stream.Collectors.joining;


public class Path {

    private final PointsPath path;
    private final boolean closed;
    private Style style;

    public Path(Style style, PointsPath path) {
        this(false, style, path);
    }

    private Path(boolean closed, Style style, PointsPath path) {
        this.closed = closed;
        this.style = style;
        this.path = path;
    }

    public PointsPath getPath() {
        return path;
    }

    public boolean isClosed() {
        return closed;
    }

    public Style getStyle() {
        return style;
    }


    public String draw(InitialConditions ic) {
        StringBuilder builder = new StringBuilder("<path d=\"");
        builder.append(fromPath(path.generatePoints(ic)).stream().map(toSvg()).collect(joining(" ")));
        builder.append(isClosed() ? " z\"" : "\"");
        builder.append(" style=\"" + toSVGString(getStyle()));
        builder.append("\"/>");
        return builder.toString();
    }

    private static Function<Pair<Point2D, InstructionType>, String> toSvg() {
        return p -> SvgFactory.toSVG(p.getLeft(), p.getRight()::getSvgInstruction);
    }

    private enum InstructionType {
        STARTING_POINT("M"),
        LINE("L");

        private final String svgInstruction;

        InstructionType(String svgInstruction) {
            this.svgInstruction = svgInstruction;
        }

        public String getSvgInstruction() {
            return svgInstruction;
        }
    }


    private List<Pair<Point2D, InstructionType>> fromPath(List<Point2D> points) {
        List<Pair<Point2D, InstructionType>> instructions = Lists.newArrayList();
        AtomicInteger counter = new AtomicInteger(0);
        points.forEach(p -> {
            if (counter.getAndIncrement() == 0) {
                instructions.add(Pair.of(p, InstructionType.STARTING_POINT));
            } else {
                instructions.add(Pair.of(p, InstructionType.LINE));
            }
        });
        return instructions;

    }


}
