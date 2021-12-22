package me.whiteship.designpatterns;

import me.whiteship.designpatterns.singleton.EarlyInitializationSingleton;
import me.whiteship.designpatterns.singleton.LazyInitializationSingleton;

public class App {

	public static void main(final String[] args) {
		singleton();
	}

	private static void singleton() {
		final EarlyInitializationSingleton early = EarlyInitializationSingleton.getInstance();
		final LazyInitializationSingleton lazy = LazyInitializationSingleton.getInstance();
		System.out.println("end");
	}
}
