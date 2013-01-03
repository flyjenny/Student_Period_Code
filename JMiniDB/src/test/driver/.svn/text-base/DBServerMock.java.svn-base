package test.driver;

import java.util.Map;

import Ice.AMI_Object_ice_flushBatchRequests;
import Ice.AMI_Object_ice_invoke;
import Ice.ByteSeqHolder;
import Ice.Communicator;
import Ice.Connection;
import Ice.Current;
import Ice.Endpoint;
import Ice.EndpointSelectionType;
import Ice.Identity;
import Ice.LocatorPrx;
import Ice.ObjectPrx;
import Ice.OperationMode;
import Ice.RouterPrx;

import common.JResultSet;

import jminidb.server.DBServerPrx;
import jminidb.server.JMiniDBServer;

public class DBServerMock implements DBServerPrx {

	public JMiniDBServer sv;
	public Current current;

	public DBServerMock() {
		sv = new JMiniDBServer();
		current = null;
	}

	@Override
	public void close() {
		sv.close(current);

	}

	@Override
	public void close(Map<String, String> ctx) {
		// TODO Auto-generated method stub

	}

	@Override
	public void closeStatement(int s) {
		sv.closeStatement(s, current);

	}

	@Override
	public void closeStatement(int s, Map<String, String> ctx) {
		// TODO Auto-generated method stub

	}

	@Override
	public void connection(String dbName) {
		sv.connection(dbName, current);

	}

	@Override
	public void connection(String dbName, Map<String, String> ctx) {
		// TODO Auto-generated method stub

	}

	@Override
	public int createStatement() {
		return sv.createStatement(current);
	}

	@Override
	public int createStatement(Map<String, String> ctx) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public JResultSet executeQuery(int s, String sql) {
		return sv.executeQuery(s, sql, current);
	}

	@Override
	public JResultSet executeQuery(int s, String sql, Map<String, String> ctx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JResultSet executeUpdate(int s, String sql) {
		return sv.executeUpdate(s, sql, current);
	}

	@Override
	public JResultSet executeUpdate(int s, String sql, Map<String, String> ctx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ObjectPrx ice_adapterId(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ObjectPrx ice_batchDatagram() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ObjectPrx ice_batchOneway() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ObjectPrx ice_collocationOptimized(boolean arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ObjectPrx ice_compress(boolean arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ObjectPrx ice_connectionCached(boolean arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ObjectPrx ice_connectionId(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ObjectPrx ice_context(Map<String, String> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ObjectPrx ice_datagram() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ObjectPrx ice_defaultContext() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ObjectPrx ice_endpointSelection(EndpointSelectionType arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ObjectPrx ice_endpoints(Endpoint[] arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ObjectPrx ice_facet(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void ice_flushBatchRequests() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean ice_flushBatchRequests_async(
			AMI_Object_ice_flushBatchRequests arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String ice_getAdapterId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Connection ice_getCachedConnection() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Communicator ice_getCommunicator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Connection ice_getConnection() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, String> ice_getContext() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EndpointSelectionType ice_getEndpointSelection() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Endpoint[] ice_getEndpoints() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String ice_getFacet() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int ice_getHash() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Identity ice_getIdentity() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LocatorPrx ice_getLocator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int ice_getLocatorCacheTimeout() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public RouterPrx ice_getRouter() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String ice_id() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String ice_id(Map<String, String> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ObjectPrx ice_identity(Identity arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] ice_ids() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] ice_ids(Map<String, String> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean ice_invoke(String arg0, OperationMode arg1, byte[] arg2,
			ByteSeqHolder arg3) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean ice_invoke(String arg0, OperationMode arg1, byte[] arg2,
			ByteSeqHolder arg3, Map<String, String> arg4) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean ice_invoke_async(AMI_Object_ice_invoke arg0, String arg1,
			OperationMode arg2, byte[] arg3) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean ice_invoke_async(AMI_Object_ice_invoke arg0, String arg1,
			OperationMode arg2, byte[] arg3, Map<String, String> arg4) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean ice_isA(String arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean ice_isA(String arg0, Map<String, String> arg1) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean ice_isBatchDatagram() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean ice_isBatchOneway() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean ice_isCollocationOptimized() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean ice_isConnectionCached() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean ice_isDatagram() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean ice_isOneway() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean ice_isPreferSecure() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean ice_isSecure() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean ice_isTwoway() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ObjectPrx ice_locator(LocatorPrx arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ObjectPrx ice_locatorCacheTimeout(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ObjectPrx ice_oneway() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void ice_ping() {
		// TODO Auto-generated method stub

	}

	@Override
	public void ice_ping(Map<String, String> arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public ObjectPrx ice_preferSecure(boolean arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ObjectPrx ice_router(RouterPrx arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ObjectPrx ice_secure(boolean arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ObjectPrx ice_timeout(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String ice_toString() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ObjectPrx ice_twoway() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JResultSet getColumns(String tlbName) {
		return sv.getColumns(tlbName);
	}

	@Override
	public JResultSet getColumns(String tlbName, Map<String, String> ctx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JResultSet getImportedKeys(String tlbName) {
		return sv.getImportedKeys(tlbName);
	}

	@Override
	public JResultSet getImportedKeys(String tlbName, Map<String, String> ctx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JResultSet getTables(String tlbName) {
		return sv.getTables(tlbName);
	}

	@Override
	public JResultSet getTables(String tlbName, Map<String, String> ctx) {
		// TODO Auto-generated method stub
		return null;
	}

}
