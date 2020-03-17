package agency.highlysuspect.appendages.ui;

import net.minecraft.util.Util;

public class Easer {
	public Easer(float value, float strength) {
		this(value, value, strength);
	}
	
	public Easer(float value, float target, float strength) {
		this(value, target, strength, 0L);
	}
	
	public Easer(float value, float target, float strength, long delay) {
		this.value = value;
		this.target = target;
		this.strength = strength;
		this.startTime = Util.getMeasuringTimeMs() + delay;
	}
	
	float value;
	float target;
	float strength;
	long startTime;
	
	public void ease(float delta) {
		if(Util.getMeasuringTimeMs() >= startTime) {
			float clamped = strength * delta;
			if(clamped > 1) clamped = 1;
			value += (target - value) * clamped;
		}
	}
	
	public float easeAndGet(float delta) {
		ease(delta);
		return value;
	}
	
	public float get() {
		return value;
	}
	
	public void setTarget(float target) {
		this.target = target;
	}
	
	public void setStrength(float strength) {
		this.strength = strength;
	}
}
