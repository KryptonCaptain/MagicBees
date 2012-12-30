package thaumicbees.bees.genetics;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import thaumicbees.bees.EffectData;
import forestry.api.apiculture.IAlleleBeeEffect;
import forestry.api.apiculture.IBeeGenome;
import forestry.api.apiculture.IBeeHousing;
import forestry.api.genetics.AlleleManager;
import forestry.api.genetics.IEffectData;

public class AlleleEffectPotion extends AlleleEffect
{
	private int potionId;
	private int duration;
	private String beealyzerId;
	
	public AlleleEffectPotion(String name, String readableName, Potion potionApplied, int effectDuration, boolean isDominant)
	{
		super("effect" + name, isDominant);
		this.beealyzerId = readableName;
		this.potionId = potionApplied.id;
		this.duration = 20 * effectDuration;
	}

	@Override
	public IEffectData validateStorage(IEffectData storedData)
	{
		if (storedData == null)
		{
			storedData = new EffectData(1, 0, 0);
		}
		return storedData;
	}

	@Override
	public String getIdentifier()
	{
		return this.beealyzerId;
	}

	@Override
	public IEffectData doEffect(IBeeGenome genome, IEffectData storedData, IBeeHousing housing)
	{
		int count = storedData.getInteger(0);
		
		if (count == 200)
		{
			// Get the size of the affected area
			int[] area = genome.getTerritory();
			
			// Calculate offset
			int[] min = new int[3];
			int[] max = new int[3];
			min[0] = housing.getXCoord() - area[0] / 2;
			max[0] = housing.getXCoord() + area[0] / 2;
			
			min[1] = housing.getYCoord() - area[1] / 2;
			max[1] = housing.getYCoord() + area[1] / 2;
			
			min[2] = housing.getZCoord() - area[2] / 2;
			max[2] = housing.getZCoord() + area[2] / 2;
			
			AxisAlignedBB bounds = AxisAlignedBB.getAABBPool().addOrModifyAABBInPool(min[0], min[1], min[2], max[0], max[1], max[2]);
			List<Entity> entityList = housing.getWorld().getEntitiesWithinAABB(EntityPlayer.class, bounds);
			
			for (Entity e : entityList)
			{
				if (e instanceof EntityPlayer)
				{
					EntityPlayer player = (EntityPlayer)e;
					player.addPotionEffect(new PotionEffect(this.potionId, this.duration, 0));
				}
			}
			storedData.setInteger(0, 0);
		}
		else
		{
			storedData.setInteger(0, count + 1);
		}
		
		return storedData;
	}

	@Override
	public String getIconTextureFile()
	{
		return null;
	}

	@Override
	public int getIconIndex()
	{
		return -1;
	}

}