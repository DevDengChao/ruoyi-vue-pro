package cn.iocoder.yudao.module.member.enums.vip;

import cn.hutool.core.util.EnumUtil;
import cn.iocoder.yudao.framework.common.core.IntArrayValuable;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

/**
 * 会员会员时长的业务类型枚举
 *
 * @author 芋道源码
 */
@AllArgsConstructor
@Getter
public enum MemberVipBizTypeEnum implements IntArrayValuable {

    SIGN(1, "签到", "签到获得 {} 会员时长", true),
    ADMIN(2, "管理员修改", "管理员修改 {} 会员时长", true),

//    ORDER_USE(11, "订单会员时长抵扣", "下单使用 {} 会员时长", false), // 下单时，扣减会员时长
//    ORDER_USE_CANCEL(12, "订单会员时长抵扣（整单取消）", "订单取消，退还 {} 会员时长", true), // ORDER_USE 的取消
//    ORDER_USE_CANCEL_ITEM(13, "订单会员时长抵扣（单个退款）", "订单退款，退还 {} 会员时长", true), // ORDER_USE 的取消

    ORDER_GIVE(21, "订单会员时长奖励", "下单获得 {} 会员时长", true), // 支付订单时，赠送会员时长
    ORDER_GIVE_CANCEL(22, "订单会员时长奖励（整单取消）", "订单取消，退还 {} 会员时长", false), // ORDER_GIVE 的取消
    ORDER_GIVE_CANCEL_ITEM(23, "订单会员时长奖励（单个退款）", "订单退款，扣除赠送的 {} 会员时长", false) // ORDER_GIVE 的取消
    ;

    /**
     * 类型
     */
    private final Integer type;
    /**
     * 名字
     */
    private final String name;
    /**
     * 描述
     */
    private final String description;
    /**
     * 是否为扣减会员时长
     */
    private final boolean add;

    public static MemberVipBizTypeEnum getByType(Integer type) {
        return EnumUtil.getBy(MemberVipBizTypeEnum.class,
                e -> Objects.equals(type, e.getType()));
    }

    @Override
    public int[] array() {
        return new int[0];
    }

}
