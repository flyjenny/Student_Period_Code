package query.plan;

import java.util.*;

import absyn.Attribute;

import metadata.Schema;
import query.predicate.AggrFcn;
import query.predicate.Predicate;
import query.scan.*;

public class GroupByPlan implements Plan {

	private Plan plan;
	private Schema schema = new Schema();
	private List<String> groupList;
	private List<AggrFcn> aggrFcn;

	private Predicate having;

	public GroupByPlan(Plan p, List<String> gl, List<AggrFcn> af,
			Predicate having) {
		plan = p;
		groupList = gl;
		aggrFcn = af;
		this.having = having;
		for (String fldn : gl)
			schema.add(fldn, plan.getSchema());
		for (AggrFcn aggf : af)
			schema.addFloatField(aggf.getFieldName());
	}

	public Scan open(Scan sca) {
		List<Integer> orderList = new ArrayList<Integer>(groupList.size());
		for (int i = 0; i < groupList.size(); i++)
			orderList.add(Attribute.ASC);
		Plan p = new SortPlan(plan, groupList, orderList);
		Scan s = p.open(sca);
		return new GroupByScan(s, groupList, aggrFcn, having);
	}

	public int blocksAccessed() {
		// TODO opt-est
		return 0;
	}

	public int recordsOutput() {
		// TODO opt-est
		return 0;
	}

	public int distinctValues(String fldName) {
		// TODO opt-est
		return 0;
	}

	public Schema getSchema() {
		return schema;
	}

}
