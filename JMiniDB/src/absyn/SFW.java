package absyn;

public class SFW extends Absyn {
	SelList selectList;
	TableList tableList;
	Condition condition;
	AttributeList group;
	HvCondition having;
	AttributeList order;

	public SFW(int p, SelList sl, TableList tl, Condition c, AttributeList g,
			HvCondition h, AttributeList o) {
		pos = p;
		selectList = sl.Resverse();
		// selectList = sl;
		tableList = tl;
		condition = c;
		group = g;
		having = h;
		order = o;
	}

	/**
	 * @return the selectList
	 */
	public SelList getSelectList() {
		return selectList;
	}

	/**
	 * @param selectList
	 *            the selectList to set
	 */
	public void setSelectList(SelList selectList) {
		this.selectList = selectList;
	}

	/**
	 * @return the tableList
	 */
	public TableList getTableList() {
		return tableList;
	}

	/**
	 * @param tableList
	 *            the tableList to set
	 */
	public void setTableList(TableList tableList) {
		this.tableList = tableList;
	}

	/**
	 * @return the condition
	 */
	public Condition getCondition() {
		return condition;
	}

	/**
	 * @param condition
	 *            the condition to set
	 */
	public void setCondition(Condition condition) {
		this.condition = condition;
	}

	/**
	 * @return the group
	 */
	public AttributeList getGroup() {
		return group;
	}

	/**
	 * @param group
	 *            the group to set
	 */
	public void setGroup(AttributeList group) {
		this.group = group;
	}

	/**
	 * @return the having
	 */
	public HvCondition getHaving() {
		return having;
	}

	/**
	 * @param having
	 *            the having to set
	 */
	public void setHaving(HvCondition having) {
		this.having = having;
	}

	/**
	 * @return the order
	 */
	public AttributeList getOrder() {
		return order;
	}

	/**
	 * @param order
	 *            the order to set
	 */
	public void setOrder(AttributeList order) {
		this.order = order;
	}
}
