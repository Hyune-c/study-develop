package me.whiteship.designpatterns.singleton;

public class EarlyInitializationSingleton {

	private static final EarlyInitializationSingleton INSTANCE = new EarlyInitializationSingleton();

	private EarlyInitializationSingleton() {
	}

	public static EarlyInitializationSingleton getInstance() {
		return INSTANCE;
	}
}
