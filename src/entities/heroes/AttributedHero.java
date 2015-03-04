package entities.heroes;

import java.util.HashMap;
import java.util.Map;

import entities.heroes.classes.Attribute;
import entities.heroes.classes.ClassedHero;

public abstract class AttributedHero extends ClassedHero{


	private Map<String, Attribute> attributes;
	public AttributedHero(String name) {
		super(name);
		attributes = new HashMap<String,Attribute>();
	}
	public float getAttribute(String name) {
		Attribute attribute = getHeroClass().getAttribute(name);
		if(attribute!=null){
			return attribute.getValue();
		}
		return -1f;
	}

	public void setAttribute(Attribute attr) {
		attributes.put(attr.getName(), attr);
	}
	protected void setAttributeValue(String string, float f) {
		attributes.get(string).setValue(f);		
	}
	
	protected boolean attributeLessThan(String string, float f) {
		return getAttribute(string)<f;
	}

	protected boolean attributeGreaterThan(String string, float f) {
		return getAttribute(string)>f;
	}
	protected void attributeMinus(String string, float f) {
		setAttributeValue(string,getAttribute(string)-f);
	}

	protected void attributeAdd(String string, float f) {
		setAttributeValue(string,getAttribute(string)+f);
	}
	protected void attributeMultiply(String string, float f) {
		setAttributeValue(string,getAttribute(string)*f);
	}
	protected float getAttributeSquared(String string) {
		return (float) Math.pow(getAttribute(string),2);
	}

	public float getLevel() {
		return attributes.containsKey("level")?getAttribute("level"):1f;
	}
	public void useAttribute(String string, float f) {
		setAttributeValue(string,getAttribute(string)-f);
	}

	public void boostAttribute(String string, float f) {
		setAttributeValue(string,getAttribute(string)+f);
	}
}
