package cn.iocoder.yudao.framework.mybatis.core.type;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import org.apache.ibatis.type.TypeHandler;

import java.sql.*;
import java.time.Instant;

/**
 * Instant 的类型转换器实现类，对应数据库的 timestamp 类型
 *
 * @author 邓超
 * @since 2024/05/14
 */
@MappedJdbcTypes(JdbcType.TIMESTAMP)
@MappedTypes(Instant.class)
public class InstantTimestampTypeHandler implements TypeHandler<Instant> {

    @Override
    public void setParameter(PreparedStatement ps, int i, Instant instant, JdbcType jdbcType) throws SQLException {
        // 设置占位符
        ps.setTimestamp(i, new Timestamp(instant.toEpochMilli()));
    }

    @Override
    public Instant getResult(ResultSet rs, String columnName) throws SQLException {
        Timestamp value = rs.getTimestamp(columnName);
        return getResult(value);
    }

    @Override
    public Instant getResult(ResultSet rs, int columnIndex) throws SQLException {
        Timestamp value = rs.getTimestamp(columnIndex);
        return getResult(value);
    }

    @Override
    public Instant getResult(CallableStatement cs, int columnIndex) throws SQLException {
        Timestamp value = cs.getTimestamp(columnIndex);
        return getResult(value);
    }

    private Instant getResult(Timestamp value) {
        if (value == null) {
            return null;
        }
        return value.toInstant();
    }
}
