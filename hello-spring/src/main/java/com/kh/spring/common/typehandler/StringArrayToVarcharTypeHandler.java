package com.kh.spring.common.typehandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

@MappedTypes(String[].class)
@MappedJdbcTypes(JdbcType.VARCHAR)
public class StringArrayToVarcharTypeHandler extends BaseTypeHandler<String[]> {

	// 파라미터 관련 ( to DB )
	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, String[] parameter, JdbcType jdbcType)
			throws SQLException {
		// String[] --> String -->  varchar2
		ps.setString(i, String.join(",", parameter)); // separate : "," , parameter
	}

	// Result관련 ( from DB )
	@Override
	public String[] getNullableResult(ResultSet rs, String columnName) throws SQLException {
		String value = rs.getString(columnName); // varchar2 --> String
		
		return value != null ? value.split(",") : null; // ResultSet이 null로 넘어올 수 있으므로 null 체크를 반드시 해줘야 한다. String -> String[] 
	}

	@Override
	public String[] getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
		String value = rs.getString(columnIndex);
		return value != null ? value.split(",") : null;
	}

	@Override
	public String[] getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
		String value = cs.getString(columnIndex);
		return value != null ? value.split(",") : null;
	}

}
