package cn.com.wenyi.sprocr.controller;

import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wyiss on 2017/1/16.
 */
@RestController
@RequestMapping("retail")
public class RetailAdminController {
    @RequestMapping(value = "", method = RequestMethod.GET)
    public Object hello(HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Content-Type", "charset=utf-8");
        return "Hello!";
    }

    @RequestMapping(value = "requirements", method = RequestMethod.GET)
    public Object crawlers(HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Content-Type", "charset=utf-8");
        Map a = new HashMap();
        a.put("id", 1);
        a.put("name", "test");
        a.put("startTime", "20160112");
        a.put("endTime", "20160120");
        a.put("status", 1);
        a.put("active", true);
        a.put("description", "aaaa");
        List b = new ArrayList();
        b.add(a);
        Map result = new HashMap();
        result.put("data", b);
        return result;
    }

    @RequestMapping(value = "requirements", method = {RequestMethod.POST})
    public Object save(HttpServletResponse response, @RequestBody Map map) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Content-Type", "charset=utf-8");
        System.out.println(map);
        return map;
    }

    @RequestMapping(value = "requirements", method = {RequestMethod.OPTIONS})
    public Object check(HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Content-Type", "charset=utf-8");
        return 0;
    }

}
