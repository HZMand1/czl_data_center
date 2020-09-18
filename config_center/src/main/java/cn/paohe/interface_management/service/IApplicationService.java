package cn.paohe.interface_management.service;

import cn.paohe.entity.model.InterfaceMag.ApplicationInfo;
import cn.paohe.vo.framework.PageAjax;

import java.util.List;

/**
 * TODO
 *
 * @Author 黄芝民
 * @Date 2020/9/16 9:13
 * @Version V1.0
 * @Copyright 广东跑合中药材有限公司 Copyright (c) 2020
 **/
public interface IApplicationService {

    /**
     * TODO 新增应用
     * @Param:   applicationInfo
     * @return:  int
     * @author:  黄芝民
     * @Date:    2020/9/16 14:29
     * @throws:
     */
    public int insertApp(ApplicationInfo applicationInfo);

    /**
     * TODO 根据ID修改应用信息
     * @Param:   applicationInfo
     * @return:  int
     * @author:  黄芝民
     * @Date:    2020/9/16 14:30
     * @throws:
     */
    public int updateAppById(ApplicationInfo applicationInfo);

    /**
     * TODO 逻辑删除应用
     * @Param:   applicationInfo
     * @return:  int
     * @author:  黄芝民
     * @Date:    2020/9/16 14:30
     * @throws:
     */
    public int enableAppById(ApplicationInfo applicationInfo);

    /**
     * TODO 删除应用
     * @Param:   applicationInfo
     * @return:  int
     * @author:  黄芝民
     * @Date:    2020/9/16 14:30
     * @throws:
     */
    public int deleteAppById(ApplicationInfo applicationInfo);

    /**
     * TODO 根据应用ID获取信息
     * @Param:   applicationInfo
     * @return:  applicationInfo
     * @author:  黄芝民
     * @Date:    2020/9/16 14:37
     * @throws:
     */
    public ApplicationInfo queryAppById(ApplicationInfo applicationInfo);

    /**
     * TODO 查询全部应用信息
     * @Param:   applicationInfo
     * @return:  list
     * @author:  黄芝民
     * @Date:    2020/9/16 14:32
     * @throws:
     */
    public List<ApplicationInfo> queryAppList(ApplicationInfo applicationInfo);

    /**
     * TODO 分页查询应用信息
     * @Param:   applicationInfo
     * @return:  pageAjax
     * @author:  黄芝民
     * @Date:    2020/9/16 14:33
     * @throws:
     */
    public PageAjax<ApplicationInfo> queryPageAppList(ApplicationInfo applicationInfo);
}
