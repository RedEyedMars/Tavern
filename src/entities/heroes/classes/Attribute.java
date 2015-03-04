package entities.heroes.classes;

import java.lang.reflect.Field;

import entities.heroes.Hero;

public class Attribute {

	private String fieldName;

	private float scaling;
	private float min;

	private float value;

	public Attribute(String name, float min){
		this.fieldName = name;

		this.min = min;
		this.scaling = 1f;
	}

	public Attribute(String name, float min, float scaling){
		this.fieldName = name;

		this.min = min;
		this.scaling = scaling;
	}

	public void mold(Hero hero) {
		Attribute clone = (Attribute) this.clone();
		float x = (float) hero.getLevel();
		clone.value = (int)(scaling*x*x+min);
		hero.setAttribute(clone);
	}

	public String getName() {
		return fieldName;
	}

	public float getValue() {
		return value;
	}

	public void setValue(float f) {
		value = f;
	}
	
	@Override
	public Object clone(){
		Attribute attr = new Attribute(fieldName,min,scaling);
		attr.value = value;
		return attr;
	}

}
