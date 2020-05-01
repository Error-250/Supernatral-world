package com.wxp.supernaturalworld.world;

import com.wxp.supernaturalworld.config.SupernaturalConfig;
import lombok.Getter;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StringUtils;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.storage.MapStorage;
import net.minecraft.world.storage.WorldSavedData;

/** @author wxp */
@Getter
public class SupernaturalWordData extends WorldSavedData {
  private Vec3d lastPosition;

  public SupernaturalWordData() {
    super(SupernaturalConfig.MOD_ID + "_supernatural_word_data");
  }

  @Override
  public void readFromNBT(NBTTagCompound nbt) {
    String x = nbt.getString("last_position_x");
    String y = nbt.getString("last_position_y");
    String z = nbt.getString("last_position_z");
    if (StringUtils.isNullOrEmpty(x)
        || StringUtils.isNullOrEmpty(y)
        || StringUtils.isNullOrEmpty(z)) {
      this.lastPosition = null;
    } else {
      this.lastPosition =
          new Vec3d(Double.parseDouble(x), Double.parseDouble(y), Double.parseDouble(z));
    }
  }

  @Override
  public NBTTagCompound writeToNBT(NBTTagCompound compound) {
    compound.setString("last_position_x", String.valueOf(this.lastPosition.x));
    compound.setString("last_position_y", String.valueOf(this.lastPosition.y));
    compound.setString("last_position_z", String.valueOf(this.lastPosition.z));
    return compound;
  }

  public void setLastPosition(Vec3d lastPosition) {
    this.lastPosition = lastPosition;
    markDirty();
  }

  public static SupernaturalWordData getInstance(World world) {
    MapStorage storage = world.getPerWorldStorage();
    SupernaturalWordData savedData =
        (SupernaturalWordData)
            storage.getOrLoadData(SupernaturalWordData.class, "supernatural_word_data");
    if (savedData == null) {
      savedData = new SupernaturalWordData();
      storage.setData("supernatural_word_data", savedData);
    }
    return savedData;
  }
}
