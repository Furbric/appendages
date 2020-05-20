package agency.highlysuspect.appendages.parts;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class AppendagePreset implements Supplier<Appendage> {
	public AppendagePreset(Consumer<Appendage> modelConfigurator) {
		this.modelConfigurator = modelConfigurator;
	}
	
	private final Consumer<Appendage> modelConfigurator;
	
	public Appendage get() {
		Appendage wow = new Appendage();
		modelConfigurator.accept(wow);
		return wow;
	}
}
