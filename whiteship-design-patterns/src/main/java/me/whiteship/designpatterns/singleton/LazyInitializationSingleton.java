package me.whiteship.designpatterns.singleton;

public class LazyInitializationSingleton {

	private LazyInitializationSingleton() {
	}

	private static class LazyInitializationSingletonHolder {

		private static final LazyInitializationSingleton INSTANCE = new LazyInitializationSingleton();
	}

	public static LazyInitializationSingleton getInstance() {
		return LazyInitializationSingletonHolder.INSTANCE;
	}
}
