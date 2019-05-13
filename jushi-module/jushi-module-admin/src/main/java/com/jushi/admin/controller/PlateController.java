package com.jushi.admin.controller;

import com.jushi.admin.pojo.po.Plate;
import com.jushi.admin.service.PlateService;
import com.jushi.api.pojo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/plate")
public class PlateController {
    @Autowired
    private PlateService plateService;

    @GetMapping("/getTop10")
    public Result<List<Plate>> PlateController(){
        return plateService.findTop10();
    }
}
