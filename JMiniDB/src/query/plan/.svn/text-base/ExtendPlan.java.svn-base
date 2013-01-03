package query.plan;

import java.util.Map;
import metadata.Schema;
import query.scan.ExtendScan;
import query.scan.Scan;

public class ExtendPlan implements Plan {
	private Plan p;
	private Schema sch;
	private Map<String, String> fmap;

	/**
	 * 
	 * @param p
	 * @param fmap
	 *            key is new name value is old name
	 */
	public ExtendPlan(Plan p, Map<String, String> fmap) {

		this.p = p;
		this.fmap = fmap;
		sch = new Schema();
		Schema osch = p.getSchema();
		// build new name
		for (String o : fmap.keySet().toArray(new String[0])) {
			String n = fmap.get(o);

			if (n.startsWith(".")) {
				// String fd = null;
				for (String f : p.getSchema().getFields())
					if (f.indexOf(n) > 0) {
						if (o.startsWith("."))
							fmap.put(o.substring(1), f);
						fmap.put(o, f);
						n = f;
						break;
					}
			}
			int type = osch.getType(n);
			int length = osch.getLength(n);
			sch.addField(o.startsWith(".") ? o.substring(1) : o, type, length);
		}

	}

	@Override
	public int blocksAccessed() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int distinctValues(String fldName) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Schema getSchema() {
		return sch;
	}

	@Override
	public Scan open(Scan sca) {
		Scan s = p.open(sca);
		s = new ExtendScan(s, fmap);
		/*
		 * if(null!=sca) s.setParentScan(sca);
		 */
		return s;
	}

	@Override
	public int recordsOutput() {
		// TODO Auto-generated method stub
		return 0;
	}

}
