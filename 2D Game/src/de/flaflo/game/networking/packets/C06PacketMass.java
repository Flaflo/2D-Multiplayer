package de.flaflo.game.networking.packets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class C06PacketMass extends Packet {

	public C06PacketMass(int mass) {
		super((byte) 6);
	}

	@Override
	public void send(DataOutputStream out) throws IOException {
		
	}

	@Override
	public void receive(DataInputStream in) throws IOException {
	}

}
