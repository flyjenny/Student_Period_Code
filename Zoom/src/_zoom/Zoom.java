/**
 * @author BennyChan
 * Copyright ® 2010 Shanghai
 * 
 * 
 */

package _zoom;
import java.util.*;

//Interface for fly.
interface CanFly{
	void fly();
}
//Interface for fight.
interface CanFight{
	void fight();
}
//Interface for swim.
interface CanSwim{
	void swim();
}
//Interface for shout.
interface CanShout{
	void shout();
}
//Interface for eat.
interface EatWhat{
	void eat();
}
//Interface for habitat.
interface Habitat{
	void habitat();
}

//Base class and the definition of interface
class Animal implements CanFly,CanFight,CanSwim,CanShout,EatWhat,Habitat{
	Animal(String name,int breath,int alive,
			int fly,int fight,int swim,
			int shout,int eat,int habitat){
		$name=name;
		$breath=breath;
		$alive=alive;
		$fly=fly;
		$fight=fight;
		$swim=swim;
		$shout=shout;
		$eat=eat;
		$habitat=habitat;
	}
	public void name(){System.out.println("Hello. I am "+$name+".");}
	public void breath(){
		if ($breath==0)
			System.out.println("I do not need breath!");
		else
			System.out.println("I need breath!");
	}
	public void alive() {
		if ($alive==0)
			System.out.println("I'm dead!!");
		else
			System.out.println("I am still alive!");
	}
	public void fly(){
		if ($fly==0)
			System.out.println("I can not fly!");
		else
			System.out.println("I can fly!");
	}
	public void fight(){
		if ($fight==0)
			System.out.println("I can not Fight!");
		else
			System.out.println("I can Fight!");
	}
	public void swim(){
		if($swim==0)
			System.out.println("I can not swim!");
		else 
			System.out.println("I can swim!");
	}
	public void shout(){
		if($shout==0)
			System.out.println("I can not shout!");
		else 
			System.out.println("I can shout!");
	}
	public void eat(){
		if ($eat==0)
			System.out.println("I am a vegetarian!");
		else 
			System.out.println("I am a carnivore!");
	}
	public void habitat(){
		if ($habitat==0)
			System.out.println("I am hydrophilous!");
		else
			System.out.println("I am terraneous!");
	}
	
	private String $name;
	private int $breath;
	private int $alive;
	private int $fly;
	private int $fight;
	private int $swim;
	private int $shout;
	private int $eat;
	private int $habitat;
}

//Derive class from Animal
class Homoiotherm extends Animal{
	Homoiotherm(String name, int breath, int alive,
				int fly, int fight,int swim,
				int shout,int eat, int habitat) {
		super(name, breath, alive, fly, fight, swim, shout, eat, habitat);
	}

	public void homoiothermous(){System.out.println("I am homoiotherous!");}
}
//Derive class from Animal
class Poikilotherm extends Animal{
	Poikilotherm(String name, int breath, int alive,
				int fly, int fight,int swim,
				int shout,int eat, int habitat) {
		super(name, breath, alive, fly, fight, swim, shout, eat, habitat);
	}

	public void erraticTemperature(){System.out.println("I hava an erratic animal heat!");}
}

public class Zoom {
	public static void main(String[] agrs){
		Animal[] animal={
		new Homoiotherm("Tiger",1,1,0,1,1,1,1,1),
		new Homoiotherm("Ostrich",1,1,0,0,0,1,0,1),
		new Homoiotherm("Parrot",1,1,1,0,0,1,0,1),
		new Poikilotherm("Crocodile",1,1,0,1,1,0,1,1),
		new Homoiotherm("Shark",1,1,0,1,1,0,1,0),
		};
		System.out.println("Welcome to my zoom!");
		System.out.println("Here are some kind of animals in my zoom!");
		System.out.println();
		for (int i=0;i<(5);i++){
			animal[i].name();
			animal[i].breath();
			animal[i].fly();
			animal[i].fight();
			animal[i].swim();
			animal[i].shout();
			animal[i].eat();
			animal[i].habitat();
			animal[i].alive();
			System.out.println();
		}	

		String NAME;
		int attrib;
		int BREATH;
		int ALIVE;
		int FLY;
		int FIGHT;
		int SWIM;
		int SHOUT;
		int EAT;
		int HABITAT;

				
		System.out.println("If you want to add new kind of animals.Press 1 ");
		Scanner stdin=new Scanner(System.in);
		while (stdin.nextInt()==1){
			System.out.println("Please follow these steps: ");
			System.out.print("Is this animal Homoiotherm? Press 1 means YES,0 means NO :");
			attrib=stdin.nextInt();
			System.out.print("Enter the name of animal: ");
			NAME=stdin.next();
			System.out.print("Whether this animal needs breath? Press 1 means YES,0 means NO :");
			BREATH=stdin.nextInt();
			System.out.print("Whether this animal can fly? Press 1 means YES,0 means NO :");
			FLY=stdin.nextInt();
			System.out.print("Whether this animal can fight? Press 1 means YES,0 means NO :");
			FIGHT=stdin.nextInt();
			System.out.print("Whether this animal can swim? Press 1 means YES,0 means NO :");
			SWIM=stdin.nextInt();
			System.out.print("Whether this animal can shout? Press 1 means YES,0 means NO :");
			SHOUT=stdin.nextInt();
			System.out.print("What is this kind of animal? Press 1 means FLESH-EATER,0 means HERBIVORE :");
			EAT=stdin.nextInt();
			System.out.print("Where do this aniaml live? Press 1 means land,0 means water :");
			HABITAT=stdin.nextInt();
			System.out.print("Whether this animal still alive? Press 1 means YES,0 means NO :");
			ALIVE=stdin.nextInt();
			
			if (attrib==0){
				Poikilotherm NewAnimal=new Poikilotherm(NAME,BREATH,ALIVE,FLY,FIGHT,SWIM,SHOUT,EAT,HABITAT);
				NewAnimal.name();
				NewAnimal.breath();
				NewAnimal.fly();
				NewAnimal.fight();
				NewAnimal.swim();
				NewAnimal.shout();
				NewAnimal.eat();
				NewAnimal.habitat();
				NewAnimal.alive();
				System.out.println();
			}
			if (attrib==1){
				Homoiotherm NewAnimal =new Homoiotherm(NAME,BREATH,ALIVE,FLY,FIGHT,SWIM,SHOUT,EAT,HABITAT);
				NewAnimal.name();
				NewAnimal.breath();
				NewAnimal.fly();
				NewAnimal.fight();
				NewAnimal.swim();
				NewAnimal.shout();
				NewAnimal.eat();
				NewAnimal.habitat();
				NewAnimal.alive();
				System.out.println();
			}
			
			System.out.println("If you want to add new kind of animals.Press 1 ");
		}
	}
}
