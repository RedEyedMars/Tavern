package game;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import entities.flora.FloraType;
import entities.flora.Floras;
import entities.monsters.MonsterType;
import entities.monsters.MonsterTypes;

public class Game {
	
	public static final Game game = null;

	private static Timer timer = new Timer();
	
	private GameThread gameThread;
	private boolean running = true;
	private List<Area> areas;
	private List<Double> angles;
	private List<MonsterType> monsters;
	private List<FloraType> floras;
	private Terminal terminal;
	private Area activeArea;
	public Game()
	{
		super();
		
		angles = new ArrayList<Double>();
		monsters = new ArrayList<MonsterType>();
		floras = new ArrayList<FloraType>();
		gameThread = new GameThread(this);
		areas = new ArrayList<Area>();
		for(double angle=0.0;angle<Math.PI*2;angle+=Math.random()*Math.PI/2){
			angles.add(angle);
			monsters.add(MonsterTypes.types.get((int)(MonsterTypes.types.size()*Math.random())));
			floras.add(Floras.types.get((int)(Floras.types.size()*Math.random())));
		}
		while(areas.size()<1)
		{
			Area newArea = new Area(this);
			int distanceFromTav = (int)(Math.random()*100);
			double angleFromTav    = Math.random()*Math.PI*2;
			newArea.initialise(distanceFromTav,angleFromTav);
			areas.add(newArea);
		}
		activeArea = areas.get(0);
		terminal = new Terminal("The Tavern");
		gameThread.start();
	}
	public boolean isRunning()
	{
		return running;
	}
	
	public void update() {
		for(Area area: areas){
			area.update();
		}
		terminal.repaint();
		timer.tick();
	}
	
	public MonsterType approximateMonsterType(double target) {
		for(int i=0;i<angles.size()-1;++i){
			if(target>=angles.get(i)&&target<angles.get(i+1)){
				return monsters.get(i);
			}
		}
		return monsters.get(monsters.size()-1);
	}
	public FloraType approximateFloraType(double target) {
		for(int i=0;i<angles.size()-1;++i){
			if(target>=angles.get(i)&&target<angles.get(i+1)){
				return floras.get(i);
			}
		}
		return floras.get(floras.size()-1);
	}
	
	private class GameThread extends Thread {
		private Game game;
		public GameThread(Game game)
		{
			super();
			this.game = game;
		}
		@Override
		public void run()
		{
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			while(game.isRunning())
			{
				game.update();
				try {
					Thread.sleep(10);
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}
		}
	}
	public class Terminal extends JFrame {
		private static final long serialVersionUID = 1L;

		public Terminal(String name){
			super(name);
			setSize(800,600);
			this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
			setVisible(true);
			this.add(new AreaPanel());
			repaint();
		}
		
	}
	public class AreaPanel extends JPanel {
		private static final long serialVersionUID = 1L;

		@Override
		public void paintComponent(Graphics g){
			super.paintComponent(g);
			Area area = activeArea;
			for(int i=0;i<area.getHeight();++i){
				for(int j=0;j<area.getWidth();++j){
					g.setColor(area.get(j,i).getColour());
					g.fillRect(j*800/area.getWidth(), i*600/area.getHeight(), 800/area.getWidth(), 600/area.getHeight());
				}
			}
		}
	}
	public static String getTime() {
		return timer.toString();
	}
}
