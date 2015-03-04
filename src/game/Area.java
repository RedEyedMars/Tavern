package game;
import java.util.*;
import entities.*;
import entities.flora.Flora;
import entities.flora.FloraType;
import entities.heroes.Hero;
import entities.monsters.Monster;
import entities.monsters.MonsterType;
public class Area
{
	private List<Entity> entities;
	private List<Monster> monsters;
	private List<Hero> heroes;
	private Map<MonsterType,ArrayList<Monster>> monsterTypes;
	private List<Flora> flora;
	private List<Square> map;
	private double distanceFromTavern;
	private double angleFromTavern;
	private Game game;
	private Monster alpha;
	private int height = 50;
	private int width = 50;
	public Area(Game game)
	{
		super();
		this.game = game;
		entities = new ArrayList<Entity>();
		monsters = new ArrayList<Monster>();
		heroes = new ArrayList<Hero>();
		monsterTypes = new HashMap<MonsterType,ArrayList<Monster>>();
		flora = new ArrayList<Flora>();
		map = new ArrayList<Square>();
	}
	public void initialise(double distance, double angle)
	{
		initialiseMap();
		distanceFromTavern = distance;
		angleFromTavern = angle;
		distance+=5;
		int numberOfMonstersToSpawn = 20;//(int)(130.0*Math.log(distance)-180);
		MonsterType mainMonster = game.approximateMonsterType(angle);

		monsterTypes.put(mainMonster, new ArrayList<Monster>());
		for(int m=0;m<numberOfMonstersToSpawn/2;++m)
		{
			Monster newMonster = mainMonster.create(map,this);
			if(newMonster!=null){
				entities.add(newMonster);
				monsters.add(newMonster);
				monsterTypes.get(mainMonster).add(newMonster);
			}
		}
		alpha = mainMonster.createAlpha(map,this);
		if(alpha!=null){
			entities.add(alpha);
			monsters.add(alpha);
			monsterTypes.get(mainMonster).add(alpha);
		}
		for(int m=0;m<numberOfMonstersToSpawn/4;++m)
		{
			Monster newMonster = mainMonster.createRival(map,this);
			if(newMonster!=null){
				entities.add(newMonster);
				monsters.add(newMonster);
				if(!monsterTypes.containsKey(newMonster.getType())){
					monsterTypes.put(newMonster.getType(), new ArrayList<Monster>());
				}
				monsterTypes.get(newMonster.getType()).add(newMonster);
			}			
		}
		for(int m=0;m<numberOfMonstersToSpawn/4;++m)
		{
			Entity newMonster = mainMonster.createPrey(map,this);
			entities.add(newMonster);
			if(newMonster instanceof Monster){
				monsters.add((Monster)newMonster);
				if(!monsterTypes.containsKey(((Monster)newMonster).getType())){
					monsterTypes.put(((Monster)newMonster).getType(), new ArrayList<Monster>());
				}
				monsterTypes.get(((Monster)newMonster).getType()).add((Monster)newMonster);
			}
			else if(newMonster instanceof Flora){
				flora.add((Flora)newMonster);
			}
		}
		FloraType mainFlora = game.approximateFloraType(angle);
		for(int m=0;m<numberOfMonstersToSpawn/2;++m)
		{
			Flora newFlora = mainFlora.create(this);
			if(newFlora!=null){
				entities.add(newFlora);
				flora.add(newFlora);
			}
		}
		for(int m=0;m<numberOfMonstersToSpawn/4;++m)
		{
			Flora newFlora = mainFlora.createRival(this);
			if(newFlora!=null){
				entities.add(newFlora);
				flora.add(newFlora);
			}

		}
		for(int m=0;m<numberOfMonstersToSpawn/2;++m)
		{
			Flora newFlora = mainFlora.createPrey(this);
			if(newFlora!=null){
				entities.add(newFlora);
				flora.add(newFlora);
			}
		}
	}

