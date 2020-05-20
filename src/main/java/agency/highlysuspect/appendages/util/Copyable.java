package agency.highlysuspect.appendages.util;

import java.lang.reflect.Array;

/**
 * Knockoff of Object.clone() because Cloneable sucks.
 * @param <T> Yourself.
 */
public interface Copyable<T> {
	T copy();
	
	default <X extends Copyable<X>> X[] copyArray(X[] src, Class<X> fuckJava) {
		//noinspection unchecked
		X[] dst = (X[]) Array.newInstance(fuckJava, src.length);
		for(int i = 0; i < src.length; i++) {
			dst[i] = src[i].copy();
		}
		return dst;
	}
}
