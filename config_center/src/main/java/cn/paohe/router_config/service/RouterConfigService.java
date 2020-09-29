package cn.paohe.router_config.service;

import cn.paohe.entity.model.routeConfig.RouterConfig;
import cn.paohe.vo.framework.AjaxResult;
import cn.paohe.vo.framework.PageAjax;

import java.util.List;

public interface RouterConfigService {

    public AjaxResult insertRouterConfig(RouterConfig rc);

    public AjaxResult updateRouterConfig(RouterConfig rc);

    public AjaxResult deleteRouterConfig(String id);

    public PageAjax<RouterConfig> queryRouterConfigPage(RouterConfig rc, PageAjax<RouterConfig> page);

    public AjaxResult searchRouterConfig(String id);

    public List<RouterConfig> searchRouterConfigByName(String name);

    public List<RouterConfig> searchAllRouterConfig();

}
