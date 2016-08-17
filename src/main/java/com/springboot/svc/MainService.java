package com.springboot.svc;

import java.util.Map;

/**
 * Created by ibong-gi on 2016. 8. 8..
 */

public interface MainService {
    Map<String, Object> readExcel(String file_name);
    Map<String, Object> convertBinaryRedisData(String key);
    Map<String, Object> oracleDataBaseFindAll(String table, int size, int page);
    void readWatcha();
}
