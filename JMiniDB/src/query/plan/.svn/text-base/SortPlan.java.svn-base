package query.plan;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import metadata.Schema;
import query.predicate.Constant;
import query.scan.Scan;
import query.scan.SortScan;

public class SortPlan implements Plan {
	private Plan p;
	private Schema sch;
	// private List<String> sf;
	private Comparator<TempRow> cp;
	private Map<String, Integer> fmap;

	public SortPlan(Plan p, List<String> sortField, List<Integer> Order) {
		this.p = p;
		this.sch = p.getSchema();
		// this.sf=sortField;
		String fs[] = sch.getFields().toArray(new String[0]);
		fmap = new HashMap<String, Integer>(fs.length);
		for (int i = 0; i < fs.length; i++) {
			{
				fmap.put(fs[i], i);
			}
		}
		for (int i = 0; i < sortField.size(); i++) {
			String o = sortField.get(i);
			if (o.startsWith("."))
				for (String f : fs)
					if (f.endsWith(o))
						sortField.set(i, f);
		}

		this.cp = new TempRowCompare(sortField, Order, fmap);
	}

	@Override
	public int blocksAccessed() {
		// TODO opt-est
		return 0;
	}

	@Override
	public int distinctValues(String fldName) {
		// TODO opt-est
		return 0;
	}

	@Override
	public Schema getSchema() {
		return sch;
	}

	@Override
	public Scan open(Scan sca) {
		Scan s = p.open(sca);
		if (null != sca)
			s.setParentScan(sca);
		List<TempRow> rs = scanDump(s, fmap);

		Collections.sort(rs, cp);
		SortScan ss = new SortScan(s, fmap, rs);
		// s.setParentScan(ss);
		return ss;
	}

	private List<TempRow> scanDump(Scan s, Map<String, Integer> fmap) {
		List<TempRow> trs = new ArrayList<TempRow>();
		Set<String> keys = fmap.keySet();
		int len = keys.size();
		while (s.next()) {
			TempRow r = new TempRow(len);
			for (String k : keys) {
				r.setRow(fmap.get(k), s.getVal(k));
			}
			trs.add(r);
		}
		return trs;
	}

	@Override
	public int recordsOutput() {
		// TODO opt-est
		return 0;
	}

	public class TempRowCompare implements Comparator<TempRow> {

		private String sortField[];
		private Map<String, Integer> fmap;
		private Integer order[];

		public TempRowCompare(List<String> sortField, List<Integer> Order,
				Map<String, Integer> fmap) {
			this.sortField = sortField.toArray(new String[0]);
			this.order = Order.toArray(new Integer[0]);
			this.fmap = fmap;

		}

		@Override
		public int compare(TempRow o1, TempRow o2) {

			for (int i = 0; i < sortField.length; i++) {
				String f = sortField[i];
				int index = fmap.get(f);
				Constant v1 = o1.getRow(index);
				Constant v2 = o2.getRow(index);
				int rst = v1.compareTo(v2);
				if (rst != 0)
					return rst * order[i];
			}
			return 0;
		}
	}

}
