package cn.paohe.interface_management.service;

import cn.paohe.entity.model.InterfaceMag.AppSourceInterInfo;
import cn.paohe.entity.vo.interfaceMag.AppSourceInterInfoVo;
import cn.paohe.vo.framework.PageAjax;

import java.util.List;

/**
 * TODO
 *
 * @Author 黄芝民
 * @Date 2020/10/20 13:57
 * @Version V1.0
 * @Copyright 广东跑合中药材有限公司 Copyright (c) 2020
 **/
public interface IAppSourceInterService {

    /**
     * TODO 新增应用接口关系
     * @Param:   appSourceInterInfo
     * @return:  int
     * @author:  黄芝民
     * @Date:    2020/9/16 14:29
     * @throws:
     */
    public int insertAppInterface(AppSourceInterInfo appSourceInterInfo);

    /**
     * TODO 新增应用接口关系
     * @Param:   appSourceInterInfo
     * @return:  int
     * @author:  黄芝民
     * @Date:    2020/9/16 14:29
     * @throws:
     */
    public int insertAppInterfaceList(List<AppSourceInterInfo> appSourceInterInfoList);

    /**
     * TODO 删除应用接口关系
     * @Param:   applicationInfo
     * @return:  int
     * @author:  黄芝民
     * @Date:    2020/9/16 14:30
     * @throws:
     */
    public int deleteAppInterfaceById(AppSourceInterInfo appSourceInterInfo);

    /**
     * TODO 分页查询应用接口信息
     * @Param:   applicationInfo
     * @return:  pageAjax
     * @author:  黄芝民
     * @Date:    2020/9/16 14:33
     * @throws:
     */
    public PageAjax<AppSourceInterInfoVo> queryPageAppInterface(AppSourceInterInfoVo appSourceInterInfoVo);

    /**
     * TODO 分页查询统计的应用接口信息
     * @Param:   applicationInfo
     * @return:  pageAjax
     * @author:  黄芝民
     * @Date:    2020/9/16 14:33
     * @throws:
     */
    public PageAjax<AppSourceInterInfoVo> queryCountPageAppInterface(AppSourceInterInfoVo appSourceInterInfoVo);

}
