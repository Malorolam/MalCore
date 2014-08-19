package mal.core.api;

public interface ITieredItem {

	public double[] getTier(int damage);
	
	public double getInsulationTier();
	public double getConductionTier();

	int getX();

	int getY();

	int getZ();
}
/*******************************************************************************
* Copyright (c) 2014 Malorolam.
* 
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the included license, which is also
* available at http://carbonization.wikispaces.com/License
* 
*********************************************************************************/