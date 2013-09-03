/**
 * 
 */
package org.opencis.db;

import org.dbunit.database.IDatabaseConnection;
import org.dbunit.database.QueryDataSet;

import com.dayatang.dbunit.DatabaseExport;

/**
 * @author chencao
 * 
 *         2011-5-28
 */
public class DbExport {

	public static void main(String[] args) throws Exception {
		IDatabaseConnection connection = DatabaseExport.createConnection();
		QueryDataSet partialDataSet = new QueryDataSet(connection);
		partialDataSet.addTable("CIS_CI_SERVERS",
				"SELECT * FROM CIS_CI_SERVERS");
		partialDataSet.addTable("CIS_PM_SERVERS",
				"SELECT * FROM CIS_PM_SERVERS");
		partialDataSet.addTable("CIS_SCM_SERVERS",
				"SELECT * FROM CIS_SCM_SERVERS");
		DatabaseExport.exportDataSet(partialDataSet,
				"src/main/resources/dataset/product-data.xml");
	}
}
