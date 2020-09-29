package cn.paohe.router_config.components.perexec;

import cn.paohe.entity.model.routeConfig.RouterConfig;
import cn.paohe.router_config.service.RouterConfigService;
import cn.paohe.util.basetype.ObjectUtils;
import cn.paohe.util.cache.RedisClientUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class Check2LoadConfigInfo implements ApplicationRunner {

    public static final String ROUTER_KEY = "router_config";

    @Autowired
    private RedisClientUtil rcu;

    @Autowired
    private RouterConfigService routerConfigService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Map<?, ?> map = rcu.hgetAllHash(ROUTER_KEY);
        if (null == map || map.isEmpty()) {
            List<RouterConfig> list = routerConfigService.searchAllRouterConfig();
            if (!ObjectUtils.isNullObj(list)) {
                for (RouterConfig rc : list) {
                    routerConfigService.deleteRouterConfig(rc.getId());
                }
                list.forEach(rc -> {
                    routerConfigService.insertRouterConfig(rc);
                });
            }
        }
    }

}
