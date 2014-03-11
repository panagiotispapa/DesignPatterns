package com.design.islamic.model;

import com.design.islamic.CentreConfiguration;
import com.design.islamic.model.hex.Tile12;
import com.google.common.collect.Maps;

import java.util.Map;

import static com.design.common.PolygonTools.HEX_DIST_HEIGHT;
import static com.design.islamic.CentreConfiguration.Conf.HEX_SECOND;
import static com.design.islamic.CentreConfiguration.Conf.HEX_THIRD;
import static com.design.islamic.CentreConfiguration.Conf.RECT;

public enum HexPattern {

    ONE("Pattern1", HEX_SECOND, 1.0),
//    TWO("Pattern2"),
    STAR1("Pattern Star 1", HEX_SECOND, 1.0),
    STAR2("Pattern Star 2", HEX_SECOND, 1.0),
    STAR3("Pattern Star 3", HEX_SECOND, 1.0),
    THREE("Pattern 3", HEX_SECOND, 1.0),
    FOUR("Pattern 4", HEX_SECOND, 1.0),
    FIVE("Pattern 5", HEX_SECOND, 1.0),
    SIX("Pattern 6", HEX_SECOND, 1.0),
    SEVEN("Pattern 7", HEX_SECOND, 1.0),
    EIGHT("Pattern 8", HEX_SECOND, 1.0),
    NINE("Pattern 9", HEX_SECOND, 1.0),
    TEN("Pattern 10", HEX_SECOND, 1.0),
    ELEVEN("Pattern 11", HEX_SECOND, 1.0),
    TWELVE("Pattern 12", RECT, Tile12.RATIO_W),
    THIRTEEN("Pattern 13", HEX_THIRD, 1.0),
    FOURTEEN("Pattern 14", HEX_THIRD, 1.0)
    ;


    private final String description;
    private final CentreConfiguration.Conf config;

    private final double ratio;


    private static final Map<String, HexPattern> patternMap;


    static {
        patternMap = Maps.newHashMap();
        for (HexPattern hexPattern : HexPattern.values()) {
            patternMap.put(hexPattern.getDescription(), hexPattern);
        }

    }

    private HexPattern(String description, CentreConfiguration.Conf config, double ratio) {
        this.description = description;
        this.config = config;
        this.ratio = ratio;
    }

    public double getRatio() {
        return ratio;
    }

    public String getDescription() {
        return description;
    }

    public CentreConfiguration.Conf getConfig() {
        return config;
    }

    public static HexPattern fromDescription(String description) {

        return patternMap.get(description);

    }

}
