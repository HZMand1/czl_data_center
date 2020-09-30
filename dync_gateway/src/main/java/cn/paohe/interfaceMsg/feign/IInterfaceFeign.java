package cn.paohe.interfaceMsg.feign;

import cn.paohe.vo.InterfaceInfoVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

/**
 * TODO
 *
 * @Author 黄芝民
 * @Date 2020/9/29 16:41
 * @Version V1.0
 * @Copyright 广东跑合中药材有限公司 Copyright (c) 2020
 **/
@FeignClient(value = "config-center")
public interface IInterfaceFeign {

    @PostMapping("/czl/rest/data/center/interface/getInterfaceList")
    public List<InterfaceInfoVo> getInterfaceList(InterfaceInfoVo interfaceInfoVo);
}
