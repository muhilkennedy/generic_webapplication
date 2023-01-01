package com.base.generator;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import com.base.util.AnnotationUtil;
import com.base.util.Constants;

/**
 * @author Muhil kennedy
 * Generate custom tenant rootId by sequence
 */
public class RealmIdGenerator implements IdentifierGenerator {

	@Override
	public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
		Connection connection = session.connection();
		try (Statement stmt = connection.createStatement()) {
			ResultSet rs = stmt.executeQuery("select count(*) from tenant");
			if (rs.next()) {
				Integer nextVal = rs.getInt(1) + 10;
				String classCode = AnnotationUtil.getClassMetaCode(object);
				return classCode + Constants.COLON_SEPERATOR + nextVal;
			} else {
				throw new HibernateException("Something went wrong in RealmIdGenerator");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new HibernateException(e.getMessage());
		}
	}

}
