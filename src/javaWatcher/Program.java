package javaWatcher;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.List;

public class Program {

	public static void main(String[] args) {

		Path path = Paths.get("/tmp/testeWatcher");

		try {
			WatchService watchService = path.getFileSystem().newWatchService();
			path.register(watchService, StandardWatchEventKinds.ENTRY_CREATE);

			WatchKey watckKey;
			while ((watckKey = watchService.take()) != null) {

				List<WatchEvent<?>> events = watckKey.pollEvents();
				for (WatchEvent<?> event : events) {
					if (event.kind() == StandardWatchEventKinds.ENTRY_CREATE) {
						System.out.println("Created: " + event.context().toString());
					}
				}

				watckKey.reset();
			}
		} catch (IOException e) {
			System.out.println("IO ERROR: " + e.getMessage());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

}
