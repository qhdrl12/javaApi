package com.springboot.svc;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.redis.RedisService;
import com.springboot.repository.EmpRepository;
import com.springboot.run.ExcelMaker;
import org.apache.log4j.Logger;

import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import javax.xml.crypto.Data;

/**
 * Created by ibong-gi on 2016. 8. 8..
 */
@Service("mainService")
public class MainServiceImpl implements MainService {

    private static Logger logger = Logger.getLogger(MainService.class);

    @Autowired
    private RedisService redisService;

    @Autowired
    private EmpRepository empRepository;

    @Override
    public Map<String, Object> readExcel(String file_name) {

        Map<String, Object> result_map = new WeakHashMap<>();

        logger.info("call make excel...");
        JSONParser parser = new JSONParser();
        try{
            Object obj = parser.parse(new FileReader("/svc/smartDelivery/was/downloadData/" + file_name));
            ExcelMaker em = new ExcelMaker();

            em.setWorkbook(obj);

            SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
            File file = new File("/svc/smartDelivery/was/excelData/excel_" + sdf.format(new Date()) + ".xlsx");
//            File file = new File("C:/svc/files/excel_" + sdf.format(new Date()) + ".xlsx");
            FileOutputStream fileOut = new FileOutputStream(file);
//            workbook.write(fileOut);

            (em.getWorkbook()).write(fileOut);

            result_map.put("result", "ok");
        }catch(Exception e){
            e.printStackTrace();
            result_map.put("result", "fail");
        }

        return result_map;
    }

    @Override
    public Map<String, Object> convertBinaryRedisData(String key){
        Map<String, Object> result = new WeakHashMap<>();
        logger.info(redisService.get(key));
//        logger.info(redisService.bHget(key, key));

        return result;
    }

    @Override
    public void readWatcha(){
        ObjectMapper objMapper = new ObjectMapper();
        Map<String, Object> wData;
        List<Map<String, Object>> data;

        try{
            wData = (Map<String, Object>) objMapper.readValue(new File("/svc/smartDelivery/was/downloadData/WATCHA_MOVIES.json"), Object.class);
            data = (List<Map<String, Object>>) wData.get("data");

            for(Map<String, Object> movie : data) {
                List<String> contents_id = (List<String>) movie.get("skbbtv_ids");
                for(String con_id : contents_id){
                    List<String> simMovies = (List<String>) movie.get("similar_movies");
                    for(String sim : simMovies) {
                        System.out.println(con_id + " : " + sim);
                        Map<String, Object> simMovie = new HashMap<String, Object>();
                        simMovie.put("con_id", con_id);
                        simMovie.put("sim_mov_id", sim);
//                        watchaSimMovies.add(simMovie);
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void recursiveSearchObject(Object obj){
//        if()
    }

    public Map<String, Object> oracleDataBaseFindAll(String table){
        Map<String, Object> result_map = new HashMap<>();

        logger.info(empRepository.findAll());

        return result_map;
    }
}
