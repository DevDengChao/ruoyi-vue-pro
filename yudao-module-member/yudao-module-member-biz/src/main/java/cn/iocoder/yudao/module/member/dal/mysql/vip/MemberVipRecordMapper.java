package cn.iocoder.yudao.module.member.dal.mysql.vip;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.member.controller.admin.vip.vo.record.MemberVipRecordPageReqVO;
import cn.iocoder.yudao.module.member.controller.app.vip.vo.AppMemberVipRecordPageReqVO;
import cn.iocoder.yudao.module.member.dal.dataobject.vip.MemberVipRecordDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.Set;

/**
 * 用户积分记录 Mapper
 *
 * @author QingX
 */
@Mapper
public interface MemberVipRecordMapper extends BaseMapperX<MemberVipRecordDO> {

    default PageResult<MemberVipRecordDO> selectPage(MemberVipRecordPageReqVO reqVO, Set<Long> userIds) {
        return selectPage(reqVO, new LambdaQueryWrapperX<MemberVipRecordDO>()
                .inIfPresent(MemberVipRecordDO::getUserId, userIds)
                .eqIfPresent(MemberVipRecordDO::getUserId, reqVO.getUserId())
                .eqIfPresent(MemberVipRecordDO::getBizType, reqVO.getBizType())
                .likeIfPresent(MemberVipRecordDO::getTitle, reqVO.getTitle())
                .orderByDesc(MemberVipRecordDO::getId));
    }

    default PageResult<MemberVipRecordDO> selectPage(Long userId, AppMemberVipRecordPageReqVO pageReqVO) {
        return selectPage(pageReqVO, new LambdaQueryWrapperX<MemberVipRecordDO>()
                .eq(MemberVipRecordDO::getUserId, userId)
                .betweenIfPresent(MemberVipRecordDO::getCreateTime, pageReqVO.getCreateTime())
                .gt(Boolean.TRUE.equals(pageReqVO.getAddStatus()),
                        MemberVipRecordDO::getDuration, 0)
                .lt(Boolean.FALSE.equals(pageReqVO.getAddStatus()),
                        MemberVipRecordDO::getDuration, 0)
                .orderByDesc(MemberVipRecordDO::getId));
    }

}
