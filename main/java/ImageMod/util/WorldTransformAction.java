package ImageMod.util;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;

public class WorldTransformAction {

	/*====================================================*/
    /*=======================vars=========================*/
    /*====================================================*/
	
	public TransformCreationData creationData;
	
	public BlockState[][][] previousStructure;
	public BlockState[][][] structure;
	
	public WorldTransformAction(TransformCreationData creationData) {
		creationData.d = Math.max(creationData.d, 1);
		creationData.h = Math.max(creationData.h, 1);
		creationData.w = Math.max(creationData.w, 1);
		
		this.creationData 		= creationData;
		this.structure 			= new BlockState[creationData.w][creationData.h][creationData.d];
		this.previousStructure 	= WorldTransformAction.getCurrentStructure(creationData);
	}
	
	/*====================================================*/
    /*=====================methods========================*/
    /*====================================================*/
	
	/*
	 * Get current blockstates in world at position
	 * */
	public static BlockState[][][] getCurrentStructure(TransformCreationData creationData) {
		
		BlockState[][][] ret = new BlockState[creationData.w][creationData.h][creationData.d];
		
		for (int x = 0; x < creationData.w; x++) {
			for (int y = 0; y < creationData.h; y++) {
				for (int z = 0; z < creationData.d; z++) {
					BlockPos pos = creationData.origin
							.relative(creationData.x, x)
							.relative(creationData.y, y)
							.relative(creationData.z, z);
					ret[x][y][z] = creationData.world.getBlockState(pos);
				}
			}
		}
		
		return ret;
	}
	
	/*
	 * Set blockstate in structure
	 * */
	public void set(int x, int y, int z, BlockState state) {
		this.structure[x][y][z] = state;
	}
	public void set(int x, int y, BlockState state) {
		this.set(x,  y, 0, state);
	}
	
	/*
	 * Place the structure
	 * */
	public void performAction() {
		this.placeStructure(this.structure);
	}
	
	/*
	 * Revert the performed action
	 * */
	public void revertAction() {
		this.placeStructure(this.previousStructure);
	}
	
	/*
	 * Place blocks for structure in world
	 * */
	private void placeStructure(BlockState[][][] structure) {
		
		for (int x = 0; x < this.creationData.w; x++) {
			for (int y = 0; y < this.creationData.w; y++) {
				for (int z = 0; z < this.creationData.w; z++) {
		
					BlockPos pos = this.creationData.origin
							.relative(this.creationData.x, x)
							.relative(this.creationData.y, y)
							.relative(this.creationData.z, z);
					
					this.creationData.world.setBlockAndUpdate(pos, structure[x][y][z]);
				}
			}
		}
	}
	
	
}