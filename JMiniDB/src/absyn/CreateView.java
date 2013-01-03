package absyn;

public class CreateView extends Update {
	String viewName;
	Query view;

	public CreateView(int p, String vn, Query q) {
		pos = p;
		viewName = vn;
		view = q;
	}

	public String getViewName() {
		return viewName;
	}

	public Query getView() {
		return view;
	}
}
