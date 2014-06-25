package mal.core.multiblock;

import org.apache.logging.log4j.Level;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.relauncher.Side;
import mal.carbonization.tileentity.TileEntityMultiblockInit;
import mal.core.api.ITileEntityMultiblock;
import mal.core.tileentity.TileEntityMultiblockMaster;
import mal.core.tileentity.TileEntityMultiblockSlave;
import mal.core.util.MalLogger;
import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

//Used to calculate and create the multiblock
public class MultiBlockInstantiator {
	
	public MultiBlockInstantiator()
	{
	}
	
	/*
	 * Compare a pattern to what there is in the world at a specific offset to shorten calculations
	 */
	public static int[] matchPatternWithOffset(MultiBlockMatcher mbMatch, int startX, int startY, int startZ, World world, Multiblock parentBlock, int[] offset)
	{
		//make sure the offset is a valid one
		if(offset == null || offset.length != 3)
		{
			System.out.println("Failed due to invalid offset");
			return null;
		}
		
		if(offset[0] == -1000 || offset[1] == -1000 || offset[2] == -1000)
			return matchPattern(mbMatch, startX, startY, startZ, world, parentBlock);
		
		Multiblock[][][] pattern = mbMatch.getPattern();
		MultiBlockMatcher test_matcher = new MultiBlockMatcher(pattern.length, pattern[0].length, pattern[0][0].length);
		
		createWorldMultiBlock(test_matcher, startX-offset[0], startY-offset[1], startZ-offset[2], pattern.length, pattern[0].length, pattern[0][0].length, world);
		if(mbMatch.comparePatternWithSubstitutions(test_matcher.getPattern(), parentBlock, 1, startX-offset[0], startY-offset[1], startZ-offset[2], world))
		{
			return offset;//it matches, so return the offset we gave it
		}
		else
		{
			System.out.println("Doing full match");
			return matchPattern(mbMatch, startX, startY, startZ, world, parentBlock);//didn't match, so use the general form instead
		}

	}
	