	public Square getRandomSquare()
	{
		return map.get((int)(map.size()*Math.random()));
	}
	public Square get(Square square, Direction direction){
		return get(square.getX()+direction.getX(),square.getY()+direction.getY());
	}
	public Square get(int i, int j) {
		if(i<width&&j<height&&i>=0&&j>=0){
			return map.get(i+height*j);
		}
		return null;
	}
	public void addMonster(Monster monster) {
		entities.add(monster);
		monsters.add(monster);
		if(!monsterTypes.containsKey(monster.getType())){
			monsterTypes.put(monster.getType(), new ArrayList<Monster>());
		}
		monsterTypes.get(monster.getType()).add(monster);
	}
	public void addFlora(Flora flora) {
		entities.add(flora);
		this.flora.add(flora);
	}
	public void removeMonster(Monster monster) {
		entities.remove(monster);
		monsters.remove(monster);
		if(monsterTypes.containsKey(monster.getType())){
			monsterTypes.get(monster.getType()).remove(monster);
		}
	}
	public void removeFlora(Flora flora) {
		entities.remove(flora);
		this.flora.remove(flora);
	}
	public Square get(Square square) {
		return get(square.getX(),square.getY());
	}
	public List<Square> getSurroundingSquares(Square square, int radius) {
		List<Square> squares = new ArrayList<Square>();
		int x=square.getX();
		int y=square.getY();
		for(int m=0;m<=radius;++m){

			for(int n=m-radius;n<=radius-m;++n){
				if(m==0&&n==0)
					continue;
				else if(x+n>=0&&x+n<width&&y+m>=0&&y+m<height){
					squares.add(map.get((x+n)+height*(y+m)));
				}
			}
		}
		for(int m=-radius;m<0;++m){			
			for(int n=radius+m;n>=-radius-m;--n){
				if(x+n>=0&&x+n<width&&y+m>=0&&y+m<height){
					squares.add(map.get((x+n)+height*(y+m)));
				}
			}
		}/*
        for(int i=20;i<30;++i){
        	for(int j=20;j<30;++j){
            	int found = 0;
            	for(Square sq:squares){
                	if(sq.getX()==i&&sq.getY()==j){
                		found++;
                	}
                }
            	if(found>0)System.out.print("["+found+"]");
            	else System.out.print("[ ]");
            }
        	System.out.println();
        }*/

		return squares;
	}

	public void initialiseMap() {
		for(int m=0;m<height;++m)
		{
			for(int n=0;n<width;++n)
			{
				map.add(new Square(n,m));
			}
		}
	}
	public boolean hasEntity(Entity target) {
		return entities.contains(target);
	}
	public void update() {
		int size = entities.size();
		for(int i=0;i<size&&i<entities.size();++i){
			entities.get(i).update();
		}/*
		System.out.println("bear:"+monsterTypes.get(MonsterTypes.bear).size());
		System.out.println("wolf:"+monsterTypes.get(MonsterTypes.wolf).size());
		System.out.println("rabbit:"+monsterTypes.get(MonsterTypes.rabbit).size());
		 */
		for(MonsterType monsterType:monsterTypes.keySet()){
			if(monsterTypes.get(monsterType).size()==0){
				for(int m=0;m<10;++m)
				{
					Entity newMonster = monsterType.create(map,this);
					if(newMonster!=null){
						entities.add(newMonster);
						monsters.add((Monster)newMonster);
						if(!monsterTypes.containsKey(((Monster)newMonster).getType())){
							monsterTypes.put(((Monster)newMonster).getType(), new ArrayList<Monster>());
						}
						monsterTypes.get(((Monster)newMonster).getType()).add((Monster)newMonster);
					}
				}
			}
		}

	}
	public int monsterTypeRatio(MonsterType type) {

		return 100*monsterTypes.get(type).size()/entities.size();
	}

	public int entityRatio() {

		return 100*entities.size()/map.size();
	}
	public int numberOfEntities() {
		return entities.size();
	}
	public int entityRatio(MonsterType type) {

		return 200*monsterTypes.get(type).size()/map.size();
	}
	public int getHeight() {
		return height;
	}
	public int getWidth() {
		return width;
	}
	public void removeHero(Hero hero) {
		heroes.remove(hero);
		entities.remove(hero);
	}
	public void addHero(Hero hero) {
		entities.add(hero);
		heroes.add(hero);
		hero.resetSquare();
	}
	public double getDistanceFromTavern() {
		return distanceFromTavern;
	}
	public double getAngleFromTavern() {
		return angleFromTavern;
	}
}
