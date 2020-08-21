package cn.paohe.service;

import java.util.List;

import cn.paohe.model.RouterConfig;
import cn.paohe.vo.framework.AjaxResult;
import cn.paohe.vo.framework.PageAjax;

public interface RouterConfigService {

    public AjaxResult insertRouterConfig(RouterConfig rc);

    public AjaxResult updateRouterConfig(RouterConfig rc);

    public AjaxResult deleteRouterConfig(String id);

    public PageAjax<RouterConfig> queryRouterConfigPage(RouterConfig rc, PageAjax<RouterConfig> page);

    public AjaxResult searchRouterConfig(String id);

    public List<RouterConfig> searchAllRouterConfig();

}
