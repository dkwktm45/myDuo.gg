package com.project.MyDuo.Const;

import java.time.Instant;
import java.util.Date;

public interface LoLConst {
    Date SEASON_12_START = Date.from(Instant.ofEpochSecond(1641481199));
    String API_KEY_NAME = "X-Riot-Token";
    String API_KEY = "RGAPI-42c2aebd-ef5a-4b2b-88b6-d72bc595871f";
    String SUMMONER_BASIC_BASE_URL = "https://kr.api.riotgames.com/lol/summoner/v4";
    String SUMMONER_BASIC_URI = "/summoners/by-name";

    String SUMMONER_DETAIL_BASE_URL = "https://kr.api.riotgames.com/lol/league/v4";
    String SUMMONER_DETAIL_URI = "/entries/by-summoner";

    String SUMMONER_MATCH_LIST_BASE_URL = "https://asia.api.riotgames.com/lol/match/v5";
    String SUMMONER_MATCH_LIST_URI = "/matches/by-puuid";
    String SUMMONER_MATCH_DATA_URI = "/matches";

    Integer SOLO_RANK_QUEUE_ID = 420;

}
