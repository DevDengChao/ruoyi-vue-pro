package cn.iocoder.yudao.module.member.service.vip;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.member.controller.admin.vip.vo.record.MemberVipRecordPageReqVO;
import cn.iocoder.yudao.module.member.controller.app.vip.vo.AppMemberVipRecordPageReqVO;
import cn.iocoder.yudao.module.member.dal.dataobject.vip.MemberVipRecordDO;
import cn.iocoder.yudao.module.member.enums.vip.MemberVipBizTypeEnum;

/**
 * 用户会员记录 Service 接口
 *
 * @author QingX
 */
public interface MemberVipRecordService {

    /**
     * 【管理员】获得会员记录分页
     *
     * @param pageReqVO 分页查询
     * @return 签到记录分页
     */
    PageResult<MemberVipRecordDO> getVipRecordPage(MemberVipRecordPageReqVO pageReqVO);

    /**
     * 【会员】获得会员记录分页
     *
     * @param userId    用户编号
     * @param pageReqVO 分页查询
     * @return 签到记录分页
     */
    PageResult<MemberVipRecordDO> getVipRecordPage(Long userId, AppMemberVipRecordPageReqVO pageReqVO);

    /**
     * 创建用户会员记录
     *
     * @param userId   用户ID
     * @param duration 变动会员
     * @param bizType  业务类型
     * @param bizId    业务编号
     */
    void createVipRecord(Long userId, Long duration, MemberVipBizTypeEnum bizType, String bizId);
}
