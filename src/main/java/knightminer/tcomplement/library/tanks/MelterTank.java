package knightminer.tcomplement.library.tanks;

import knightminer.tcomplement.common.TCompNetwork;
import knightminer.tcomplement.melter.network.FluidUpdatePacket;
import knightminer.tcomplement.melter.tileentity.TileMelter;
import slimeknights.tconstruct.library.fluid.FluidTankAnimated;

public class MelterTank extends FluidTankAnimated {

	private TileMelter parent;
	public MelterTank(int capacity, TileMelter parent) {
		super(capacity, parent);
		this.parent = parent;
	}

	@Override
	protected void sendUpdate(int amount) {
		if(amount != 0) {
			renderOffset += amount;
			// packet is sent on all changes as the server side often adds fluids
		}
	}

	@Override
	protected void onContentsChanged() {
		super.onContentsChanged();
		if(parent.isServerWorld()) {
			TCompNetwork.sendToAll(new FluidUpdatePacket(parent.getPos(), this.getFluid()));
		}
	}
}
