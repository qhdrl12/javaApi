package com.springboot.svc;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.domain.CmsCon;
import com.springboot.domain.SmdRelCon;
import com.springboot.redis.RedisService;
import com.springboot.repository.CmsConRepository;
import com.springboot.repository.SmdRelConRepository;
import com.springboot.run.ExcelMaker;
import org.apache.log4j.Logger;

import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

/**
 * Created by ibong-gi on 2016. 8. 8..
 */
@Service("mainService")
public class MainServiceImpl implements MainService {

    private static Logger logger = Logger.getLogger(MainService.class);

    @Autowired
    private RedisService redisService;

//    @Autowired
//    private EmpRepository empRepository;

    @Autowired
    private SmdRelConRepository smdRelConRepository;

    @Autowired
    private CmsConRepository cmsConRepository;

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

    public Map<String, Object> oracleDataBaseFindAll(String table, int size, int page){
        Map<String, Object> result_map = new HashMap<>();


//        List<SmdRelCon> list_map = smdRelConRepository.findAll();

        Page<SmdRelCon> list_map = smdRelConRepository.findAll(new PageRequest(page-1 , size));

        printPageData("pageBasic", list_map);

        return result_map;
    }

    @Override
    public Map<String, Object> searchSkpRef(String file_name, String id) {
        Map<String, Object> result_map = new WeakHashMap<>();
        Map<String, Object> ref_map = new HashMap<>();
        Map<String, Object> vcms_map = new HashMap<>();
        Map<String, Object> sub_map;

        List<Map<String, Object>> conList;
        JSONParser parser = new JSONParser();

        List<String> paramList = new ArrayList<String>();

        paramList.add("100");
        paramList.add("200");

//        List<CmsCon> cmsConList = cmsConRepository.findMappingId(paramList);
        List<CmsCon> cmsConList = cmsConRepository.findMappingIntg(paramList);
        logger.info("cms total size : " + cmsConList.size());

        for(CmsCon cmsCon : cmsConList){
            vcms_map.put(cmsCon.getCID(), cmsCon);
        }

        cmsConList.clear();

        try{
            Object obj = parser.parse(new FileReader("/svc/smartDelivery/was/downloadData/" + file_name));
            Map<String, Object> objMap = (Map<String, Object>)obj;

            List<Map<String, Object>> objList = (ArrayList<Map<String, Object>>)objMap.get("c2c");
            String con_id;
            String rel_id;
            CmsCon tempCmsCon;
            for(Map<String, Object> tempMap : objList){
                conList = new ArrayList<>();
                con_id = (String)tempMap.get("id_contents");

                for(Map<String, Object> tempSubMap : (List<Map<String, Object>>)tempMap.get("contents")){
                    sub_map = new HashMap<>();
                    rel_id = (String)tempSubMap.get("id_contents");
                    tempCmsCon = (CmsCon)vcms_map.get(rel_id);
                    if(tempCmsCon != null) {
                        sub_map.put("id_contents", tempSubMap.get("id_contents"));
                        sub_map.put("score", tempSubMap.get("score"));
                        sub_map.put("title", tempCmsCon.getTitle());
                        conList.add(sub_map);
                    }
                }
                ref_map.put(con_id, conList);

//                redisService.hset("skpMovieRef", con_id, conList);
            }
            redisService.hmset("skpMovieRef", ref_map);

            result_map.put("size", ref_map.size());
            result_map.put("result", "ok");
        }catch(Exception e){
            e.printStackTrace();
            result_map.put("result", "fail");
        }finally {
            ref_map.clear();
            vcms_map.clear();
        }
        return result_map;
    }

    private void printPageData(String label, Page<SmdRelCon> page){
        logger.info("[" + label + "] page  : " + page + " , page_cnt : " + page.getSize());
        List<Map<String, Object>> propsList = new ArrayList<>();
        Map<String, Object> props;
        if( page == null || page.getSize() <= 0 ) return;

        for( int i = 0 ; i < page.getSize(); i++ ){
            props = new WeakHashMap<>();
            SmdRelCon smdRelCon = page.getContent().get(i);

            logger.info("smdRelCon : " + smdRelCon.getCID() + " : " + smdRelCon.getRID() + " : " + smdRelCon.getFgCd() + " : " + smdRelCon.getRelScore());
        }
    }

}
