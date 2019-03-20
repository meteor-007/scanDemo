package cnvd.weakpwdscan;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/***
 * @author dqye
 */
public class InitConnect {
    /***
     * 用于存放所有数据库配置的连接，key:别名；value:数据库连接
     */
    public static final Map<String, Object> DB_CONNECT_MAP = new HashMap<>(16);
    final Logger log = LoggerFactory.getLogger(InitConnect.class);

//    @Resource
//    private DatabaseConfigService databaseConfigService;
//    @Resource
//    public MonitorObjectService monitorObjectService;
//    @Resource
//    private LogService logService;
//    @Resource
//    private MonitorSystemConfService monitorSystemConfService;
//    @Resource
//    private LicenseService licenseService;
//    @Resource
//    private MonitorConfigService monitorConfigService;
//    @Resource
//    private ModuleGroupMapper moduleGroupMapper;
//    @Resource
//    private DashboardService dashboardService;
//    @Resource
//    private AlarmService alarmService;
//    @Resource
//    private UserService userService;
//    @Resource
//    private GlobalConfigService globalConfigService;
//    @Resource
//    private MemoryRateService memoryRateService;
//
//    public static ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("initDbConnect").build();
    /***
     * 创建一个线程池
     * setCorePoolSize：设置核心池大小;
     * setMaximumPoolSize：设置线程池最大能创建的线程数目大小
     * keepAliveTime:当线程空闲时间达到keepAliveTime，该线程会退出，直到线程数量等于corePoolSize
     * 比如说线程池中最大的线程数为50，而其中只有40个线程任务在跑，相当于有10个空闲线程，这10个空闲线程不能让他一直在开着，
     * 因为线程的存在也会特别耗资源的，所有就需要设置一个这个空闲线程的存活时间
     * 这些参数后续可能需要调整，先暂时不管
     */
//    public static ThreadPoolExecutor executor = new ThreadPoolExecutor(
//            20, 256, 2000, TimeUnit.MILLISECONDS,
//            new SynchronousQueue<>(), threadFactory, new ThreadPoolExecutor.AbortPolicy()
//    );

//    /**
//     * 随Spring初始化启动,注解含义：在Spring初始化时，执行此方法
//     */
//    @PostConstruct
//    public void initDbConnect() {
//
//        log.info("开始初始化用户配置的数据库连接!");
//        try {
//            //更新用户授权过期的时间
//            Map<String, Object> resultMap = licenseService.readLicenseInformation();
//            Object longDate = resultMap.get("licenceValidityPeriod");
//            if (longDate != null) {
//                MonitorSystemConf monitorSystemConf1 = new MonitorSystemConf();
//                monitorSystemConf1.setId(2);
//                monitorSystemConf1.setValue("" + longDate);
//                monitorSystemConfService.updateByPrimaryKeySelective(monitorSystemConf1);
//            }
//            //创建系统定时器任务
//            SystemTimer systemTimer = new SystemTimer();
//            systemTimer.startTask(monitorSystemConfService,logService);//创建系统定时器任务
//            SendMailTimer sendMailTimer = new SendMailTimer();
//
//            sendMailTimer.startTask(databaseConfigService,logService,alarmService,globalConfigService,userService);
//
//            MemoryRateTimer memoryRateTimer = new MemoryRateTimer();
//            memoryRateTimer.startTask(logService,memoryRateService);
//
//            List<DatabaseConfig> list = databaseConfigService.selectAll();
//            if (list == null || list.isEmpty()) {
//                log.warn("系统中还没有配置任何需要监控的数据库");
//            } else {
//                //利用线程池管理多线程，获取每个数据库的连接
//                list.forEach((config) -> {
//
//                    ConnectionThread connThread = new ConnectionThread(config);
//                    executor.execute(connThread);
//
//                });
//                log.debug("系统中配置了{}个监控数据库配置", list.size());
//            }
//
//            list.forEach((config) -> {
//                startMonitorThread(config);
//            });
//
//        } catch (Exception e) {
//            SystemLog syslog = new SystemLog();
//            syslog.setLogType(Byte.valueOf("1"));
//            syslog.setSystemLogResult(Byte.valueOf("-1"));
//            syslog.setSystemLogTime(System.currentTimeMillis());
//            syslog.setEventLevel(Byte.valueOf("1"));
//            syslog.setLogDescribe("初始化数据库连接失败");
//            logService.insertSelective(syslog);
//
//            log.error("初始化数据库连接失败，失败信息：", e);
//        }
//    }

//    /**
//     * 创建开启监控定时器类
//     *
//     * @param config 数据配置信息
//     */
//    public void startMonitorThread(DatabaseConfig config) {
//        MonitorMain monitorMain = new MonitorMain();
//        monitorMain.init(config, monitorObjectService,databaseConfigService,
//                monitorConfigService, monitorSystemConfService, moduleGroupMapper,dashboardService,logService);
//
//        executor.execute(monitorMain);
//    }
}