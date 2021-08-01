package com.senderman.bplpaybot.bis;

import com.senderman.bplpaybot.bis.entity.Result;
import com.senderman.bplpaybot.bis.entity.User;
import com.senderman.bplpaybot.bis.exception.InternalServerErrorException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

@Service
public class BplIdSystemImpl implements BplIdSystem {

    private final RestTemplate restTemplate;
    private final String bplToken;
    private final String bplUrl;

    private final ParameterizedTypeReference<Result<User>> responseTypeRef;

    public BplIdSystemImpl(
            RestTemplate restTemplate,
            @Value("${bpl-token}") String bplToken,
            @Value("${bpl-url}") String bplUrl

    ) {
        this.restTemplate = restTemplate;
        this.bplToken = bplToken;
        this.bplUrl = bplUrl;
        this.responseTypeRef = new ParameterizedTypeReference<>() {
        };
    }

    @Override
    public User getUser(long id) throws InternalServerErrorException {
        var params = new HashMap<String, String>();
        params.put("tg_id", String.valueOf(id));
        params.put("auth_token", bplToken);
        var resp = restTemplate.exchange(
                bplUrl + "get_user_by_tg_id/{tg_id}?auth_token={auth_token}",
                HttpMethod.GET,
                null,
                responseTypeRef,
                params);
        if (resp.getStatusCodeValue() != 200) {
            throw new InternalServerErrorException();
        }
        return resp.getBody().getResult();
    }

    @Override
    public void increaseMoney(long id, int amount) throws InternalServerErrorException {
        var params = new HashMap<String, String>();
        params.put("telegram", String.valueOf(id));
        params.put("auth_token", bplToken);
        params.put("count", String.valueOf(amount));
        var resp = restTemplate.exchange(
                // TODO изменить адрес когда этот долбаеб сделает метод
                // изменил
                bplUrl + "inc_money_tg/?auth_token={auth_token}&count={count}&telegram={telegram}",
                HttpMethod.GET,
                null,
                responseTypeRef,
                params);
        if (resp.getStatusCodeValue() != 200) {
            throw new InternalServerErrorException();
        }
    }
}
