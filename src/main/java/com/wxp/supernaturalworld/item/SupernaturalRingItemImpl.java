package com.wxp.supernaturalworld.item;

import com.wxp.supernaturalworld.capability.BindingEntityI;
import com.wxp.supernaturalworld.domain.SupernaturalLevel;
import com.wxp.supernaturalworld.domain.SupernaturalRingInfo;
import com.wxp.supernaturalworld.manager.CapabilityManager;
import com.wxp.supernaturalworld.world.SupernaturalWordData;
import com.wxp.supernaturalworld.world.SupernaturalWorldProvider;
import com.wxp.supernaturalworld.world.SupernaturalWorldTeleporter;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;

/** @author wxp */
public class SupernaturalRingItemImpl extends Item implements SupernaturalRingItemI {
  public static ItemStack tenYearAttackRing;
  private static ItemStack hundredYearAttackRing;
  private static ItemStack thousandYearAttackRing;
  private static ItemStack tenThousandYearAttackRing;
  private static ItemStack hundredThousandYearAttackRing;
  private static ItemStack tenYearDefenceRing;
  private static ItemStack hundredYearDefenceRing;
  private static ItemStack thousandYearDefenceRing;
  private static ItemStack tenThousandYearDefenceRing;
  private static ItemStack hundredThousandYearDefenceRing;
  private static String name = "supernatural_ring";

  public SupernaturalRingItemImpl() {
    setRegistryName(name);
    setHasSubtypes(true);
  }

  @Override
  public String getUnlocalizedName(ItemStack stack) {
    SupernaturalRingInfo supernaturalRingInfo = getRingInfo(stack);
    return String.format("item.%s_%s", name, supernaturalRingInfo.getSupernaturalLevel().name());
  }

  private static String generateSkillDescription(RingSkill ringSkill) {
    StringBuilder stringBuilder = new StringBuilder("\n");
    TextComponentTranslation typeText =
        new TextComponentTranslation(
            "text.supernatural_ring.skill.type", ringSkill.getRingType().getName());
    stringBuilder.append(String.format("    %s\n", typeText.getUnformattedText()));
    if (ringSkill.getAttack() != null) {
      TextComponentTranslation attackText =
          new TextComponentTranslation(
              "text.supernatural_ring.skill.attack",
              "\u00a7a+" + ringSkill.getAttack() + "\u00a77");
      stringBuilder.append(String.format("    %s\n", attackText.getUnformattedText()));
    }
    if (ringSkill.getAttackDoubleRate() != null) {
      TextComponentTranslation attackDoubleText =
          new TextComponentTranslation(
              "text.supernatural_ring.skill.attack_double",
              "\u00a7a*" + ringSkill.getAttackDoubleRate() + "\u00a77");
      stringBuilder.append(String.format("    %s\n", attackDoubleText.getUnformattedText()));
    }
    if (ringSkill.getDefence() != null) {
      TextComponentTranslation defenceText =
          new TextComponentTranslation(
              "text.supernatural_ring.skill.defence",
              "\u00a7a+" + ringSkill.getDefence() + "\u00a77");
      stringBuilder.append(String.format("    %s\n", defenceText.getUnformattedText()));
    }
    if (ringSkill.getDefenceDoubleRate() != null) {
      TextComponentTranslation defenceDoubleText =
          new TextComponentTranslation(
              "text.supernatural_ring.skill.defence_double",
              "\u00a7a*" + ringSkill.getDefenceDoubleRate() + "\u00a77");
      stringBuilder.append(String.format("    %s\n", defenceDoubleText.getUnformattedText()));
    }
    return stringBuilder.toString();
  }

