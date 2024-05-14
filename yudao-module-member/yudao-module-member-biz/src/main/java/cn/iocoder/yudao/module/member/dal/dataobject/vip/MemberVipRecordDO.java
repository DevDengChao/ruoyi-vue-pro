package cn.iocoder.yudao.module.member.dal.dataobject.vip;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.util.Date;

/**
 * 用户会员记录 DO
 *
 * @author QingX
 */
@TableName("member_vip_record")
@KeySequence("member_vip_record_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberVipRecordDO extends BaseDO {

    /**
     * 自增主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 用户编号
     * <p>
     * 对应 MemberUserDO 的 id 属性
     */
    private Long userId;

    /**
     * 业务编码
     */
    private String bizId;
    /**
     * 业务类型
     * <p>
     * 枚举 {@link cn.iocoder.yudao.module.member.enums.vip.MemberVipBizTypeEnum}
     */
    private Integer bizType;

    /**
     * 会员标题
     */
    private String title;
    /**
     * 会员描述
     */
    private String description;

    /**
     * 变动会员
     * <p>
     * 1、正数表示获得会员
     * 2、负数表示消耗会员
     */
    private Long duration;
    /**
     * 变动后的会员
     */
    private Date expiration;

}
