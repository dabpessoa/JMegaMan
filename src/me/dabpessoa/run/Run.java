package me.dabpessoa.run;

import me.dabpessoa.game.World;

import java.awt.EventQueue;

public class Run {

	public static void main(String args[]) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {

				World world = new World();
				world.initWorld();
				world.start();

			}
		});
	}

}
