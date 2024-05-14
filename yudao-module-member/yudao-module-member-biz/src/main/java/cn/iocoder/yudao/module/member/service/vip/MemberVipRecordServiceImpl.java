package cn.iocoder.yudao.module.member.service.vip;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.iocoder.yudao.framework.common.exception.enums.GlobalErrorCodeConstants;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.date.TimeStampSupplier;
import cn.iocoder.yudao.module.member.controller.admin.vip.vo.record.MemberVipRecordPageReqVO;
import cn.iocoder.yudao.module.member.controller.app.vip.vo.AppMemberVipRecordPageReqVO;
import cn.iocoder.yudao.module.member.dal.dataobject.user.MemberUserDO;
import cn.iocoder.yudao.module.member.dal.dataobject.vip.MemberVipRecordDO;
import cn.iocoder.yudao.module.member.dal.mysql.vip.MemberVipRecordMapper;
import cn.iocoder.yudao.module.member.enums.vip.MemberVipBizTypeEnum;
import cn.iocoder.yudao.module.member.service.user.MemberUserService;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;

import java.util.Date;
import java.util.List;
import java.util.Set;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.convertSet;


/**
 * 会员记录 Service 实现类
 *
 * @author QingX
 */
@Slf4j
@Service
@Validated
@RequiredArgsConstructor
public class MemberVipRecordServiceImpl implements MemberVipRecordService {

    private final TimeStampSupplier timeStampSupplier;
    @Resource
    private MemberVipRecordMapper memberVipRecordMapper;
    @Resource
    private MemberUserService memberUserService;

    @Override
    public PageResult<MemberVipRecordDO> getVipRecordPage(MemberVipRecordPageReqVO pageReqVO) {
        // 根据用户昵称查询出用户 ids
        Set<Long> userIds = null;
        if (StringUtils.isNotBlank(pageReqVO.getNickname())) {
            List<MemberUserDO> users = memberUserService.getUserListByNickname(pageReqVO.getNickname());
            // 如果查询用户结果为空直接返回无需继续查询
            if (CollectionUtils.isEmpty(users)) {
                return PageResult.empty();
            }
            userIds = convertSet(users, MemberUserDO::getId);
        }
        // 执行查询
        return memberVipRecordMapper.selectPage(pageReqVO, userIds);
    }

    @Override
    public PageResult<MemberVipRecordDO> getVipRecordPage(Long userId, AppMemberVipRecordPageReqVO pageReqVO) {
        return memberVipRecordMapper.selectPage(userId, pageReqVO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createVipRecord(Long userId, Long duration, MemberVipBizTypeEnum bizType, String bizId) {
        if (duration == 0) return;
        // 1. 校验用户会员余额
        MemberUserDO user = memberUserService.getUser(userId);
        Date userExpiration = ObjectUtil.defaultIfNull(user.getVipExpiration(), new Date(0));
        Date now = new Date(timeStampSupplier.get());

        long targetVipExpiration = Math.max(userExpiration.getTime(), now.getTime()) + duration; // 用户变动后的会员
//        if (targetVipExpiration < 0) {
//            throw exception(USER_VIP_WILL_EXPIRED);
//        }

        // 2. 更新用户会员
        boolean success = memberUserService.updateUserVip(userId, duration);
        if (!success) {
            throw exception(GlobalErrorCodeConstants.INTERNAL_SERVER_ERROR);
        }

        // 3. 增加会员记录
        MemberVipRecordDO record = new MemberVipRecordDO()
                .setUserId(userId).setBizId(bizId).setBizType(bizType.getType())
                .setTitle(bizType.getName()).setDescription(StrUtil.format(bizType.getDescription(), duration))
                .setDuration(duration).setExpiration(new Date(targetVipExpiration));
        memberVipRecordMapper.insert(record);
    }
}
