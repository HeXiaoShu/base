package com.service.impl;

import com.mapper.CityMapper;
import com.model.City;
import com.service.CityService;
import com.tk.TkService;
import com.tk.TkServiceImpl;
import org.springframework.stereotype.Service;

/**
 * IUserService
 * @author Hexiaoshu
 * @date 2020-11-28 18:11:57
 */
@Service("cityService")
public class CityServiceImpl extends TkServiceImpl<City, CityMapper> implements CityService {


}