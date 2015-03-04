package entities.heroes.roles.parser;


public class Command {
	protected Command ifst;
	protected Command elsest;
	public void add(Command popped) {
		if(ifst==null){
			ifst = popped;
		}
		else if(elsest==null){
			elsest = popped;
		}
		else {
		elsest.add(popped);
		}
	}
	public float get(Object ev){
		return ifst.get(ev);
	}
}