package cn.paohe.sys.service;

import cn.paohe.entity.model.sys.SystemParamEntity;
import cn.paohe.vo.framework.AjaxResult;

import java.util.List;

/**TODO 系统参数配置
 * @author 黄芝民
 * @date 2019/10/23 15:00
 * @version V1.0
 * @copyright 广东跑合中药材有限公司 Copyright (c) 2020
 */
public interface ISystemParamService {

    /**TODO 新增
     * @param systemParamEntity
     * @return AjaxResult
     * @author 黄芝民
     * @date 2019/10/23 16:38
     * @throws
     */
    AjaxResult add(SystemParamEntity systemParamEntity);

    /**TODO 修改
     * @param systemParamEntity
     * @return AjaxResult
     * @author 黄芝民
     * @date 2019/10/23 16:38
     * @throws
     */
    AjaxResult update(SystemParamEntity systemParamEntity);

    /**TODO 批量删除
     * @param
     * @return
     * @author 黄芝民
     * @date 2019/10/23 16:38
     * @throws
     */
    AjaxResult del(List<Long> ids);

    /**TODO 列表条件查询
     * @param systemParamEntity
     * @return AjaxResult
     * @author 黄芝民
     * @date 2019/10/23 1638
     * @throws
     */
    AjaxResult findListPage(SystemParamEntity systemParamEntity);

    /**TODO 根据条件获取参数配置
     * @param systemParamEntity
     * @return AjaxResult
     * @author: 黄芝民
     * @date: 2019年11月4日 上午11:07:46
     * @throws
     */
    public AjaxResult findListByCondition(SystemParamEntity systemParamEntity);

    /**TODO 根据条件获取参数配置
     * @param systemParamEntity
     * @return AjaxResult
     * @author: 黄芝民
     * @date: 2019年11月4日 上午11:07:46
     * @throws
     */
    public List<SystemParamEntity> findListByCond(SystemParamEntity systemParamEntity);


    /**TODO 查询全部系统参数
     * @param
     * @return AjaxResult
     * @author 黄芝民
     * @date 2019/11/5 10:06
     * @throws
     */
    AjaxResult findAll();

    /**TODO 查询当前类型下所有的系统参数配置
     * @param systemParamEntity
     * @return AjaxResult
     * @author 黄芝民
     * @date 2019/11/7 16:46
     * @throws
     */
    AjaxResult findListByType(SystemParamEntity systemParamEntity);

    /**TODO 查询当前类型下的系统参数配置
     * @param systemParamEntity
     * @return AjaxResult
     * @author 黄芝民
     * @date 2019/11/7 16:46
     * @throws
     */
    SystemParamEntity findSystemParamByType(SystemParamEntity systemParamEntity);
}
