package evilcraft.entities.monster;

import java.util.Random;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingData;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import evilcraft.api.Helpers;
import evilcraft.api.config.ElementType;
import evilcraft.api.config.ExtendedConfig;
import evilcraft.api.config.configurable.Configurable;
import evilcraft.entities.villager.WerewolfVillagerConfig;
import evilcraft.items.WerewolfBoneConfig;
import evilcraft.items.WerewolfFurConfig;

public class Werewolf extends EntityMob implements Configurable{
    
    protected ExtendedConfig eConfig = null;
    
    public static ElementType TYPE = ElementType.MOB;
    
    private NBTTagCompound villagerNBTTagCompound = new NBTTagCompound();
    private boolean fromVillager = false;
    
    private static int BARKCHANCE = 1000;
    private static int BARKLENGTH = 40;
    private static int barkprogress = -1;

    // Set a configuration for this entity
    public void setConfig(ExtendedConfig eConfig) {
        this.eConfig = eConfig;
    }

    public Werewolf(World world) {
        super(world);
        
        this.getNavigator().setAvoidsWater(true);
        this.setSize(0.6F, 2.9F);
        this.stepHeight = 1.0F;
        this.isImmuneToFire = false;
        
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIWander(this, 1.0F));
        this.tasks.addTask(2, new EntityAILookIdle(this));
        this.tasks.addTask(4, new EntityAIAttackOnCollide(this, EntityPlayer.class, 1.0D, true));

        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, false));
    
        // This sets the default villager profession ID.
        this.villagerNBTTagCompound.setInteger("Profession", WerewolfVillagerConfig._instance.ID);
    }
    
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setAttribute(40.0D);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setAttribute(2.0D);
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setAttribute(7.0D);
    }
    
    public void writeEntityToNBT(NBTTagCompound NBTTagCompound) {
        super.writeEntityToNBT(NBTTagCompound);
        NBTTagCompound.setCompoundTag("villager", villagerNBTTagCompound);
        NBTTagCompound.setBoolean("fromVillager", fromVillager);
    }

    public void readEntityFromNBT(NBTTagCompound NBTTagCompound) {
        super.readEntityFromNBT(NBTTagCompound);
        this.villagerNBTTagCompound = NBTTagCompound.getCompoundTag("villager");
        this.fromVillager = NBTTagCompound.getBoolean("fromVillager");
    }
    
    public static boolean isWerewolfTime(World world) {
        return WerewolfConfig._instance.isEnabled()
                && world.getCurrentMoonPhaseFactor() == 1.0
                && !Helpers.isDay(world)
                && world.difficultySetting != 0;
    }
    
    private static void replaceEntity(EntityLiving old, EntityLiving neww, World world) {
        // TODO: A nice update effect?
        // Maybe something like this: https://github.com/iChun/Morph/blob/master/morph/client/model/ModelMorphAcquisition.java
        neww.copyLocationAndAnglesFrom(old);
        world.removeEntity(old);
        neww.onSpawnWithEgg((EntityLivingData)null);

        world.spawnEntityInWorld(neww);
        world.playAuxSFXAtEntity((EntityPlayer)null, 1016, (int)old.posX, (int)old.posY, (int)old.posZ, 0);
    }
    
    public void replaceWithVillager() {
        EntityVillager villager = new EntityVillager(this.worldObj, WerewolfVillagerConfig._instance.ID);
        replaceEntity(this, villager, this.worldObj);
        villager.readEntityFromNBT(villagerNBTTagCompound);
    }
    
    public static void replaceVillager(EntityVillager villager) {
        Werewolf werewolf = new Werewolf(villager.worldObj);
        villager.writeEntityToNBT(werewolf.getVillagerNBTTagCompound());
        werewolf.setFromVillager(true);
        replaceEntity(villager, werewolf, villager.worldObj);
    }
    
    @Override
    public void onLivingUpdate() {        
        if(!worldObj.isRemote && (!isWerewolfTime(worldObj) || worldObj.difficultySetting == 0)) {
            replaceWithVillager();
        } else {
            super.onLivingUpdate();
        }
        
        // Random barking
        Random random = worldObj.rand;
        if(random.nextInt(BARKCHANCE) == 0 && barkprogress == -1) {
            barkprogress++;
        } else if(barkprogress > -1) {
            playSound("mob.wolf.growl", 0.15F, 1.0F);
            barkprogress++;
            if(barkprogress > BARKLENGTH) {
                barkprogress = -1;
            }
        }
    }
    
    public float getBarkProgressScaled(float scale) {
        if(barkprogress == -1)
            return 0;
        else
            return (float)barkprogress / (float)BARKLENGTH * scale;
    }
    
    @Override
    protected int getDropItemId() {
        if(WerewolfBoneConfig._instance.isEnabled())
            return WerewolfBoneConfig._instance.ID;
        else
            return super.getDropItemId();
    }
    
    protected void dropRareDrop(int par1) {
        if(WerewolfFurConfig._instance.isEnabled())
            this.dropItem(WerewolfFurConfig._instance.ID, 1);
    }
    
    @Override
    protected String getLivingSound() {
        return "mob.wolf.growl";
    }

    @Override
    protected String getHurtSound() {
        return "mob.wolf.hurt";
    }

    @Override
    protected String getDeathSound() {
        return "mob.wolf.death";
    }
    
    @Override
    protected void playStepSound(int par1, int par2, int par3, int par4) {
        this.playSound("mob.zombie.step", 0.15F, 1.0F);
    }
    
    @Override
    public EnumCreatureAttribute getCreatureAttribute() {
        return EnumCreatureAttribute.ARTHROPOD;
    }
    
    @Override
    protected boolean canDespawn() {
        return !isFromVillager();
    }
    
    public NBTTagCompound getVillagerNBTTagCompound() {
        return villagerNBTTagCompound;
    }
    
    public boolean isFromVillager() {
        return fromVillager;
    }
    
    public void setFromVillager(boolean fromVillager) {
        this.fromVillager = fromVillager;
    }

    @Override
    public String getUniqueName() {
        return "entity."+eConfig.NAMEDID+".name";
    }

    @Override
    public boolean isEntity() {
        return true;
    }

}