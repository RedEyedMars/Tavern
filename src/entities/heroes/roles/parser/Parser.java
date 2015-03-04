package entities.heroes.roles.parser;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import system.FileParser;

import entities.heroes.Hero;
import entities.heroes.histories.HistoryEvent;
import entities.heroes.misc.Condition;
import entities.heroes.roles.HeroRole;
import entities.heroes.roles.requirements.HeroRoleRequirement;
import entities.heroes.roles.requirements.HeroRoleRequirementAttributeGreater;
import entities.heroes.roles.requirements.HeroRoleRequirementAttributeLess;
import entities.heroes.roles.requirements.HeroRoleRequirementSkillGreater;
import entities.heroes.roles.requirements.HeroRoleRequirementSkillLess;
import entities.heroes.roles.requirements.HeroRoleRequirementWithSkill;
import entities.heroes.roles.requirements.HeroRoleRequirementWithoutSkill;
import entities.stats.BehaviourGenerator;

public class Parser{
	public static HeroRole parse(String line){
		try {
			line  = FileParser.parseFile(line);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		line = line.replaceAll("##[^\\n]*\\n", "");
		line = line.replaceAll("\\Q...\\E\\s*", "");
		final String[] lines = line.split(";");
		String name = lines[0].trim();
		String requirements = lines[1].trim()+";"+lines[2].trim();
		List<BehaviourGenerator> gens = new ArrayList<BehaviourGenerator>();
		for(String genName:lines[3].split(" ")){
			gens.add(getGenerator(genName.trim()));
		}
		HeroRole role = null;
		try {
			role = new HeroRole(name,getRequirements(requirements),gens.toArray(new BehaviourGenerator[0])){
				private Map<Class<? extends Object>,Command> factorCommands = new HashMap<Class<? extends Object>,Command>();
				private Map<String,Command> evaluateCommands = new HashMap<String,Command>();
				{
					for(String line:lines[4].split(",")){
						String[] split = line.trim().split(":");
						Class<? extends Object> key =  Class.forName("entities.heroes.histories."+split[0]+"Event");
						factorCommands.put(key, getCommand(split[1]));
					}
					for(String line:lines[5].split(",")){
						String[] split = line.trim().split(":");
						String key =  split[0].trim();
						evaluateCommands.put(key, getCommand(split[1]));
					}
				}

				@Override
				public float factor(HistoryEvent event) {
					if(factorCommands.containsKey(event.getClass())){
						return factorCommands.get(event.getClass()).get(event);
					}
					else return 1.0f;
				}

				@Override
				public float evaluate(Hero hero, BehaviourGenerator gen) {
					if(evaluateCommands.containsKey(gen.getName())){
						return evaluateCommands.get(gen.getName()).get(hero);
					}
					else return 0.0f;
				}
			};
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return role;
	}

	private static List<HeroRoleRequirement> getRequirements(String plan){
		List<HeroRoleRequirement> requirements = new ArrayList<HeroRoleRequirement>();
		String[] splitplan = plan.split(";");
		String attributes = splitplan[0];
		String skills = splitplan[1];
		for(String attribute:attributes.split("\\s*,\\s*")){
			if(attribute.contains(">")){
				requirements.add(new HeroRoleRequirementAttributeGreater(attribute.substring(0,attribute.indexOf('>')).trim(),Integer.parseInt(attribute.substring(attribute.indexOf('>')+1))));
			}
			else if(attribute.contains("<")){
				requirements.add(new HeroRoleRequirementAttributeLess(attribute.substring(0,attribute.indexOf('<')).trim(),Integer.parseInt(attribute.substring(attribute.indexOf('<')+1))));
			}
			else {
				System.err.println("ERROR: UNREACHABlE CODE IN HeroRole");
			}
		}
		for(String skill:skills.split("\\s*,\\s*")){
			if(skill.contains(">")){
				requirements.add(new HeroRoleRequirementSkillGreater(skill.substring(0,skill.indexOf('>')).trim(),Integer.parseInt(skill.substring(skill.indexOf('>')+1))));
			}
			else if(skill.contains("<")){
				requirements.add(new HeroRoleRequirementSkillLess(skill.substring(0,skill.indexOf('>')).trim(),Integer.parseInt(skill.substring(skill.indexOf('>')+1))));
			}
			else if(skill.contains("!")){
				requirements.add(new HeroRoleRequirementWithoutSkill(skill.substring(0,skill.indexOf('!')).trim()));
			}
			else {
				requirements.add(new HeroRoleRequirementWithSkill(skill.trim()));
			}
		}
		return requirements;
	}

	private static BehaviourGenerator getGenerator(String name){
		try {
			Class<? extends Object> behaviour = Class.forName("entities.heroes.behaviours."+name+"Behaviour");
			Field generatorField = behaviour.getField("generator");
			BehaviourGenerator gen = (BehaviourGenerator) generatorField.get(null);
			return gen;
		} catch (ClassNotFoundException | NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}

	private static Command getCommand(String string) {
		string = string.trim();
		int tab = -1;
		Stack<Command> stack = new Stack<Command>();
		for(String line:string.split("\\n")){
			int temp = line.lastIndexOf('\t');
			if(temp<tab){
				do{
					Command popped = stack.pop();
					stack.peek().add(popped);
				} while(stack.peek().elsest!=null);
			}
			tab = temp;
			try {
				stack.push(new ReturnCommand(Float.parseFloat(line.trim())));
			}
			catch(NumberFormatException e){
				line = line.trim();
				if(line.contains(" is a ")){
					String[] splitz = line.split(" is a ");
					String right = splitz[1];
					String left = splitz[0];
					if(left.startsWith("hero.")||left.startsWith("ev.")){
						GetObjectCommand command = getGetFromAccessOperators(left,null);						
						stack.push(new InstanceOfCommand(command,right.trim()));
					}
				}
				else if(line.contains(" is ")){
					String[] splitz = line.split(" is ");
					String right = splitz[1];
					String left = splitz[0];
					if(left.startsWith("hero.")||left.startsWith("ev.")){
						GetObjectCommand command = getGetFromAccessOperators(left,null);						
						if("null".equals(right.trim())){
							stack.push(new EqualsNullCommand(command));
						}
						else {
							stack.push(new StringEqualsCommand(command,right.trim()));
						}

					}
				}
				else if(line.trim().startsWith("calculate ")){
					line = line.substring(10).trim();
					Command command = getOperatorCommand(line,null);
					stack.push(command);
				}
				else if(line.contains("_")){
					String[] splitz = line.split("_");
					String right = splitz[1];
					String left = splitz[0];
					if(left.startsWith("hero.")||left.startsWith("ev.")){
						GetObjectFromObjectCommand command = (GetObjectFromObjectCommand) getGetFromAccessOperators(left,null);					
						stack.push(new BooleanWithParameterCommand(command.command,command.name,right.trim()));
					}
				}

			}
		}
		while(stack.size()>1){
			Command popped = stack.pop();
			stack.peek().add(popped);
		}
		
		return stack.pop();
	}

	private static GetObjectCommand getGetFromAccessOperators(String left,List<String> protect) {
		GetObjectCommand command = new GetObjectFromParameterCommand();	
		int index = -1;
		do {
			index = left.indexOf('.');
			left = left.substring(index+1);	
			if(index>=0){
				int next = left.indexOf('.');
				if(next>=0){
					command = new GetObjectFromObjectCommand(command,left.substring(0,next));
				}
				else {
					if(!left.contains("_")){
						command = new GetObjectFromObjectCommand(command,left);
					}
					else {
						List<String> params = new ArrayList<String>();
						params.add(left.substring(0,left.indexOf('_')));
						left = left.substring(left.indexOf('_'));
						while(left.length()>0&&left.contains("_")){

							int nnext = left.indexOf('_',1);
							if(left.startsWith("_if")){
								int p = Integer.parseInt(left.substring(4,left.indexOf('$',4)));
								params.add("$"+protect.get(p).substring(1,protect.get(p).length()-1));
							}
							else {
								if(nnext>=0){
									params.add(left.substring(1,nnext));
								}
								else {
									params.add(left.substring(1));
								}
							}
							if(nnext>=0){
								left = left.substring(nnext);
							}
							else {
								left = "";
							}
						}
						command = new GetObjectFromObjectWithParametersCommand(command,params.toArray(new String[0]));
					}
				}
			}
		}	
		while(index!=-1);
		return command;
	}

	private static GetObjectCommand getOperatorCommand(String line, List<String> protect){
		if(protect==null){
			protect = new ArrayList<String>();
		}
		int i = line.indexOf('(');
		while(i>=0){
			StringBuilder builder = new StringBuilder();
			int brace = 1;
			int j = i;
			for(++i;i<line.length()&&brace>0;++i){
				if(line.charAt(i)=='('){
					++brace;
				}
				else if(line.charAt(i)==')'){
					--brace;
				}
				if(brace>0){
					builder.append(line.charAt(i));
				}
			}
			String string = builder.toString();
			line = line.substring(0,j)+"$"+protect.size()+"$"+line.substring(j+string.length()+2);
			protect.add("("+string+")");
			i = line.indexOf('(');
		}
		for(char o:new char[]{'>','<','=','!','+','-','*','/'}){
			int index = line.indexOf(o);
			if(index!=-1){
				String left = line.substring(0,index).trim();
				String right = line.substring(index+1).trim();
				return new OperatorCommand(getOperatorCommand(left,protect),o,getOperatorCommand(right,protect));
			}
		}
		try {
			return new ReturnCommand(Float.parseFloat(line.trim()));
		}
		catch(NumberFormatException e){
			return getGetFromAccessOperators(line.trim(),protect);
		}
	}

	private static class ReturnCommand extends GetObjectCommand {
		private float value;
		public ReturnCommand(float value){
			this.value = value;
		}
		@Override
		public float get(Object ev){
			return value;
		}
		public String toString(){
			return "[["+value+"]]";
		}
		@Override
		public Object getValue(Object ev) {
			return value;
		}
	}
	private static class InstanceOfCommand extends Command {
		private String instof;
		private GetObjectCommand left;
		public InstanceOfCommand(GetObjectCommand left,String string){
			this.left = left;
			instof = string;
		}

		@Override
		public float get(Object ev){
			if(left.getValue(ev).getClass().getSimpleName().equals(instof)){
				return ifst.get(ev);
			}
			else {
				return elsest.get(ev);
			}
		}
		public String toString(){
			return "[isA:"+instof+"{"+ifst+","+elsest+"}]";
		}
	}
	private static class EqualsNullCommand extends Command {
		private GetObjectCommand left;
		public EqualsNullCommand(GetObjectCommand left){
			this.left = left;
		}

		@Override
		public float get(Object ev){
			if(left.getValue(ev)==null){
				return ifst.get(ev);
			}
			else {
				return elsest.get(ev);
			}
		}
		public String toString(){
			return "[null:"+"{"+ifst+","+elsest+"}]";
		}
	}
	private static class StringEqualsCommand extends Command {
		private String instof;
		private GetObjectCommand left;
		public StringEqualsCommand(GetObjectCommand left,String string){
			this.left = left;
			instof = string;
		}

		@Override
		public float get(Object ev){
			if(((String)left.getValue(ev)).equals(instof)){
				return ifst.get(ev);
			}
			else {
				return elsest.get(ev);
			}
		}
		public String toString(){

			return "[==:"+instof+"{"+ifst+","+elsest+"}]";
		}
	}

	private static abstract class GetObjectCommand extends Command implements Condition {
		public abstract Object getValue(Object ev);
		@Override
		public boolean satisfies(Hero hero) {
			return (boolean) getValue(hero);
		}
	}
	private static class GetObjectFromObjectCommand extends GetObjectCommand {
		private String name;
		private GetObjectCommand command;
		public GetObjectFromObjectCommand(GetObjectCommand command,String name){
			this.name = name;
			this.command = command;
		}
		public Object getValue(Object ev){
			try {
				Object object = command.getValue(ev); 
				Object ret = object.getClass().getMethod("get"+name.substring(0,1).toUpperCase()+name.substring(1), new Class[]{}).invoke(object, new Object[]{});
				if(ret instanceof Integer){
					return Float.parseFloat(ret.toString());
				}
				return ret;
			} catch (IllegalAccessException | IllegalArgumentException
					| InvocationTargetException | NoSuchMethodException
					| SecurityException e) {
				e.printStackTrace();
			}
			return null;
		}
	}

	private static class GetObjectFromObjectWithParametersCommand extends GetObjectCommand {
		private String name;
		private String[] parameters;
		private List<Condition> conditionsObjects;
		private GetObjectCommand command;
		public GetObjectFromObjectWithParametersCommand(GetObjectCommand command,String... name){
			this.name = name[0];
			this.parameters = new String[name.length-1];
			this.conditionsObjects = new ArrayList<Condition>();
			for(int i=0;i<parameters.length;++i){
				if(name[i+1].startsWith("$")){
					parameters[i]="$"+conditionsObjects.size();
					conditionsObjects.add(getOperatorCommand(name[i+1],null));
				}
				else {
					parameters[i]=name[i+1];
				}
			}
			this.command = command;
		}
		public Object getValue(Object ev){
			try {
				@SuppressWarnings("rawtypes")
				List<Class> classes = new ArrayList<Class>();
				List<Object> params = new ArrayList<Object>();
				for(String param:parameters){
					if("Self".equals(param)){
						classes.add(ev.getClass());
						params.add(ev);
					}
					else if(param.startsWith("$")){
						classes.add(Condition.class);
						params.add(conditionsObjects.get((Integer.parseInt(param.substring(1)))));
					}
					else {
						try {
							params.add(Float.parseFloat(param));
							classes.add(Float.class);
						}
						catch(NumberFormatException e){
							if("True".equals(param)){
								classes.add(Boolean.class);
								params.add(true);
							}
							else if("False".equals(param)){
								classes.add(Boolean.class);
								params.add(false);
							}
							else {
								classes.add(String.class);
								params.add(param);
							}
						}
					}
				}
				Object object = command.getValue(ev);
				Object ret = object.getClass().getMethod("get"+name.substring(0,1).toUpperCase()+name.substring(1), classes.toArray(new Class[0])).invoke(object,params.toArray(new Object[0]));
				if(ret instanceof Integer){
					return (float)ret;
				}
				return ret;
			} catch (IllegalAccessException | IllegalArgumentException
					| InvocationTargetException | NoSuchMethodException
					| SecurityException e) {
				e.printStackTrace();
			}
			return null;
		}
	}
	private static class BooleanWithParameterCommand extends Command {
		private String param;
		private String name;
		private GetObjectCommand command;
		public BooleanWithParameterCommand(GetObjectCommand command,String name,String param){
			this.param = param;
			this.name = name;
			this.command = command;
		}
		public float get(Object ev){
			try {
				Object object = command.getValue(ev);
				Boolean value = (Boolean)object.getClass().getMethod("get"+name.substring(0,1).toUpperCase()+name.substring(1), new Class[]{String.class}).invoke(object, new Object[]{param});
				if(value){
					return ifst.get(ev);
				}
				else {
					return elsest.get(ev);
				}
			} catch (IllegalAccessException | IllegalArgumentException
					| InvocationTargetException | NoSuchMethodException
					| SecurityException e) {
				e.printStackTrace();
			}
			return -1;
		}
		public String toString(){
			return "[bool:"+param+"{"+ifst+","+elsest+"}]";
		}
	}
	private static class GetObjectFromParameterCommand extends GetObjectCommand {
		public Object getValue(Object ev){
			return ev;
		}
	}

	private static class OperatorCommand extends GetObjectCommand {

		private GetObjectCommand left;
		private GetObjectCommand right;
		private char o;
		public OperatorCommand(GetObjectCommand left, char o,GetObjectCommand right) {
			this.o = o;
			this.left = left;
			this.right = right;
		}

		@Override
		public Object getValue(Object ev) {
			if('*'==o){
				return ((float)left.getValue(ev))*((float)right.getValue(ev));
			}
			else if('/'==o){
				return ((float)left.getValue(ev))/((float)right.getValue(ev));
			}
			else if('+'==o){
				return ((float)left.getValue(ev))+((float)right.getValue(ev));
			}
			else if('-'==o){
				return ((float)left.getValue(ev))-((float)right.getValue(ev));
			}
			else if('>'==o){
				return ((float)left.getValue(ev))>((float)right.getValue(ev));
			}
			else if('<'==o){
				return ((float)left.getValue(ev))<((float)right.getValue(ev));
			}
			else if('='==o){
				return ((float)left.getValue(ev))==((float)right.getValue(ev));
			}
			else if('!'==o){
				return ((float)left.getValue(ev))!=((float)right.getValue(ev));
			}
			return null;
		}

		@Override
		public float get(Object ev){
			return ((float)this.getValue(ev));
		}

	}
}
