package absyn;

public class PriKeyDef extends CreateDef {
	AttributeList attributes;

	public PriKeyDef(AttributeList al) {
		attributes = al;
	}

	public AttributeList getPriKeys() {
		return attributes;
	}
}
