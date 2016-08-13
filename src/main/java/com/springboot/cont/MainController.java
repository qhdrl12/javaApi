package com.springboot.cont;

import com.springboot.svc.MainServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by ibong-gi on 2016. 7. 27..
 */
@Controller
public class MainController {

    @Autowired
    private MainServiceImpl mainService;

    @RequestMapping(value="/")
    public String index(Model model){
        model.addAttribute("test" , "thisIsTestCode");
        return "index";
    }

    @RequestMapping(value="/readExcelFile")
    public String readExcel(Model model,
            @RequestParam(value = "file_name", required = false) String file_name){
        model.addAttribute("title", "Excel View");

        mainService.readExcel(file_name);

        return "excel";
    }

    @RequestMapping(value="/readWatcha")
    public void readWatcha(){
        mainService.readWatcha();
    }

    @RequestMapping(value="/searchRedis")
    public void searchRedis(@RequestParam(value = "key", required = false) String key){
        mainService.convertBinaryRedisData(key);
    }

    @RequestMapping(value="/dijkstra")
    public String dijkstra(){
        mainService.dijkstraAlgorithm();
        return "index";
    }
}