package query.plan;

import query.predicate.Constant;

public class TempRow {

	Constant cs[];

	public TempRow(int c) {
		cs = new Constant[c];
	}

	public Constant getRow(int i) {
		if (i >= 0 && i < cs.length)
			return cs[i];
		else
			return null;
	}

	public void setRow(int i, Constant v) {
		if (i >= 0 && i < cs.length)
			cs[i] = v;

	}

}