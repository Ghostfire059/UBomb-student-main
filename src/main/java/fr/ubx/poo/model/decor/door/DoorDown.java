package fr.ubx.poo.model.decor.door;


public class DoorDown extends Door{
	
	@Override
	public boolean isCrossable() {
		return true;
	}
	@Override
	public boolean isTaken() {
		return false;
	}
	
}
