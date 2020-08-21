package cn.paohe.service;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.paohe.Application;
import cn.paohe.base.utils.encryption.UUIDGenerator;
import cn.paohe.model.RouterConfig;
import cn.paohe.util.basetype.ObjectUtils;
import cn.paohe.vo.framework.AjaxResult;
import cn.paohe.vo.framework.PageAjax;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestDyncRouter {

    @Autowired
    private RouterConfigService routerConfigService;

    private RouterConfig rc = null;

    @Before
    public void setupData() {
        rc = new RouterConfig();
        rc.setId(UUIDGenerator.getUUID());
        rc.setInterfaceName("paohe_route1");
        rc.setContextName("/paohe/**");
        rc.setDescription("通过转发进行页面跳转");
        rc.setMappingPath("http://www.paohe.cn");
        rc.setStatus("1");
    }

    @Test
    public void AinsertRouterConfig() {
        AjaxResult ar = routerConfigService.insertRouterConfig(rc);
        assertEquals(1, ar.getRetcode());
    }

    // @Test
    public void BupdateRouterConfig() {
        PageAjax<RouterConfig> page = new PageAjax<>();
        page.setPageSize(10);
        page.setPageNo(0);

        rc = new RouterConfig();
        rc.setStatus("1");
        PageAjax<RouterConfig> pageAjax = routerConfigService.queryRouterConfigPage(rc, page);
        if (!ObjectUtils.isNullObj(pageAjax)) {
            List<RouterConfig> list = pageAjax.getRows();
            if (!ObjectUtils.isNullObj(list)) {
                RouterConfig rcTmp = list.get(0);
                rcTmp.setInterfaceName("go2taobao");
                rcTmp.setMappingPath("http://www.taobao.com");

                AjaxResult ar = routerConfigService.updateRouterConfig(rcTmp);
                assertEquals(1, ar.getRetcode());
            }
        }
    }

    // @Test
    public void CdeleteRouterConfig() {
        PageAjax<RouterConfig> page = new PageAjax<>();
        page.setPageSize(10);
        page.setPageNo(0);

        rc = new RouterConfig();
        rc.setStatus("1");
        PageAjax<RouterConfig> pageAjax = routerConfigService.queryRouterConfigPage(rc, page);
        if (!ObjectUtils.isNullObj(pageAjax)) {
            List<RouterConfig> list = pageAjax.getRows();
            if (!ObjectUtils.isNullObj(list)) {
                RouterConfig rcTmp = list.get(0);
                rcTmp.setStatus("0");
                AjaxResult ar = routerConfigService.updateRouterConfig(rcTmp);
                assertEquals(1, ar.getRetcode());
            }
        }
    }

}
