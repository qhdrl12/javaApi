package com.springboot.svc;

import java.util.Map;

public interface MainService {
    Map<String, Object> readExcel(String file_name);
    Map<String, Object> convertBinaryRedisData(String key);
    Map<String, Object> oracleDataBaseFindAll(String table, int size, int page);

    Map<String, Object> searchSkpRef(String file_name, String id);
    void readWatcha();
}