	/*
	 * Continuously compare our pattern to what is in the world until there is a match and we return the offset
	 */
	public static int[] matchPattern(MultiBlockMatcher mbMatch, int startX, int startY, int startZ, World world, Multiblock parentBlock)
	{
		/**
		 * There are only a limited number of orientations that we will consider valid
		 * Mainly, the command block (starting position) must be on a side
		 * We can also figure out which face based off of where the air is, since we know that a common valid position will have
		 * air on opposite sides
		 * 
		 * this may be able to be made more efficient
		 */
		int[] offset = new int[3];
		Multiblock[][][] pattern = mbMatch.getPattern();
		MultiBlockMatcher test_matcher = new MultiBlockMatcher(pattern.length, pattern[0].length, pattern[0][0].length);
		
		//Figure out if we have an easy calculation
		int sideAxis;
		if(world.isAirBlock(startX+1, startY, startZ) && world.isAirBlock(startX-1, startY, startZ))//x axis alignment
			sideAxis = 0;
		else if(world.isAirBlock(startX, startY+1, startZ) && world.isAirBlock(startX, startY-1, startZ))//y axis alignment
			sideAxis = 1;
		else if(world.isAirBlock(startX, startY, startZ+1) && world.isAirBlock(startX, startY, startZ-1))//z axis alignment
			sideAxis = 2;
		else//we are sad and have to do lots of calculations now :[
			sideAxis = -1;
		
		//System.out.println(sideAxis);
		
		//now we have limited the options to hopefully 2 planes to iterate through
		//We loop through the two axises that are used
		if(sideAxis == 0)//x axis calculations
		{
			offset[0] = 0;
			
			for(int j=0; j<mbMatch.getPattern()[0].length; j++)
				for(int k=0; k<mbMatch.getPattern()[0][0].length; k++)
				{
					createWorldMultiBlock(test_matcher, startX, startY-j, startZ-k, pattern.length, pattern[0].length, pattern[0][0].length, world);
					if(mbMatch.comparePatternWithSubstitutions(test_matcher.getPattern(), parentBlock, 1, startX, startY, startZ, world))
					{
						offset[1] = j;
						offset[2] = k;
						return offset;
					}
					
					createWorldMultiBlock(test_matcher, startX-pattern.length+1, startY-j, startZ-k, pattern.length, pattern[0].length, pattern[0][0].length, world);
					if(mbMatch.comparePatternWithSubstitutions(test_matcher.getPattern(), parentBlock, 1, startX, startY, startZ, world))
					{
						offset[0] = pattern.length-1;
						offset[1] = j;
						offset[2] = k;
						return offset;
					}
				}
		}
		if(sideAxis == 1)//y axis calculations
		{
			offset[0] = 0;
			
			for(int i=0; i<mbMatch.getPattern().length; i++)
				for(int k=0; k<mbMatch.getPattern()[0][0].length; k++)
				{
					createWorldMultiBlock(test_matcher, startX-i, startY, startZ-k, pattern.length, pattern[0].length, pattern[0][0].length, world);
					if(mbMatch.comparePatternWithSubstitutions(test_matcher.getPattern(), parentBlock, 1, startX, startY, startZ, world))
					{
						offset[0] = i;
						offset[1] = 0;
						offset[2] = k;
						return offset;
					}
					
					createWorldMultiBlock(test_matcher, startX-i, startY-pattern[0].length+1, startZ-k, pattern.length, pattern[0].length, pattern[0][0].length, world);
					if(mbMatch.comparePatternWithSubstitutions(test_matcher.getPattern(), parentBlock, 1, startX, startY, startZ, world))
					{
						offset[0] = i;
						offset[1] = pattern[0].length-1;
						offset[2] = k;
						return offset;
					}
				}
		}
		if(sideAxis == 2)//z axis calculations
		{
			offset[0] = 0;
			
			for(int i=0; i<mbMatch.getPattern().length; i++)
				for(int j=0; j<mbMatch.getPattern()[0].length; j++)
				{
					createWorldMultiBlock(test_matcher, startX-i, startY-j, startZ, pattern.length, pattern[0].length, pattern[0][0].length, world);
					if(mbMatch.comparePatternWithSubstitutions(test_matcher.getPattern(), parentBlock, 1, startX, startY, startZ, world))
					{
						offset[0] = i;
						offset[1] = j;
						offset[2] = 0;
						return offset;
					}
					
					createWorldMultiBlock(test_matcher, startX-i, startY-j, startZ-pattern[0][0].length+1, pattern.length, pattern[0].length, pattern[0][0].length, world);
					if(mbMatch.comparePatternWithSubstitutions(test_matcher.getPattern(), parentBlock, 1, startX, startY, startZ, world))
					{
						offset[0] = i;
						offset[1] = j;
						offset[2] = pattern[0][0].length-1;
						return offset;
					}
				}
		}
		/*if(sideAxis == -1)//the dreaded full axis calculations, since there is no information we have to examine every possible position
		{
			for(int i = 1-pattern.length; i<pattern.length; i++)
				for(int j=1-pattern[0].length; j<pattern[0].length; j++)
					for(int k=1-pattern[0][0].length; k<pattern[0][0].length; k++)
					{
						createWorldMultiBlock(test_matcher, startX-i, startY-j, startZ-k, pattern.length, pattern[0].length, pattern[0][0].length, world);
						if(mbMatch.comparePatternWithSubstitutions(test_matcher.getPattern(), parentBlock, 1, forceAir, startX, startY, startZ, world))
						{
							offset[0] = i;
							offset[1] = j;
							offset[2] = k;
							return offset;
						}
					}
					
		}*/
		return null;
	}
	
