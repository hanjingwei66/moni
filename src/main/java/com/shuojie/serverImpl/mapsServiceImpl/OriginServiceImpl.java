package com.shuojie.serverImpl.mapsServiceImpl;

import com.shuojie.dao.mapsMapper.OriginMapper;
import com.shuojie.domain.maps.Origin;
import com.shuojie.service.mapsService.OriginService;
import com.shuojie.utils.vo.ReturnOrigin;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service("originServiceImpl")
public class OriginServiceImpl implements OriginService {
    @Resource
    private OriginMapper originMapper;

    private ReturnOrigin returnOrigin;

    //根据orName查询经纬度
    @Override
    public ReturnOrigin getByName(Origin origin) {
        ArrayList o = originMapper.getByName(origin);
        ReturnOrigin returnOrigin= new ReturnOrigin();
        returnOrigin.setOrName(getOriginName());
        returnOrigin.setOriginAndLine(o);
        return returnOrigin;
    }

    //查询orName
    public ArrayList getOriginName(){
        ArrayList originName = originMapper.getOriginName();

        return originName;
    }

    //查询线路名称和线路经纬度
    @Override
    public List<Origin> getOriginLine() {
        List<Origin> originLine = originMapper.getOriginLine();
        return originLine;
    }

}