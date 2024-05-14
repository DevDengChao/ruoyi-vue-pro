package cn.iocoder.yudao.module.member.api.vip;

import cn.iocoder.yudao.module.member.enums.vip.MemberVipBizTypeEnum;
import jakarta.validation.constraints.Min;

/**
 * 用户会员的 API 接口
 *
 * @author owen
 */
public interface MemberVipApi {

    /**
     * 增加用户会员
     *
     * @param userId   用户编号
     * @param duration 会员
     * @param bizType  业务类型 {@link  MemberVipBizTypeEnum}
     * @param bizId    业务编号
     */
    void addDuration(Long userId, @Min(0) Long duration,
                     Integer bizType, String bizId);

    /**
     * 减少用户会员
     *
     * @param userId   用户编号
     * @param duration 会员
     * @param bizType  业务类型 {@link MemberVipBizTypeEnum}
     * @param bizId    业务编号
     */
    void reduceDuration(Long userId, @Min(0) Long duration,
                        Integer bizType, String bizId);

}
