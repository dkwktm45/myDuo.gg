package com.project.MyDuo.factory;

import com.project.MyDuo.Const.LoLConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;

@Component @Slf4j
public class WebClientFactory {
    static ExchangeFilterFunction loggingFilter = (request, next) -> {
        log.warn("{}",request.url().getPath());
        return next.exchange(request);
    };

    public WebClient getLoLBasicAccountRequestClient() {
        return WebClient.builder()
                .baseUrl(LoLConst.SUMMONER_BASIC_BASE_URL)
                .defaultHeader(LoLConst.API_KEY_NAME, LoLConst.API_KEY)
                .build();
    }

    public WebClient getLoLDetailAccountRequestClient() {
        return WebClient.builder()
                .baseUrl(LoLConst.SUMMONER_DETAIL_BASE_URL)
                .defaultHeader(LoLConst.API_KEY_NAME, LoLConst.API_KEY)
                .build();
    }

    public WebClient getLoLMatchIdListRequestClient() {
        return WebClient.builder()
                .baseUrl(LoLConst.SUMMONER_MATCH_LIST_BASE_URL)
                //.filter(loggingFilter)
                .defaultHeader(LoLConst.API_KEY_NAME, LoLConst.API_KEY)
                .build();
    }

    public WebClient getMatchDataRequestClient() {
        return WebClient.builder()
                .baseUrl(LoLConst.SUMMONER_MATCH_LIST_BASE_URL)
                .filter(loggingFilter)
                .defaultHeader(LoLConst.API_KEY_NAME, LoLConst.API_KEY)
                .build();
    }
}
