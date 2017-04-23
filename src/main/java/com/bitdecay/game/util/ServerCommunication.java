package com.bitdecay.game.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import java.util.ArrayList;
import java.util.List;

public class ServerCommunication {
    private ServerCommunication(){}
    private static ServerCommunication i;
    public static ServerCommunication instance() {
        if (i == null) i = new ServerCommunication();
        return i;
    }

    private Logger log = Logger.getLogger(ServerCommunication.class);
    private final String baseUrl = "http://bytebreakstudios.com:8080";
    private Client client = ClientBuilder.newClient();
    private final static String auth = "e1103cee83aee47220fc82e30c37143a";

    public void sendHighscore(Highscore highscore){
        log.info("Sending highscore: " + highscore);
        try {
            client.target(baseUrl + "/score").request().header("Authorization", auth).post(Entity.entity(highscore.toString(), "application/json"));
        } catch (Exception e){
            log.error("Failed to post highscore: " + highscore, e);
        }
    }

    public List<Highscore> getHighscores(){
        log.info("Get highscores");
        String result = client.target(baseUrl + "/score").request().header("Authorization", auth).get(String.class);
        List<Highscore> highscores = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode json = mapper.readTree(result).get("scores");
            log.info("Got highscore json: " + json);
            json.forEach(score -> highscores.add(Highscore.buildFromJson(score)));
            log.info("Got highscores: " + highscores);
            return highscores;
        } catch (Exception e) {
            log.error("Failed to get highscores: ", e);
            return highscores;
        }
    }
}
