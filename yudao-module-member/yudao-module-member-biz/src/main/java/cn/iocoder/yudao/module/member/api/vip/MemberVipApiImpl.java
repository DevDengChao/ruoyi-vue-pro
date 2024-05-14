package cn.iocoder.yudao.module.member.api.vip;

import cn.hutool.core.lang.Assert;
import cn.iocoder.yudao.module.member.enums.vip.MemberVipBizTypeEnum;
import cn.iocoder.yudao.module.member.service.vip.MemberVipRecordService;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.Min;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.member.enums.ErrorCodeConstants.VIP_RECORD_BIZ_NOT_SUPPORT;

/**
 * 用户积分的 API 实现类
 *
 * @author owen
 */
@Service
@Validated
public class MemberVipApiImpl implements MemberVipApi {

    @Resource
    private MemberVipRecordService memberVipRecordService;


    @Override
    public void addDuration(Long userId, @Min(0) Long duration, Integer bizType, String bizId) {
        Assert.isTrue(duration > 0);
        MemberVipBizTypeEnum bizTypeEnum = MemberVipBizTypeEnum.getByType(bizType);
        if (bizTypeEnum == null) {
            throw exception(VIP_RECORD_BIZ_NOT_SUPPORT);
        }
        memberVipRecordService.createVipRecord(userId, duration, bizTypeEnum, bizId);
    }

    @Override
    public void reduceDuration(Long userId, @Min(0) Long duration, Integer bizType, String bizId) {
        Assert.isTrue(duration > 0);
        MemberVipBizTypeEnum bizTypeEnum = MemberVipBizTypeEnum.getByType(bizType);
        if (bizTypeEnum == null) {
            throw exception(VIP_RECORD_BIZ_NOT_SUPPORT);
        }
        memberVipRecordService.createVipRecord(userId, -duration, bizTypeEnum, bizId);
    }
}
