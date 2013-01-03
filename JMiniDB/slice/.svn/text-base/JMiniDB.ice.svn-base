module jminidb{
	module server{    
	["java:serializable:common.JResultSet"] sequence<byte> JResult;

	interface DBServer{  
 		idempotent void connection(string dbName);
 		int createStatement();
 		JResult executeQuery(int s,string sql);
 		JResult executeUpdate(int s,string sql);
 		idempotent void close(); 
 		void closeStatement(int s);
 		JResult getTables(string tlbName);
 		JResult getImportedKeys(string tlbName);
 		JResult getColumns(string tlbName);
 		};
	};  
};  