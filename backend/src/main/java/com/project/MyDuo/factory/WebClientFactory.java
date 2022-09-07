package com.project.MyDuo.factory;

import com.project.MyDuo.Const.LoLConst;
import com.project.MyDuo.customException.riotServerError;
import com.project.MyDuo.customException.summonerNotFoundException;
import com.project.MyDuo.customException.tooManyRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j @Configuration
public class WebClientFactory {
    static ExchangeFilterFunction loggingFilter = (request, next) -> {
        log.warn("{}",request.url().getPath());
        return next.exchange(request);
    };

    @Bean(name = "basicInfoWebClient")
    public WebClient getLoLBasicAccountRequestClient() {
        return WebClient.builder()
                .filter(errorResponseFilter())
                .baseUrl(LoLConst.SUMMONER_BASIC_BASE_URL)
                .defaultHeader(LoLConst.API_KEY_NAME, LoLConst.API_KEY)
                .build();
    }

    @Bean(name = "detailInfoWebClient")
    public WebClient getLoLDetailAccountRequestClient() {
        return WebClient.builder()
                .baseUrl(LoLConst.SUMMONER_DETAIL_BASE_URL)
                .defaultHeader(LoLConst.API_KEY_NAME, LoLConst.API_KEY)
                .build();
    }

    @Bean(name = "matchIdListCWebClient")
    public WebClient getLoLMatchIdListRequestClient() {
        return WebClient.builder()
                .baseUrl(LoLConst.SUMMONER_MATCH_LIST_BASE_URL)
                //.filter(loggingFilter)
                .defaultHeader(LoLConst.API_KEY_NAME, LoLConst.API_KEY)
                .build();
    }

    @Bean(name = "matchDataWebClient")
    public WebClient getMatchDataRequestClient() {
        return WebClient.builder()
                .baseUrl(LoLConst.SUMMONER_MATCH_LIST_BASE_URL)
                .filter(loggingFilter)
                .defaultHeader(LoLConst.API_KEY_NAME, LoLConst.API_KEY)
                .build();
    }

    public ExchangeFilterFunction errorResponseFilter() {
        return ExchangeFilterFunction.ofResponseProcessor(response -> {
            HttpStatus status = response.statusCode();

            if (status.equals(HttpStatus.TOO_MANY_REQUESTS))
                return response.bodyToMono(String.class)
                        .flatMap(body -> Mono.error(new tooManyRequestException("서버 내 요청 초과로 인해 잠시 후 다시 시도해 주십시오.")));
            if (status.equals(HttpStatus.NOT_FOUND))
                return response.bodyToMono(String.class)
                        .flatMap(body -> Mono.error(new summonerNotFoundException("해당 소환사의 정보를 찾을 수 없습니다.")));
            if (status.is5xxServerError())
                return response.bodyToMono(String.class)
                        .flatMap(body -> Mono.error(new riotServerError("riotAPI 서버 문제로 인해 잠시 후 다시 시도해 주시기 바랍니다.")));

            return Mono.just(response);
        });
    }
}
