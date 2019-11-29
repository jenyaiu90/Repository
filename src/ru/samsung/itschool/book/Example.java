package ru.samsung.itschool.book;

<<<<<<< HEAD
class Unit {
    public String name;
    public int health;
}

class Robot extends Unit {
    private int armor;
}

class Wizard extends Unit {
    private int mana;
}

class Warrior extends Unit {
    public void attack() {}
}

public class Example {

	public static void main(String[] args) {
		System.out.println("Hello");
	}

}


=======
class Unit{
	public String className(){ 
		return "Unit"; 
	}
}

class Robot extends Unit{
	public String className(){ 
		return "Robot";
	}
}

class Wizard extends Unit{
	public String className(){
		return "Wizard"; 
	}
}

class Terminator extends Robot{
	public String className()
	{ 
		return "Terminator"; 
	}
}

public class Example{
	public static void main(String [] args){
		Unit unit = new Unit();
		Robot robot = new Robot();
		Wizard wizard = new Wizard();
		Terminator terminator = new Terminator();
	
		Unit [] units = {unit, robot, wizard, terminator};
		for(Unit u: units)
			System.out.println(u.className());
	}
}

>>>>>>> develop
