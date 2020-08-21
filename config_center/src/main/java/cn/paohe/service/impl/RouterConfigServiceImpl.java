package cn.paohe.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.toolkit.StringUtils;
import com.github.pagehelper.page.PageMethod;

import cn.paohe.base.utils.check.AppUtil;
import cn.paohe.components.feign.DyncRouterTrigger;
import cn.paohe.dao.RouterConfigMapper;
import cn.paohe.model.RouterConfig;
import cn.paohe.service.RouterConfigService;
import cn.paohe.util.basetype.ObjectUtils;
import cn.paohe.vo.framework.AjaxResult;
import cn.paohe.vo.framework.PageAjax;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example.Criteria;

@Service("RouterConfigService")
public class RouterConfigServiceImpl implements RouterConfigService {

    @Autowired
    private RouterConfigMapper routerConfigMapper;

    @Autowired
    private DyncRouterTrigger dyncRouterTrigger;

    /**
     * 插入路由配置
     */
    @Override
    public AjaxResult insertRouterConfig(RouterConfig rc) {
        AjaxResult ar = null;
        int counter = routerConfigMapper.insertSelective(rc);
        if (counter > 0) {
            ar = new AjaxResult(1, "Successfully insert routing configuration");
            dyncRouterTrigger.getRouterReflush();
        } else {
            ar = new AjaxResult(0, "Route configuration failed");
        }
        return ar;
    }

    /**
     * 更新路由配置
     */
    @Override
    public AjaxResult updateRouterConfig(RouterConfig rc) {
        AjaxResult ar = null;
        int counter = routerConfigMapper.updateByPrimaryKeySelective(rc);
        if (counter > 0) {
            ar = new AjaxResult(1, "Successfully update routing configuration");
            dyncRouterTrigger.getRouterReflush();
        } else {
            ar = new AjaxResult(0, "Route configuration failed");
        }
        return ar;
    }

    /**
     * 根据id删除路由配置
     */
    @Override
    public AjaxResult deleteRouterConfig(String id) {
        AjaxResult ar = null;
        int counter = routerConfigMapper.deleteByPrimaryKey(id);
        if (counter > 0) {
            ar = new AjaxResult(1, "The routing configuration was successfully deleted");
            dyncRouterTrigger.getRouterReflush();
        } else {
            ar = new AjaxResult(0, "Route configuration deletion failed");
        }
        return ar;
    }

    /**
     * 分页查询路由配置信息
     */
    @Override
    public PageAjax<RouterConfig> queryRouterConfigPage(RouterConfig rc, PageAjax<RouterConfig> page) {
        PageMethod.startPage(page.getPageNo(), page.getPageSize());

        Condition condition = new Condition(RouterConfig.class);
        Criteria criteria = condition.createCriteria();

        if (null != rc && StringUtils.isNotEmpty(rc.getId())) {
            criteria.andEqualTo("id", rc.getId());
        }
        if (null != rc && StringUtils.isNotEmpty(rc.getInterfaceName())) {
            criteria.andLike("interfaceName", "%" + rc.getInterfaceName() + "%");
        }
        if (null != rc && StringUtils.isNotEmpty(rc.getContextName())) {
            criteria.andLike("contextName", "%" + rc.getContextName() + "%");
        }
        if (null != rc && StringUtils.isNotEmpty(rc.getDescription())) {
            criteria.andLike("description", "%" + rc.getDescription() + "%");
        }
        if (null != rc && StringUtils.isNotEmpty(rc.getMappingPath())) {
            criteria.andLike("mappingPath", "%" + rc.getMappingPath() + "%");
        }
        if (null != rc && StringUtils.isNotEmpty(rc.getStatus())) {
            criteria.andEqualTo("status", rc.getStatus());
        }
        List<RouterConfig> list = routerConfigMapper.selectByCondition(condition);
        return AppUtil.returnPage(list);
    }

    /**
     * 通过id值查询路由配置信息
     */
    @Override
    public AjaxResult searchRouterConfig(String id) {
        AjaxResult ar = null;
        RouterConfig rc = routerConfigMapper.selectByPrimaryKey(id);
        if (!ObjectUtils.isNullObj(rc)) {
            ar = new AjaxResult(1, "Route configuration query succeeded", rc);
        } else {
            ar = new AjaxResult(0, "No routing data information was queried based on id");
        }
        return ar;
    }

    /**
     * 查询所有路由信息
     */
    @Override
    public List<RouterConfig> searchAllRouterConfig() {
        return routerConfigMapper.selectAll();
    }

}
