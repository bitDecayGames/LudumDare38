package com.bitdecay.game.util;

import com.fasterxml.jackson.databind.JsonNode;
import org.apache.log4j.Logger;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Highscore{
    private static final Logger log = Logger.getLogger(Highscore.class);
    private static final SimpleDateFormat dt = new SimpleDateFormat("h:mm M/d");

    public String name;
    public int score;
    public long timestamp;
    public Highscore(String name, int score, long timestamp){
        this.name = name;
        this.score = score;
        this.timestamp = timestamp;
    }
    public Highscore(String name, int score){
        this(name, score, System.currentTimeMillis());
    }

    public String toString(){
        return "{ \"name\":\"" + name + "\", \"score\":" + score + ", \"time\":" + timestamp + " }";
    }

    public String pretty(){
        return name + " --- " + score + " --- " + dt.format(new Date(timestamp));
    }

    public static Highscore buildFromJson(JsonNode json){
        log.info("Build highscore from json: " + json);
        String name = json.has("name") ? json.get("name").asText() : "";
        int score = json.has("score") ? json.get("score").asInt() : 0;
        long time = json.has("time") ? json.get("time").asLong() : 0;
        return new Highscore(name, score, time);
    }
}