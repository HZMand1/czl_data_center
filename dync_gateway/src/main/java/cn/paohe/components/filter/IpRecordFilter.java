package cn.paohe.components.filter;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import cn.paohe.components.constants.CommonConstant;
import cn.paohe.framework.utils.ESUtil;
import cn.paohe.framework.utils.RedisClient;
import cn.paohe.framework.utils.base.ObjectUtils;
import cn.paohe.framework.utils.base.StringUtil;
import cn.paohe.framework.utils.cryption.uuid.UUIDUtil;
import cn.paohe.framework.utils.http.IPUtil;
import cn.paohe.framework.utils.ref.ReflectUtil;
import cn.paohe.vo.IpRecordVO;
import reactor.core.publisher.Mono;

@Component
public class IpRecordFilter implements GlobalFilter, Ordered {

    private static final String MALICIOUS_IP = "MALICIOUS_IP";
    private static final String PRE_REQUEST_PATH = "PRE_REQUEST_PATH";
    private static final String PRE_REQUEST_TIME = "PRE_REQUEST_TIME";
    private static final String MAL_REQUEST_TIMES = "MAL_REQUEST_TIMES";

    public final static Boolean ALL_REQUEST = false;

    @Autowired
    private RedisClient redisClient;

    @Autowired
    private ESUtil esUtil;

    /**
     * 允许的最小请求间隔
     */
    @Value("${filter.ip.min-interval-time}")
    private Long minRequestIntervalTime;

    /**
     * 允许的最大恶意请求次数
     */
    @Value("${filter.ip.max-malicious-time}")
    private Integer maxMaliciousTimes;

    @Override
    public int getOrder() {
        // 以5开头作为第一个
        return 5;
    }

    /**
     * 过滤器
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        IpRecordVO irv = new IpRecordVO();
        irv.setRecodeDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        // 同一个IP
        ServerHttpRequest shr = exchange.getRequest();

        String requestIp = IPUtil.getIpAdd(shr);
        irv.setIp(requestIp);

        String macAddress = IPUtil.getMACAddress(requestIp);
        irv.setMacAddress(macAddress == null ? "" : macAddress);

        String uri = shr.getURI().getPath();
        irv.setUri(uri);

        // 上次请求地址
        String preRequestPath = redisClient.hGetHash(MALICIOUS_IP, requestIp + PRE_REQUEST_PATH) == null ? ""
            : String.valueOf(redisClient.hGetHash(MALICIOUS_IP, requestIp + PRE_REQUEST_PATH));
        // 上次请求时间
        String preRequestTimeStr = redisClient.hGetHash(MALICIOUS_IP, requestIp + PRE_REQUEST_TIME) == null ? ""
            : String.valueOf(redisClient.hGetHash(MALICIOUS_IP, requestIp + PRE_REQUEST_TIME));
        Long preRequestTime = StringUtil.isEmpty(preRequestTimeStr) ? 0L : Long.valueOf(preRequestTimeStr);
        // 当前时间
        Long nowtime = System.currentTimeMillis();
        // 过滤频繁操作
        if (StringUtil.isNotEmpty(preRequestPath) && !ObjectUtils.isNullObj(preRequestTime)) {
            boolean flag =
                (uri.equals(preRequestPath) || ALL_REQUEST) && nowtime - preRequestTime < minRequestIntervalTime;
            if (flag) {
                // 非法请求次数
                String malRequestTimesStr =
                    String.valueOf(redisClient.hGetHash(MALICIOUS_IP, requestIp + MAL_REQUEST_TIMES));
                Integer malRequestTimes;
                if (StringUtil.isEmpty(preRequestTimeStr)) {
                    malRequestTimes = 1;
                } else {
                    malRequestTimes = Integer.valueOf(malRequestTimesStr) + 1;
                }
                redisClient.hSetHash(MALICIOUS_IP, requestIp + MAL_REQUEST_TIMES, String.valueOf(malRequestTimes));
                if (malRequestTimes > maxMaliciousTimes) {
                    irv.setModelCode(CommonConstant.ATTACK_ON_PURPOSE);
                    IpRecordVO irvAttack = ReflectUtil.objectCopyASM(irv, IpRecordVO.class);
                    irvAttack.setId(UUIDUtil.getUUID());
                    setupRecord2Elasticsearch(irvAttack);
                }
            } else {
                redisClient.hSetHash(MALICIOUS_IP, requestIp + MAL_REQUEST_TIMES, "0");
            }
        }

        irv.setId(UUIDUtil.getUUID());
        irv.setModelCode(CommonConstant.ACCESS_TO_RECORDS);
        setupRecord2Elasticsearch(irv);
        redisClient.hSetHash(MALICIOUS_IP, requestIp + PRE_REQUEST_PATH, uri);
        redisClient.hSetHash(MALICIOUS_IP, requestIp + PRE_REQUEST_TIME, String.valueOf(nowtime));
        return chain.filter(exchange);
    }

    /**
     * 将记录写入es库中
     */
    private void setupRecord2Elasticsearch(IpRecordVO irv) {
        esUtil.save(CommonConstant.RECORD_IP_KEY, irv);
    }

}
