package com.springboot.svc;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.redis.RedisService;
import com.springboot.run.ExcelMaker;
import org.apache.log4j.Logger;

import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by ibong-gi on 2016. 8. 8..
 */
@Service("mainService")
public class MainServiceImpl implements MainService {

    private static Logger logger = Logger.getLogger(MainService.class);

    @Autowired
    private RedisService redisService;

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

//        logger.info(redisService.get(key));
        logger.info(redisService.bHget(key, key));

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

    @Override
    public Map<String, Object> dijkstraAlgorithm(){
        Map<String, Object> result = new WeakHashMap<>();

        Scanner sc = new Scanner(System.in);
        int[][] matrix = new int[5][5];
        int[] distance = new int[5];
        int[] visited = new int[5];
        int[] preD = new int[5];
        int min;
        int nextNode = 0;
        System.out.println("Enter the matrix : ");

        for(int i=0; i<5; ++i){
            visited[i] = 0;
            preD[i] = 0;
            for(int j=0; j<5; ++j){
                matrix[i][j] = sc.nextInt();
                if(matrix[i][j] == 0) matrix[i][j] = 999;
            }
        }

        distance = matrix[0];
        distance[0] = 0;
        visited[0] = 1;

        for(int i=0; i<5; ++i){
            min=999;
            for(int j=0; j<5; ++j){
                if(min > distance[j] && visited[j] != 1){
                    min = distance[j];
                    nextNode = j;
                }
            }

            visited[nextNode] = 1;

            for(int c=0; c<5; ++c){
                if(visited[c] != 1){
                    if(min+matrix[nextNode][c] < distance[c]){
                        distance[c] = min + matrix[nextNode][c];
                        preD[c] = nextNode;
                    }
                }
            }
        }

        for(int i=0; i<5; ++i){
            System.out.println("|" + distance[i]);
        }
        System.out.println("|");

        for(int i=0; i<5; ++i){
            int j;
            System.out.println("Path = " + i);
            j=i;
            do{
                j=preD[j];
                System.out.println("<-" + j);
            }while(j!=0);

            System.out.println();
        }

        return result;
    }
}
