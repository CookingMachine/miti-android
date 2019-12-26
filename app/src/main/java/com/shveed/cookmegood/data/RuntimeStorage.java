package com.shveed.cookmegood.data;

import java.util.ArrayList;
import java.util.List;

public class RuntimeStorage {

    private static RuntimeStorage instance;

    public List<String> categories = new ArrayList<>();

    public static RuntimeStorage newInstance(){
        if(instance == null){
            instance = new RuntimeStorage();
        }
        return instance;
    }


}
