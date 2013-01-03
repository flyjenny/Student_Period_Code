package jminidb.server;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int status = 0;

		try {
			ic = Ice.Util.initialize(args);
			Ice.ObjectAdapter adapter = ic.createObjectAdapterWithEndpoints(
					"jminidbAdapter", "default -p 12345");
			Ice.Object object = new JMiniDBServer();
			adapter.add(object, ic.stringToIdentity("jminidb"));
			adapter.activate();
			System.out.println("JMiniDB Server Starting");
			ic.waitForShutdown();
		} catch (Exception e) {
			e.printStackTrace();
			status = 1;
		}
		if (ic != null) {
			// Clean up
			//
			try {
				ic.destroy();
			} catch (Exception e) {
				// System.err.println(e.getMessage());
				status = 1;
			}
		}
		// System.exit(status);
	}

	public static Ice.Communicator ic = null;

	public static void shutdown() {
		ic.shutdown();
		if (null != ic)
			ic.destroy();

		ic = null;
		System.out.println("JMiniDB Server Shutdown");
		// System.out.println("afdasdf");
	}

}
