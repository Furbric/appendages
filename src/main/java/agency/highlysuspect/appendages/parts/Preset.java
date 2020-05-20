package agency.highlysuspect.appendages.parts;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class Preset<T> implements Supplier<T> {
	public Preset(Function<Preset<T>, T> factory, Consumer<T> configurator) {
		this.factory = factory;
		this.configurator = configurator;
	}
	
	private final Function<Preset<T>, T> factory;
	private final Consumer<T> configurator;
	
	public T get() {
		T thing = factory.apply(this);
		configurator.accept(thing);
		return thing;
	}
}
