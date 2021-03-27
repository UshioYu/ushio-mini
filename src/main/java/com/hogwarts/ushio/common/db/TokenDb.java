package com.hogwarts.ushio.common.db;

import com.hogwarts.ushio.dto.TokenDto;
import org.apache.http.util.TextUtils;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: ushio
 * @description:
 **/
@Component
public class TokenDb {

    //这里key传token值，即TokenDto里面的token变量值
    private Map<String, TokenDto> tokenMap = new HashMap<>();

    public int onLineUserSize(){
        return tokenMap.size();
    }

    public TokenDto getUserInfo(String token){
        if (TextUtils.isEmpty(token) || !tokenMap.containsKey(token)) {
            return null;
        }
        return tokenMap.get(token);
    }

    public void addUserInfo(String token,TokenDto tokenDto){
        if (!TextUtils.isEmpty(token)) {
            tokenMap.put(token, tokenDto);
        }
    }

    public TokenDto removeUserInfo(String token){
        if (TextUtils.isEmpty(token) || !tokenMap.containsKey(token)) {
            return null;
        }
        return tokenMap.remove(token);
    }

    public boolean isOnline(String token){
        if (TextUtils.isEmpty(token) || !tokenMap.containsKey(token)) {
            return false;
        }
        return tokenMap.get(token) != null;
    }

}
