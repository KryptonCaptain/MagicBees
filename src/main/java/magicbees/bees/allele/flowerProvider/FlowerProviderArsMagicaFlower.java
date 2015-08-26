package magicbees.bees.allele.flowerProvider;

import magicbees.main.Config;
import magicbees.main.utils.LocalizationManager;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import forestry.api.genetics.IIndividual;
import forestry.api.genetics.IPollinatable;

public class FlowerProviderArsMagicaFlower extends FlowerProvider {
	
	public FlowerProviderArsMagicaFlower() {
		super(5);
		flowers.add(new FlowerImpl(Config.amBlackOrchid, 0, 1, true));
		flowers.add(new FlowerImpl(Config.amDesertNova, 0, 1, true));
		flowers.add(new FlowerImpl(Config.amAum, 0, 1, true));
		flowers.add(new FlowerImpl(Config.amWakebloom, 0, 1, true));
		flowers.add(new FlowerImpl(Config.amTarmaRoot, 0, 1, true));
	}

	@Override
	public boolean growFlower(World world, IIndividual genome, int x, int y, int z) {
		boolean flag = false;
		Block blockDown = world.getBlock(x, y - 1, z);
		if (world.getBlock(x, y, z).isAir(world, x, y, z)) {
			if (blockDown == Blocks.dirt || blockDown == Blocks.grass) {
				int dart = world.rand.nextInt(100);
				Block block;
				if (dart > 60) {
					block = Config.amBlackOrchid;
				} else if (dart > 30) {
					block = Config.amAum;
				} else {
					block = Config.amTarmaRoot;
				}
				world.setBlock(x, y, z, block);
				flag = true;
			} else if (blockDown == Blocks.sand) {
				world.setBlock(x, y, z, Config.amDesertNova);
				flag = true;
			} else if (blockDown == Blocks.stone) {
				world.setBlock(x, y, z, Config.amTarmaRoot);
				flag = true;
			} else if (blockDown == Blocks.water || blockDown == Blocks.water) {
				world.setBlock(x, y, z, Config.amWakebloom);
				flag = true;
			}
		}
		return flag;
	}

	@Override
	public String getDescription() {
		return LocalizationManager.getLocalizedString("flowerProvider.arsmagica");
	}

	@Override
	public ItemStack[] affectProducts(World world, IIndividual genome, int x, int y, int z, ItemStack[] products) {
		return products;
	}

	@Override
	public boolean isAcceptedPollinatable(World world, IPollinatable pollinatable) {
		return pollinatable.getPlantType().size() > 1;
	}

}
