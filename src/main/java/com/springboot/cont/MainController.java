package com.springboot.cont;

import com.springboot.svc.MainServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

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
    public String searchRedis(@RequestParam(value = "key", required = false) String key){
        mainService.convertBinaryRedisData(key);
        return "index";
    }

    @RequestMapping(value="/oracleFindAll")
    public void oracleDataBaseFindAll(@RequestParam(value = "table", required = false) String table,
                                      @RequestParam(value = "size", defaultValue = "10") int size,
                                      @RequestParam(value = "page", defaultValue = "1") int page){

        mainService.oracleDataBaseFindAll(table, size, page);
    }

    @RequestMapping(value="/searchSkpRef")
    public ModelAndView searchSkpRef(
//    public String searchSkpRef(Model model,
                               @RequestParam(value = "file_name", required = false) String file_name,
                               @RequestParam(value = "id", required = false) String id){
        System.out.println(file_name);

        Map<String, Object> result = mainService.searchSkpRef(file_name, id);

//        result.remove("result");
//        model.addAttribute("result", result);

        return new ModelAndView("json", "xResult", result);
    }


//    @RequestMapping(value = "/allSkpDrama")
//    public ModelAndView dramaContentForSKP() {
//        Map<String, Object> result = new WeakHashMap<String, Object>();
//        result.put("drama_data", deliverySvc.getFullSKP(Common.CMS_DRAMA, Common.META_DRAMA, ""));
//
//        return new ModelAndView("json", "xResult", result);
//    }

}
