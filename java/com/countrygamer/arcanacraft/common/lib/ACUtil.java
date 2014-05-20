package com.countrygamer.arcanacraft.common.lib;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class ACUtil {
	
	public static int[] getNewCoordsFromSide(int x, int y, int z, int side) {
		int x1 = x, y1 = y, z1 = z;
		if (side == 0) {
			--y1;
		}
		if (side == 1) {
			++y1;
		}
		
		if (side == 2) {
			--z1;
		}
		
		if (side == 3) {
			++z1;
		}
		
		if (side == 4) {
			--x1;
		}
		
		if (side == 5) {
			++x1;
		}
		
		return new int[] {
				x1, y1, z1
		};
	}
	
	public static MovingObjectPosition getMOPFromPlayer(World world,
			EntityPlayer player, double reachLength) {
		float f = 1.0F;
		float f1 = player.prevRotationPitch
				+ (player.rotationPitch - player.prevRotationPitch) * f;
		float f2 = player.prevRotationYaw
				+ (player.rotationYaw - player.prevRotationYaw) * f;
		double d0 = player.prevPosX + (player.posX - player.prevPosX) * (double) f;
		double d1 = player.prevPosY + (player.posY - player.prevPosY) * (double) f
				+ 1.62D - (double) player.yOffset;
		double d2 = player.prevPosZ + (player.posZ - player.prevPosZ) * (double) f;
		Vec3 vec3 = world.getWorldVec3Pool().getVecFromPool(d0, d1, d2);
		float f3 = MathHelper.cos(-f2 * 0.017453292F - (float) Math.PI);
		float f4 = MathHelper.sin(-f2 * 0.017453292F - (float) Math.PI);
		float f5 = -MathHelper.cos(-f1 * 0.017453292F);
		float f6 = MathHelper.sin(-f1 * 0.017453292F);
		float f7 = f4 * f5;
		float f8 = f3 * f5;
		double d3 = reachLength < 0 ? 200.0D : reachLength;
		Vec3 vec31 = vec3.addVector((double) f7 * d3, (double) f6 * d3, (double) f8
				* d3);
		MovingObjectPosition movingobjectposition = world.rayTraceBlocks(vec3,
				vec31, true);
		return movingobjectposition;
	}
	
	public static MovingObjectPositionTarget getBlockFromCursor(World world,
			EntityPlayer player, double reachLength) {
		MovingObjectPosition mop = ACUtil.getMOPFromPlayer(world, player,
				reachLength);
		if (mop == null) return null;
		
		int blockX, blockY, blockZ, side;
		if (mop.typeOfHit == MovingObjectType.BLOCK) {
			int[] newCoords = ACUtil.getNewCoordsFromSide(mop.blockX, mop.blockY,
					mop.blockZ, mop.sideHit);
			blockX = newCoords[0];
			blockY = newCoords[1];
			blockZ = newCoords[2];
			side = mop.sideHit;
		}
		else {
			blockX = (int) mop.hitVec.xCoord;
			blockY = (int) mop.hitVec.yCoord;
			blockZ = (int) mop.hitVec.zCoord;
			side = 1;
		}
		
		return new ACUtil.MovingObjectPositionTarget(blockX, blockY, blockZ, side);
	}
	
	public static class MovingObjectPositionTarget {
		
		public final int x, y, z, side;
		
		public MovingObjectPositionTarget(int x, int y, int z, int side) {
			this.x = x;
			this.y = y;
			this.z = z;
			this.side = side;
		}
		
	}
	
}
