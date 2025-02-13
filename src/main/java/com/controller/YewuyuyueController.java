package com.controller;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Date;
import java.util.List;
import java.util.Collections;

import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import com.utils.ValidatorUtils;
import com.utils.DeSensUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.annotation.IgnoreAuth;
import com.annotation.SysLog;

import com.entity.YewuyuyueEntity;
import com.entity.view.YewuyuyueView;

import com.service.YewuyuyueService;
import com.service.TokenService;
import com.utils.PageUtils;
import com.utils.R;
import com.utils.EncryptUtil;
import com.utils.MPUtil;
import com.utils.MapUtils;
import com.utils.CommonUtil;
import java.io.IOException;

/**
 * 业务预约
 * 后端接口
 * @author 
 * @email 
 * @date 2025-01-21 11:29:59
 */
@RestController
@RequestMapping("/yewuyuyue")
public class YewuyuyueController {
    @Autowired
    private YewuyuyueService yewuyuyueService;






    



    /**
     * 后台列表
     */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params,YewuyuyueEntity yewuyuyue,
                @RequestParam(required = false) @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date banlishijianstart,
                @RequestParam(required = false) @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date banlishijianend,
		HttpServletRequest request){
		String tableName = request.getSession().getAttribute("tableName").toString();
		if(tableName.equals("yonghu")) {
			yewuyuyue.setYonghuzhanghao((String)request.getSession().getAttribute("username"));
		}
        EntityWrapper<YewuyuyueEntity> ew = new EntityWrapper<YewuyuyueEntity>();
                if(banlishijianstart!=null) ew.ge("banlishijian", banlishijianstart);
                if(banlishijianend!=null) ew.le("banlishijian", banlishijianend);



		PageUtils page = yewuyuyueService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, yewuyuyue), params), params));
				Map<String, String> deSens = new HashMap<>();
				DeSensUtil.desensitize(page,deSens);
        return R.ok().put("data", page);
    }
    
    /**
     * 前台列表
     */
	@IgnoreAuth
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params,YewuyuyueEntity yewuyuyue, 
                @RequestParam(required = false) @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date banlishijianstart,
                @RequestParam(required = false) @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date banlishijianend,
		HttpServletRequest request){
        EntityWrapper<YewuyuyueEntity> ew = new EntityWrapper<YewuyuyueEntity>();
                if(banlishijianstart!=null) ew.ge("banlishijian", banlishijianstart);
                if(banlishijianend!=null) ew.le("banlishijian", banlishijianend);

		PageUtils page = yewuyuyueService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, yewuyuyue), params), params));
		
				Map<String, String> deSens = new HashMap<>();
				DeSensUtil.desensitize(page,deSens);
        return R.ok().put("data", page);
    }



	/**
     * 列表
     */
    @RequestMapping("/lists")
    public R list( YewuyuyueEntity yewuyuyue){
       	EntityWrapper<YewuyuyueEntity> ew = new EntityWrapper<YewuyuyueEntity>();
      	ew.allEq(MPUtil.allEQMapPre( yewuyuyue, "yewuyuyue")); 
        return R.ok().put("data", yewuyuyueService.selectListView(ew));
    }

	 /**
     * 查询
     */
    @RequestMapping("/query")
    public R query(YewuyuyueEntity yewuyuyue){
        EntityWrapper< YewuyuyueEntity> ew = new EntityWrapper< YewuyuyueEntity>();
 		ew.allEq(MPUtil.allEQMapPre( yewuyuyue, "yewuyuyue")); 
		YewuyuyueView yewuyuyueView =  yewuyuyueService.selectView(ew);
		return R.ok("查询业务预约成功").put("data", yewuyuyueView);
    }
	
    /**
     * 后台详情
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        YewuyuyueEntity yewuyuyue = yewuyuyueService.selectById(id);
				Map<String, String> deSens = new HashMap<>();
				DeSensUtil.desensitize(yewuyuyue,deSens);
        return R.ok().put("data", yewuyuyue);
    }

    /**
     * 前台详情
     */
	@IgnoreAuth
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id){
        YewuyuyueEntity yewuyuyue = yewuyuyueService.selectById(id);
				Map<String, String> deSens = new HashMap<>();
				DeSensUtil.desensitize(yewuyuyue,deSens);
        return R.ok().put("data", yewuyuyue);
    }
    



    /**
     * 后台保存
     */
    @RequestMapping("/save")
    @SysLog("新增业务预约") 
    public R save(@RequestBody YewuyuyueEntity yewuyuyue, HttpServletRequest request){
    	//ValidatorUtils.validateEntity(yewuyuyue);
        yewuyuyueService.insert(yewuyuyue);
        return R.ok().put("data",yewuyuyue.getId());
    }
    
    /**
     * 前台保存
     */
    @SysLog("新增业务预约")
    @RequestMapping("/add")
    public R add(@RequestBody YewuyuyueEntity yewuyuyue, HttpServletRequest request){
    	//ValidatorUtils.validateEntity(yewuyuyue);
        yewuyuyueService.insert(yewuyuyue);
        return R.ok().put("data",yewuyuyue.getId());
    }





    /**
     * 修改
     */
    @RequestMapping("/update")
    @Transactional
    @SysLog("修改业务预约")
    public R update(@RequestBody YewuyuyueEntity yewuyuyue, HttpServletRequest request){
        //ValidatorUtils.validateEntity(yewuyuyue);
        //全部更新
        yewuyuyueService.updateById(yewuyuyue);

        return R.ok();
    }



    

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @SysLog("删除业务预约")
    public R delete(@RequestBody Long[] ids){
        yewuyuyueService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }
    
	












}