	/*
	 * Build a multiblock from a successful pattern
	 */
	public static boolean createMultiBlock(MultiBlockMatcher mbMatch, int x, int y, int z, World world, int[] offset, Block masterBlock, Block slaveBlock)
	{
		Multiblock[][][] pattern = mbMatch.getPattern();
		MultiBlockMatcher test_matcher = new MultiBlockMatcher(pattern.length, pattern[0].length, pattern[0][0].length);
		createWorldMultiBlock(test_matcher, x-offset[0], y-offset[1], z-offset[2], pattern.length, pattern[0].length, pattern[0][0].length, world);
		Multiblock[][][] ipattern = test_matcher.getPattern();
		
		if(Side.SERVER == FMLCommonHandler.instance().getEffectiveSide())
		{
			world.setBlock(x, y, z, masterBlock);
			TileEntityMultiblockMaster te = null;
			if(world.getTileEntity(x, y, z) instanceof TileEntityMultiblockMaster)
			{
				te = (TileEntityMultiblockMaster) world.getTileEntity(x, y, z);
				te.properlyActivated = false;
			}
			else
			{
				//something really bad happened, so return
				MalLogger.addLogMessage(Level.FATAL, "Multiblock creation failed HARD!  Report this to Mal and don't try to make more multiblocks please.");
				return false;
			}
	
			double[] eff = new double[2];
			
			for(int i = 0; i<pattern.length; i++) {
				for(int j=0;j<pattern[0].length;j++) {
					for(int k=0;k<pattern[0][0].length;k++)
					{
						if(world.getBlock(x-offset[0]+i, y-offset[1]+j, z-offset[2]+k) == masterBlock)
						{
							//we do this above, so just chill
						}
						else if(pattern[i][j][k].compare(new Multiblock(slaveBlock,pattern[i][j][k].data, pattern[i][j][k].getTier(), false, false), false))
						{
							TileEntity mte = world.getTileEntity(x-offset[0]+i, y-offset[1]+j, z-offset[2]+k);
							if(mte instanceof TileEntityMultiblockSlave)
							{
								((TileEntityMultiblockSlave)mte).InitMultiblock(te);
								eff[0] += ((TileEntityMultiblockSlave)mte).getInsulationTier();
								eff[1] += ((TileEntityMultiblockSlave)mte).getConductionTier();
							}
							else
								MalLogger.addLogMessage("Tile Entity at index: " + i + ", " + j + ", " + k + " not a Multiblock slave block.");
						}
					}
				}
			}
			if(te != null)
			{
				eff[0]=eff[0]/mbMatch.getSize(false);
				eff[1]=eff[1]/mbMatch.getSize(false);
				te.initilize(new Object[]{pattern.length, pattern[0].length, pattern[0][0].length, offset[0], offset[1], offset[2], eff[0], eff[1]});
			}
		}
		return true;
	}
	
	/**
	 * Create a area from a pattern with a certain block for testing
	 */
	public static boolean createTestArea(MultiBlockMatcher mbMatch, int x, int y, int z, World world, int[] offset, Block block)
	{
		Multiblock[][][] pattern = mbMatch.getPattern();
		MultiBlockMatcher test_matcher = new MultiBlockMatcher(pattern.length, pattern[0].length, pattern[0][0].length);
		createWorldMultiBlock(test_matcher, x-offset[0], y-offset[1], z-offset[2], pattern.length, pattern[0].length, pattern[0][0].length, world);
		Multiblock[][][] ipattern = test_matcher.getPattern();
		
		if(Side.SERVER == FMLCommonHandler.instance().getEffectiveSide())
		{	
			for(int i = 0; i<pattern.length; i++) {
				for(int j=0;j<pattern[0].length;j++) {
					for(int k=0;k<pattern[0][0].length;k++)
					{
						world.setBlock(x-offset[0]+i, y-offset[1]+j, z-offset[2]+k, block, 0, 2);
					}
				}
			}
		}
		return true;
	}
	public static boolean createTestArea(MultiBlockMatcher mbMatch, int x, int y, int z, World world, Block block)
	{
		int[] offset = new int[3];
		offset[0]=offset[1]=offset[2]=0;
		return createTestArea(mbMatch,x,y,z,world,offset,block);
	}
	
	public static boolean createSingleBlock(int x, int y, int z, World world, Block block)
	{
		if(Side.SERVER == FMLCommonHandler.instance().getEffectiveSide())
		{
			world.setBlock(x, y, z, block, 0, 2);
			return true;
		}
		
		return false;
	}
	