  public static RingSkill getRingSkill(ItemStack stack) {
    RingSkill ringSkill = new RingSkill();
    if (stack.getMetadata() == tenYearAttackRing.getMetadata()) {
      ringSkill.setSkillType(RingSkill.SkillType.ATTACK_UP);
      ringSkill.setAttack(3f);
      ringSkill.setRingType(RingType.ATTACK);
      ringSkill.setSkillDesc(generateSkillDescription(ringSkill));
    } else if (stack.getMetadata() == hundredYearAttackRing.getMetadata()) {
      ringSkill.setSkillType(RingSkill.SkillType.ATTACK_UP);
      ringSkill.setAttack(5f);
      ringSkill.setRingType(RingType.ATTACK);
      ringSkill.setSkillDesc(generateSkillDescription(ringSkill));
    } else if (stack.getMetadata() == thousandYearAttackRing.getMetadata()) {
      ringSkill.setSkillType(RingSkill.SkillType.ATTACK_DOUBLE);
      ringSkill.setAttackDoubleRate(1.5f);
      ringSkill.setRingType(RingType.ATTACK);
      ringSkill.setSkillDesc(generateSkillDescription(ringSkill));
    } else if (stack.getMetadata() == tenThousandYearAttackRing.getMetadata()) {
      ringSkill.setSkillType(RingSkill.SkillType.ATTACK_DOUBLE);
      ringSkill.setAttackDoubleRate(2f);
      ringSkill.setRingType(RingType.ATTACK);
      ringSkill.setSkillDesc(generateSkillDescription(ringSkill));
    } else if (stack.getMetadata() == hundredThousandYearAttackRing.getMetadata()) {
      ringSkill.setSkillType(RingSkill.SkillType.ATTACK_UP_AND_DOUBLE);
      ringSkill.setAttack(10f);
      ringSkill.setAttackDoubleRate(2.5f);
      ringSkill.setRingType(RingType.ATTACK);
      ringSkill.setSkillDesc(generateSkillDescription(ringSkill));
    } else if (stack.getMetadata() == tenYearDefenceRing.getMetadata()) {
      ringSkill.setSkillType(RingSkill.SkillType.DEFENCE_UP);
      ringSkill.setDefence(1.5f);
      ringSkill.setRingType(RingType.DEFENCE);
      ringSkill.setSkillDesc(generateSkillDescription(ringSkill));
    } else if (stack.getMetadata() == hundredYearDefenceRing.getMetadata()) {
      ringSkill.setSkillType(RingSkill.SkillType.DEFENCE_UP);
      ringSkill.setDefence(2.5f);
      ringSkill.setRingType(RingType.DEFENCE);
      ringSkill.setSkillDesc(generateSkillDescription(ringSkill));
    } else if (stack.getMetadata() == thousandYearDefenceRing.getMetadata()) {
      ringSkill.setSkillType(RingSkill.SkillType.DEFENCE_DOUBLE);
      ringSkill.setDefenceDoubleRate(2f);
      ringSkill.setRingType(RingType.DEFENCE);
      ringSkill.setSkillDesc(generateSkillDescription(ringSkill));
    } else if (stack.getMetadata() == tenThousandYearDefenceRing.getMetadata()) {
      ringSkill.setSkillType(RingSkill.SkillType.DEFENCE_DOUBLE);
      ringSkill.setDefenceDoubleRate(2.5f);
      ringSkill.setRingType(RingType.DEFENCE);
      ringSkill.setSkillDesc(generateSkillDescription(ringSkill));
    } else if (stack.getMetadata() == hundredThousandYearDefenceRing.getMetadata()) {
      ringSkill.setSkillType(RingSkill.SkillType.DEFENCE_UP_AND_DOUBLE);
      ringSkill.setDefence(8f);
      ringSkill.setDefenceDoubleRate(3f);
      ringSkill.setRingType(RingType.DEFENCE);
      ringSkill.setSkillDesc(generateSkillDescription(ringSkill));
    }
    return ringSkill;
  }

