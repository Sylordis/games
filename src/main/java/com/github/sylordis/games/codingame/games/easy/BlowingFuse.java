package com.github.sylordis.games.codingame.games.easy;

//import static utils.DebugUtils.debug;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class BlowingFuse {

	private class Device {
		private final int consumption;

		public Device(int consumption) {
			this.consumption = consumption;
		}

		/**
		 * @return the consumption
		 */
		public int getConsumption() {
			return consumption;
		}

	}

	private List<Device> devices;
	private final int capacity;

	public BlowingFuse(int capacity) {
		this.devices = new ArrayList<>();
		this.capacity = capacity;
	}

	private class FuseBlownException extends Exception {

	}

	/**
	 * Creates a device with given consumption. Devices id will be as per inserting order.
	 *
	 * @param consumption
	 *            Consumption of the device.
	 */
	public void addDevice(int consumption) {
		devices.add(new Device(consumption));
		// debug("New device[{}]={}", devices.size() - 1, consumption);
	}

	/**
	 * Resolves the test to see if the fuse blows.
	 *
	 * @param switches
	 * @return the max consumption
	 * @throws FuseBlownException
	 *             if the consumption of all devices exceed the capacity of the fuse
	 */
	public int resolve(int[] switches) throws FuseBlownException {
		int current = 0;
		int max = 0;
		// Array to see if a device is on (true) or off (false)
		boolean[] onOff = new boolean[devices.size()];
		Arrays.fill(onOff, false);
		for (int id : switches) {
			final int consumption = devices.get(id - 1).getConsumption();
			if (!onOff[id - 1]) {
				current += consumption;
				max = Math.max(max, current);
				// debug("[{}] on, {}A, max={}", id, current, max);
				if (current >= capacity)
					throw new FuseBlownException();
			} else {
				current -= consumption;
				// debug("[{}] off, {}A", id, current);
			}
			onOff[id - 1] = !onOff[id - 1];
		}
		return max;
	}

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int nDevices = in.nextInt();
		int nClicking = in.nextInt();
		int fuseCapacity = in.nextInt();
		BlowingFuse fuse = new BlowingFuse(fuseCapacity);
		for (int id = 0; id < nDevices; id++)
			fuse.addDevice(in.nextInt());
		int[] switches = new int[nClicking];
		for (int s = 0; s < nClicking; s++)
			switches[s] = in.nextInt();
		in.close();
		try {
			int maxConsumption = fuse.resolve(switches);
			System.out.println("Fuse was not blown.");
			System.out.println("Maximal consumed current was " + maxConsumption + " A.");
		} catch (FuseBlownException e) {
			System.out.println("Fuse was blown.");
		}
	}

}
