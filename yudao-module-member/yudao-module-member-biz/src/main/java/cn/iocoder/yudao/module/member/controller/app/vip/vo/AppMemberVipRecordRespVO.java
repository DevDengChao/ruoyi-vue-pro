package cn.iocoder.yudao.module.member.controller.app.vip.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "用户 App - 用户会员记录 Response VO")
@Data
public class AppMemberVipRecordRespVO {

    @Schema(description = "自增主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "31457")
    private Long id;

    @Schema(description = "会员标题", requiredMode = Schema.RequiredMode.REQUIRED, example = "你猜")
    private String title;

    @Schema(description = "会员描述", example = "你猜")
    private String description;

    @Schema(description = "会员", requiredMode = Schema.RequiredMode.REQUIRED, example = "100")
    private Long duration;

    @Schema(description = "发生时间", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;

}
