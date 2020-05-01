package com.wxp.supernaturalworld.world;

import lombok.Data;
import net.minecraft.init.Biomes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeProvider;
import net.minecraft.world.gen.IChunkGenerator;

import java.util.ArrayList;
import java.util.List;

/** @author wxp */
public class SupernaturalWorldProvider extends WorldProvider {
  public static DimensionType supernaturalWorldType =
      DimensionType.register(
          "supernatural", "_supernatural_world", 2, SupernaturalWorldProvider.class, false);
  private static List<BiomeChunk> supportBiomes;

  static {
    supportBiomes = new ArrayList<>();

    BiomeChunk forestChunk = new BiomeChunk();
    forestChunk.setMinX(0);
    forestChunk.setMaxX(256);
    forestChunk.setMinZ(0);
    forestChunk.setMaxZ(256);
    forestChunk.setBiome(Biomes.FOREST);
    supportBiomes.add(forestChunk);
  }

  @Override
  public DimensionType getDimensionType() {
    return supernaturalWorldType;
  }

  @Override
  public int getDimension() {
    return supernaturalWorldType.getId();
  }

  @Override
  public void setDimension(int dim) {
    super.setDimension(supernaturalWorldType.getId());
  }

  @Override
  public IChunkGenerator createChunkGenerator() {
    return new SupernaturalWorldChunkGenerator(this.world, this.getSeed());
  }

  @Override
  public BiomeProvider getBiomeProvider() {
    return super.getBiomeProvider();
  }

  @Override
  public BlockPos getRandomizedSpawnPoint() {
    int randomX = this.world.rand.nextInt(256);
    int randomZ = this.world.rand.nextInt(256);
    return new BlockPos(randomX, 4, randomZ);
  }

  @Override
  public boolean isSurfaceWorld() {
    return true;
  }

  @Data
  public static class BiomeChunk {
    private int minX;
    private int maxX;
    private int minZ;
    private int maxZ;
    private Biome biome;
  }
}
