package raw2analysis;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

import org.jdom.*;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

public class ToXML {
	private Document $document = null;
	private Element $event = null;

	private String $writePath = null;

	private Entity_Comments[] $entityComments = null;

	public ToXML(Entity_Comments[] ec, String writePath) {
		$document = new Document();
		$event = new Element("event");
		$document.addContent($event);

		$writePath = writePath;
		$entityComments = ec;
	}

	public void wirteXML() throws FileNotFoundException, IOException {

		for (int i = 0; i < $entityComments.length; i++) {
			$event.addContent(produceEntity($entityComments[i]));
		}

		Format format = Format.getPrettyFormat();
		format.setEncoding("utf-8");
		format.setIndent("\t");

		XMLOutputter out = new XMLOutputter(format);
		out.output($document, new FileOutputStream($writePath));
	}

	public Element produceEntity(Entity_Comments entityComments) {
		Element entity = new Element("ENTITY");

		Element entityName = new Element("EntityName");
		entityName.setText(entityComments.entity);
		entity.addContent(entityName);

		Element entityType = new Element("EntityType");
		entityType.setText(entityComments.type_ent + "");
		entity.addContent(entityType);

		Element entityCount = new Element("EntityCount");
		entityCount.setText(entityComments.count + "");
		entity.addContent(entityCount);

		Element allComments = new Element("Comments");

		HashMap<String, Integer> commentAndCount = entityComments.comments;

		Iterator<String> comments = commentAndCount.keySet().iterator();
		while (comments.hasNext()) {
			String comment = comments.next();
			int Count = commentAndCount.get(comment);

			Element commentWord = new Element("CommentWord");
			entityCount.setText(comment);

			Element commentCount = new Element("CommentCount");
			entityCount.setText(Count + "");

			Element commentInXML = new Element("Comment");

			commentInXML.addContent(commentWord);
			commentInXML.addContent(commentCount);

			allComments.addContent(commentInXML);
		}

		entity.addContent(allComments);
		return entity;
	}
}
