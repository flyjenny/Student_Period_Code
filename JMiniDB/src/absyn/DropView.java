package absyn;

public class DropView extends Update {
	String viewName;

	public DropView(int p, String vn) {
		pos = p;
		viewName = vn;
	}

	public String getViewName() {
		return viewName;
	}
}
