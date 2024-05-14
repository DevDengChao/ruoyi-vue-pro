package cn.iocoder.yudao.module.member.service.vip;

import cn.iocoder.yudao.framework.common.enums.TerminalEnum;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.date.TimeStampSupplier;
import cn.iocoder.yudao.framework.redis.config.YudaoRedisAutoConfiguration;
import cn.iocoder.yudao.framework.test.core.ut.BaseDbAndRedisUnitTest;
import cn.iocoder.yudao.module.member.controller.admin.vip.vo.record.MemberVipRecordPageReqVO;
import cn.iocoder.yudao.module.member.dal.dataobject.user.MemberUserDO;
import cn.iocoder.yudao.module.member.dal.dataobject.vip.MemberVipRecordDO;
import cn.iocoder.yudao.module.member.enums.vip.MemberVipBizTypeEnum;
import cn.iocoder.yudao.module.member.mq.producer.user.MemberUserProducer;
import cn.iocoder.yudao.module.member.service.user.MemberUserService;
import cn.iocoder.yudao.module.member.service.user.MemberUserServiceImpl;
import cn.iocoder.yudao.module.system.api.sms.SmsCodeApi;
import cn.iocoder.yudao.module.system.api.social.SocialClientApi;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.Duration;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;


@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@Import({MemberUserServiceImpl.class, MemberVipRecordServiceImpl.class, YudaoRedisAutoConfiguration.class})
class MemberVipRecordServiceTest extends BaseDbAndRedisUnitTest {

    @Autowired
    private MemberUserService users;
    @Autowired
    private MemberVipRecordService vipRecords;

    @MockBean
    private TimeStampSupplier timeStampSupplier;

    // mock dependencies
    @MockBean
    private SmsCodeApi smsCodeApi;
    @MockBean
    private SocialClientApi socialClientApi;
    @MockBean
    private PasswordEncoder passwordEncoder;
    @MockBean
    private MemberUserProducer memberUserProducer;

    @Test
    void increaseAndDecreaseVipDuration() {
        when(timeStampSupplier.get()).thenReturn(0L);

        MemberUserDO user = users.createUserIfAbsent("15800000000", "127.0.0.1", TerminalEnum.UNKNOWN.getTerminal());
        vipRecords.createVipRecord(user.getId(), Duration.ofDays(1).toMillis(), MemberVipBizTypeEnum.ORDER_GIVE, "increase-vip");
        vipRecords.createVipRecord(user.getId(), -Duration.ofDays(1).toMillis(), MemberVipBizTypeEnum.ORDER_GIVE_CANCEL, "decrease-vip");

        MemberVipRecordPageReqVO req = new MemberVipRecordPageReqVO();
        req.setUserId(user.getId());
        PageResult<MemberVipRecordDO> result = vipRecords.getVipRecordPage(req);
        assertEquals(2, result.getTotal());

        Date vipExpiration = users.getUser(user.getId()).getVipExpiration();
        assertNotNull(vipExpiration);
        assertEquals(vipExpiration.getTime(), 0);
    }
}