  @Override
  public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
    if (this.isInCreativeTab(tab)) {
      initRingItem();

      items.add(tenYearAttackRing);
      items.add(hundredYearAttackRing);

      items.add(tenYearDefenceRing);
      items.add(hundredYearDefenceRing);

      items.add(thousandYearAttackRing);
      items.add(tenThousandYearAttackRing);

      items.add(thousandYearDefenceRing);
      items.add(tenThousandYearDefenceRing);

      items.add(hundredThousandYearAttackRing);
      items.add(hundredThousandYearDefenceRing);
    }
  }

  @Override
  public EnumRarity getRarity(ItemStack stack) {
    SupernaturalRingInfo supernaturalRingInfo = getRingInfo(stack);
    switch (supernaturalRingInfo.getSupernaturalLevel()) {
      case THOUSAND:
      case TEN_THOUSAND:
        return EnumRarity.RARE;
      case HUNDRED_THOUSAND:
        return EnumRarity.EPIC;
      case TEN:
      case HUNDRED:
      default:
        return EnumRarity.COMMON;
    }
  }

  @Override
  public void addInformation(
      ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
    super.addInformation(stack, worldIn, tooltip, flagIn);
    SupernaturalRingInfo supernaturalRingInfo = getRingInfo(stack);
    TextComponentTranslation rarityDesc =
        new TextComponentTranslation(
            "text.supernatural_ring.rarity." + getRarity(stack).rarityName);
    TextComponentTranslation rarityText =
        new TextComponentTranslation(
            "text.supernatural_ring.rarity",
            getRarity(stack).rarityColor + rarityDesc.getUnformattedText());
    TextComponentTranslation yearsText =
        new TextComponentTranslation(
            "text.supernatural_ring.years", supernaturalRingInfo.getYear());
    BindingEntityI bindingEntityI =
        stack.getCapability(CapabilityManager.bindingEntityICapability, null);
    boolean isBind = bindingEntityI != null && bindingEntityI.getBindingPlayerUuid() != null;
    TextComponentTranslation bindText =
        new TextComponentTranslation("text.supernatural_ring.bind", isBind);
    TextComponentTranslation descText =
        new TextComponentTranslation(
            "text.supernatural_ring.skill.desc", getRingSkill(stack).getSkillDesc());
    tooltip.add(rarityText.getFormattedText());
    tooltip.add(yearsText.getFormattedText());
    tooltip.add(bindText.getFormattedText());
    tooltip.addAll(Arrays.asList(descText.getUnformattedText().split("\n")));
  }

  @Override
  public EnumActionResult onItemUse(
      EntityPlayer player,
      World worldIn,
      BlockPos pos,
      EnumHand hand,
      EnumFacing facing,
      float hitX,
      float hitY,
      float hitZ) {
    if (!worldIn.isRemote) {
      int destination = DimensionType.OVERWORLD.getId();
      if (worldIn.provider.getDimensionType().getId()
          != SupernaturalWorldProvider.supernaturalWorldType.getId()) {
        destination = SupernaturalWorldProvider.supernaturalWorldType.getId();
      }
      SupernaturalWordData currentWorldData = SupernaturalWordData.getInstance(worldIn);
      currentWorldData.setLastPosition(player.getPositionVector());
      WorldServer worldServer = worldIn.getMinecraftServer().getWorld(destination);
      SupernaturalWorldTeleporter teleporter = new SupernaturalWorldTeleporter(worldServer);
      player.changeDimension(destination, teleporter);
      SupernaturalWordData supernaturalWordData = SupernaturalWordData.getInstance(worldServer);
      if (supernaturalWordData.getLastPosition() != null) {
        player.setPositionAndUpdate(
            supernaturalWordData.getLastPosition().x,
            supernaturalWordData.getLastPosition().y,
            supernaturalWordData.getLastPosition().z);
      } else {
        BlockPos blockPos = worldServer.provider.getRandomizedSpawnPoint();
        player.setPositionAndUpdate(blockPos.getX(), blockPos.getY(), blockPos.getZ());
      }
    }
    return super.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
  }

  public static SupernaturalRingInfo getRingInfo(ItemStack itemStack) {
    if (!(itemStack.getItem() instanceof SupernaturalRingItemImpl)) {
      return null;
    }
    SupernaturalRingInfo supernaturalRingInfo = new SupernaturalRingInfo();
    int meta = itemStack.getMetadata();
    if (meta == 0) {
      itemStack.setItemDamage(tenYearAttackRing.getMetadata());
      meta = tenYearAttackRing.getMetadata();
    }
    SupernaturalLevel supernaturalLevel = SupernaturalLevel.valueOf(meta / 1000 - 1);
    supernaturalRingInfo.setSupernaturalLevel(supernaturalLevel);
    RingSkill.SkillType skillType = RingSkill.SkillType.valueOfNumber(meta / 10 % 100);
    supernaturalRingInfo.setSkillType(skillType);
    int base;
    switch (supernaturalLevel) {
      case TEN:
        base = 10;
        break;
      case HUNDRED:
        base = 100;
        break;
      case THOUSAND:
        base = 1000;
        break;
      case TEN_THOUSAND:
        base = 10000;
        break;
      case HUNDRED_THOUSAND:
        base = 100000;
        break;
      default:
        base = 1;
    }
    supernaturalRingInfo.setBaseYear(meta % 10);
    supernaturalRingInfo.setYear(base * supernaturalRingInfo.getBaseYear());
    return supernaturalRingInfo;
  }

  private void initRingItem() {
    if (tenYearAttackRing == null) {
      tenYearAttackRing = new ItemStack(this, 1, 1001);
    }
    if (hundredYearAttackRing == null) {
      hundredYearAttackRing = new ItemStack(this, 1, 2001);
    }
    if (thousandYearAttackRing == null) {
      thousandYearAttackRing = new ItemStack(this, 1, 3021);
    }
    if (tenThousandYearAttackRing == null) {
      tenThousandYearAttackRing = new ItemStack(this, 1, 4021);
    }
    if (hundredThousandYearAttackRing == null) {
      hundredThousandYearAttackRing = new ItemStack(this, 1, 5041);
    }
    if (tenYearDefenceRing == null) {
      tenYearDefenceRing = new ItemStack(this, 1, 1011);
    }
    if (hundredYearDefenceRing == null) {
      hundredYearDefenceRing = new ItemStack(this, 1, 2011);
    }
    if (thousandYearDefenceRing == null) {
      thousandYearDefenceRing = new ItemStack(this, 1, 3031);
    }
    if (tenThousandYearDefenceRing == null) {
      tenThousandYearDefenceRing = new ItemStack(this, 1, 4031);
    }
    if (hundredThousandYearDefenceRing == null) {
      hundredThousandYearDefenceRing = new ItemStack(this, 1, 5051);
    }
  }
}
