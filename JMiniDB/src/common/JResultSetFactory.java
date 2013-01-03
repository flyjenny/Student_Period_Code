package common;

import java.util.LinkedList;
import java.util.List;

import metadata.Schema;

import query.plan.Plan;
import query.scan.Scan;

public class JResultSetFactory {

	public static JResultSet create(RuntimeException e) {

		JExceptionResult rst = new JExceptionResult();
		rst.exception = e;
		return rst;
	}

	public static JResultSet create(int count) {
		JEffectResult rst = new JEffectResult();
		rst.effectRow = count;
		return rst;
	}

	public static JResultSet create(Plan qp) {
		try {
			JQueryResult rst = new JQueryResult();
			Schema sch = qp.getSchema();
			String fds[] = sch.getFields().toArray(new String[0]);
			int len = fds.length;

			int length[] = new int[fds.length];
			int type[] = new int[fds.length];
			for (int i = 0; i < fds.length; i++) {
				length[i] = sch.getLength(fds[i]);
				type[i] = sch.getType(fds[i]);
			}
			rst.length = length;
			rst.type = type;

			rst.fds = fds;
			rst.length = length;
			rst.type = type;

			rst.rows = new LinkedList<Object[]>();

			Scan s = qp.open(null);
			rst.count = 0;
			while (s.next()) {
				Object r[] = new Object[fds.length];
				for (int i = 0; i < fds.length; i++) {
					r[i] = s.getVal(fds[i]).toJavaVal();
				}

				// rst.rows.add(Resvert(r));
				rst.rows.add(r);
				rst.count++;
			}
			// rst.fds=Resvert(rst.fds);
			// rst.length=Resvert(rst.length);
			// rst.type=Resvert(rst.type);
			return rst;
		} catch (RuntimeException e) {
			e.printStackTrace();
			return create(e);
		}
	}

	private static String[] Resvert(String[] i) {
		String t[] = i.clone();
		for (int j = i.length - 1; j >= 0; j--)
			t[i.length - 1 - j] = i[j];
		return t;
	}

	private static Object[] Resvert(Object[] i) {
		Object t[] = i.clone();
		for (int j = i.length - 1; j >= 0; j--)
			t[i.length - 1 - j] = i[j];
		return t;
	}

	private static int[] Resvert(int[] i) {
		int t[] = i.clone();
		for (int j = i.length - 1; j >= 0; j--)
			t[i.length - 1 - j] = i[j];
		return t;
	}

	public static JResultSet create(String[] fld, int[] type, List<Object[]> rs) {
		JQueryResult rst = new JQueryResult();
		rst.count = rs.size();
		rst.fds = fld;
		rst.type = type;
		rst.rows = rs;
		rst.length = new int[fld.length];
		return rst;
	}

}
