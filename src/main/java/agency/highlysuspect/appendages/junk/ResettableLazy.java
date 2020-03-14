package agency.highlysuspect.appendages.junk;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class ResettableLazy<T> {
	public ResettableLazy(Supplier<T> factory) {
		this.factory = factory;
	}
	
	private final Supplier<T> factory;
	private T thing;
	
	public T getOrCompute() {
		if(thing == null) {
			thing = factory.get();
			if(thing == null) throw new NullPointerException("resettablelazy supplier returned null object");
		}
		return thing;
	}
	
	//TODO: threaded version that submits factory.get() to some kind of executor? is that possible? look in to java threading
	
	public void reset() {
		thing = null;
	}
	
	public static class Category<T> {
		private final List<ResettableLazy<T>> lazies = new ArrayList<>();
		
		public ResettableLazy<T> create(Supplier<T> factory) {
			ResettableLazy<T> lazy = new ResettableLazy<>(factory);
			lazies.add(lazy);
			return lazy;
		}
		
		public void resetAll() {
			lazies.forEach(ResettableLazy::reset);
		}
	}
}
