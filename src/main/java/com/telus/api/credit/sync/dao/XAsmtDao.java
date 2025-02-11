package com.telus.api.credit.sync.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.stereotype.Repository;

@Repository
public class XAsmtDao extends NamedParameterJdbcDaoSupport {

   private static final String SELECT_ALL = "SELECT * FROM x_assessment where need_to_sync = true order by customer_id asc limit ? offset ?";

   private static final String MAX_RECORDS = "select count(customer_id) from x_assessment where need_to_sync = true";
   
   private static final String UPDATE_NEED_TO_SYNC_FALSE = "UPDATE x_assessment SET need_to_sync = false where customer_id = ?";

   public XAsmtDao(DataSource dataSource) {
      setDataSource(dataSource);
   }

   public List<Map<String, Object>> getAllNeedToSync(long limit, long offset) {
      return getJdbcTemplate().query(SELECT_ALL, new Object[] { limit, offset }, new ColumnMapRowMapper());
   }

   public Long getCountMaxRecords() {
      return getJdbcTemplate().queryForObject(MAX_RECORDS, Long.class);
   }

   public int updateNeedToSyncFalse(Long custId) {
      return getJdbcTemplate().update(UPDATE_NEED_TO_SYNC_FALSE, custId); 
   }

   public int[] batchUpdateNeedToSyncFalse(List<Long> custIds) {
      return getJdbcTemplate().batchUpdate(UPDATE_NEED_TO_SYNC_FALSE, new BatchPreparedStatementSetter() {
            public void setValues(PreparedStatement ps, int i) throws SQLException {
               ps.setLong(1, custIds.get(i));
            }
            public int getBatchSize() {
               return custIds.size();
            }
      }); 
   }

}
