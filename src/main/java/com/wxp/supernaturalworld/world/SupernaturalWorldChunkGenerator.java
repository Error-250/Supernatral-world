package com.wxp.supernaturalworld.world;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.IChunkGenerator;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/** @author wxp */
public class SupernaturalWorldChunkGenerator implements IChunkGenerator {
  private World world;
  private Random random;
  private Biome[] biomesForGeneration;

  public SupernaturalWorldChunkGenerator(World worldIn, long seed) {
    this.world = worldIn;
    this.random = new Random(seed);
    biomesForGeneration = new Biome[16 * 16];
    for (int i = 0; i < 16; i++) {
      for (int j = 0; j < 16; j++) {
        biomesForGeneration[i * j] = Biomes.FOREST;
      }
    }
  }

  @Override
  public Chunk generateChunk(int x, int z) {
    // 地形生成
    ChunkPrimer chunkprimer = new ChunkPrimer();

    for (int i = 0; i < 256; ++i) {
      IBlockState iblockstate = null;
      if (i == 0) {
        iblockstate = Blocks.BEDROCK.getDefaultState();
      } else if (i < 3) {
        iblockstate = Blocks.STONE.getDefaultState();
      } else if (i == 3) {
        iblockstate = Blocks.GRASS.getDefaultState();
      }

      if (iblockstate != null) {
        for (int j = 0; j < 16; ++j) {
          for (int k = 0; k < 16; ++k) {
            chunkprimer.setBlockState(j, i, k, iblockstate);
          }
        }
      }
    }

    Chunk chunk = new Chunk(this.world, chunkprimer, x, z);

    byte[] abyte = chunk.getBiomeArray();

    for (int l = 0; l < abyte.length; ++l) {
      abyte[l] = (byte) Biome.getIdForBiome(getBiomeFromWorld(x, z));
    }

    chunk.generateSkylightMap();
    return chunk;
  }

  @Override
  public void populate(int x, int z) {
    // 填充生物群系, 生物, 矿洞等
    Biome biome = getBiomeFromWorld(x, z);
    if (!Biomes.PLAINS.equals(biome)) {
      biome.decorate(this.world, random, new BlockPos(x * 16, 0, z * 16));
    }
  }

  @Override
  public boolean generateStructures(Chunk chunkIn, int x, int z) {
    return false;
  }

  @Override
  public List<Biome.SpawnListEntry> getPossibleCreatures(
      EnumCreatureType creatureType, BlockPos pos) {
//        if (EnumCreatureType.MONSTER.equals(creatureType)) {
//          return Collections.emptyList();
//        }
    return Biomes.FOREST.getSpawnableList(creatureType);
  }

  @Nullable
  @Override
  public BlockPos getNearestStructurePos(
      World worldIn, String structureName, BlockPos position, boolean findUnexplored) {
    return null;
  }

  @Override
  public void recreateStructures(Chunk chunkIn, int x, int z) {}

  @Override
  public boolean isInsideStructure(World worldIn, String structureName, BlockPos pos) {
    return false;
  }

  private Biome getBiomeFromWorld(int x, int z) {
    final int biomeWidth = 256;
    if (x >= 0 && x <= 256 && z >= 0 && z <= 256) {
      return Biomes.PLAINS;
    } else {
      return Biomes.FOREST;
    }
  }
}