	/*
	 * revert the multiblocks in an area
	 * masterOverride will remove the control block to bypass an issue of the game not correctly recognizing that the block broke
	 */
	public static boolean revertMultiBlock(int[] offset, World worldObj, int x, int y, int z, int xsize, int ysize, int zsize, boolean masterOverride, Block masterBlock, Block slaveBlock)
	{
		//go through the volume and reset everyone
		//if(Side.SERVER == FMLCommonHandler.instance().getEffectiveSide())
		{
				//System.out.println("Master Revert");
				for(int i = 0; i<xsize;i++)
				{
					for(int j=0; j<ysize; j++)
					{
						for(int k=0; k<zsize; k++)
						{
							TileEntity te = worldObj.getTileEntity(x-offset[0]+i,y-offset[1]+j,z-offset[2]+k);
							int xCoord = x-offset[0]+i;
							int yCoord = y-offset[1]+j;
							int zCoord = z-offset[2]+k;
							
							if(te instanceof ITileEntityMultiblock)
							{
								if(te instanceof TileEntityMultiblockSlave)
								{
									((TileEntityMultiblockSlave) te).setMaster(null);
								}
								else if(te instanceof TileEntityMultiblockMaster)
								{
									//if the control block broke, dump the inventory, otherwise save the inventory
									
									
									if(!worldObj.isAirBlock(xCoord, yCoord, zCoord) && masterOverride == false)//block still exists
									{
										//TODO: Fix backup system
										//System.out.println("making backup nbt");
										//NBTTagCompound nbt = ((TileEntityMultiblockFurnace)te).saveInventory();
										((TileEntityMultiblockMaster)te).dumpInventory();//dump the inventories out
										worldObj.setBlock(xCoord, yCoord, zCoord, masterBlock, 0, 2);
						    			TileEntity tte = worldObj.getTileEntity(xCoord, yCoord, zCoord);
						    			if(tte instanceof TileEntityMultiblockInit)
						    				((TileEntityMultiblockInit) tte).initData(((TileEntityMultiblockMaster) te).getXSize(), ((TileEntityMultiblockMaster) te).getYSize(), ((TileEntityMultiblockMaster) te).getZSize(), offset, ((TileEntityMultiblockMaster)te).getType());
						    			else if (tte instanceof TileEntityMultiblockSlave)
						    				System.out.println("wat...");
						    			else
						    			{
						    				//System.out.println("dafuq...");
						    				worldObj.setTileEntity(xCoord, yCoord, zCoord, masterBlock.createTileEntity(worldObj, 0));
							    			tte = worldObj.getTileEntity(xCoord, yCoord, zCoord);
						    				((TileEntityMultiblockInit) tte).initData(((TileEntityMultiblockMaster) te).getXSize(), ((TileEntityMultiblockMaster) te).getYSize(), ((TileEntityMultiblockMaster) te).getZSize(), offset, ((TileEntityMultiblockMaster)te).getType());
						    			}
									}
								}
								
							}
							else
							{
								//System.out.println("This isn't our tile entity!");
								//return;
							}
						}
					}
				}
		}
		return true;
	}
	
	/*
	 * Revert a block at a location, used to clean up broken multiblocks
	 */
	public static void revertSingleMultiblock(World worldObj, int x, int y, int z)
	{
		if(Side.SERVER == FMLCommonHandler.instance().getEffectiveSide())
		{
			TileEntity te = worldObj.getTileEntity(x,y,z);
			
			if(te instanceof ITileEntityMultiblock)
			{
				if(te instanceof TileEntityMultiblockSlave)
				{
					if(worldObj.getBlock(x, y, z) != null)
					{
						((TileEntityMultiblockSlave) te).setMaster(null);
					}
				}
				else
				{
					//System.out.println("This isn't a dummy entity!");
					return;
				}
			}
			else
			{
				//System.out.println("This isn't our tile entity!");
				return;
			}
			te = null;
		}
	}
	
	/*
	 * Determine the two efficiency values of a multiblock
	 */
	public static int[] addValue(int value, boolean type, int[] data)
	{
		if(type)//side and top
		{
			data[0] += value;
		}
		else
		{
			data[1] += value;
		}
		return data;
	}
	
	/*
	 * Build a multiBlockMatcher following the blocks in a volume
	 */
	public static boolean createWorldMultiBlock(MultiBlockMatcher mbMatch, int startX, int startY, int startZ, int xSize, int ySize, int zSize, World world)
	{
		//System.out.println("Starting Values: " + startX +", "+ startY+", "+ startZ +", " + xSize + ", " + ySize + ", " + zSize);
		
		for(int i=0;i<mbMatch.getPattern().length;i++)
			for(int j=0; j<mbMatch.getPattern()[0].length;j++)
				for(int k=0; k<mbMatch.getPattern()[0][0].length;k++)
				{
					TileEntity te = world.getTileEntity(i+startX, j+startY, k+startZ);
					TileEntityMultiblockSlave ste = null;
					if(te instanceof TileEntityMultiblockSlave)
						ste = (TileEntityMultiblockSlave) te;
					
					//if(!world.isAirBlock(i+startX, j+startY, k+startZ))
					{
						boolean succ = mbMatch.setBlock(i, j, k, world.getBlock(i+startX, j+startY, k+startZ), (ste != null)?ste.getData():world.getBlockMetadata(i+startX, j+startY, k+startZ), (ste == null));
						if(succ==false)
						{
							MalLogger.addLogMessage(Level.WARN, "Detect process failed at indices: "+i+", "+j+", "+k+": Previous process failed.");
							return succ;
						}
					}
				}
		return true;
	}
}

/*******************************************************************************
* Copyright (c) 2014 Malorolam.
* 
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the included license, which is also
* available at http://carbonization.wikispaces.com/License
* 
*********************************************************************************/