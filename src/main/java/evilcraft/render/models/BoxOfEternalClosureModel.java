// Date: 12/07/2014 11:42:39
// Template version 1.1
// Java generated by Techne
// Keep in mind that you still need to fill in some blanks
// - ZeuX

package evilcraft.render.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

/**
 * TODO: put at 0,0,0
 * @author Davivs69
 */
public class BoxOfEternalClosureModel extends ModelBase {
	
	private float xOffset = 0F;
	private float yOffset = 0F;
	private float zOffset = 0F;
	
	ModelRenderer rightTrapBody;
	ModelRenderer leftTrapBody;
	ModelRenderer frontTrapBody;
	ModelRenderer backTrapBody;
	ModelRenderer bottomTrapBody;
	ModelRenderer rightFlap;
	ModelRenderer leftFlap;
	ModelRenderer rightFrontFoot;
	ModelRenderer leftFrontFoot;
	ModelRenderer rightBackFoot;
	ModelRenderer leftBackFoot;

	/**
	 * Make a new instance.
	 */
	public BoxOfEternalClosureModel() {
		textureWidth = 256;
		textureHeight = 64;

		rightTrapBody = new ModelRenderer(this, 0, 0);
		addBox(rightTrapBody, -5F, -2.5F, -8F, 1, 7, 16);
		rightTrapBody.setRotationPoint(0F, 0F, 0F);
		rightTrapBody.setTextureSize(64, 32);
		rightTrapBody.mirror = true;
		setRotation(rightTrapBody, 0F, 0F, 0F);
		leftTrapBody = new ModelRenderer(this, 36, 0);
		addBox(leftTrapBody, 4F, -2.5F, -8F, 1, 7, 16);
		leftTrapBody.setRotationPoint(0F, 0F, 0F);
		leftTrapBody.setTextureSize(64, 32);
		leftTrapBody.mirror = true;
		setRotation(leftTrapBody, 0F, 0F, 0F);
		frontTrapBody = new ModelRenderer(this, 0, 24);
		addBox(frontTrapBody, -4F, -1.5F, -8F, 8, 6, 1);
		frontTrapBody.setRotationPoint(0F, 0F, 0F);
		frontTrapBody.setTextureSize(64, 32);
		frontTrapBody.mirror = true;
		setRotation(frontTrapBody, 0F, 0F, 0F);
		backTrapBody = new ModelRenderer(this, 21, 24);
		addBox(backTrapBody, -4F, -1.5F, 7F, 8, 6, 1);
		backTrapBody.setRotationPoint(0F, 0F, 0F);
		backTrapBody.setTextureSize(64, 32);
		backTrapBody.mirror = true;
		setRotation(backTrapBody, 0F, 0F, 0F);
		bottomTrapBody = new ModelRenderer(this, 72, 0);
		addBox(bottomTrapBody, -4F, 3.5F, -7F, 8, 1, 14);
		bottomTrapBody.setRotationPoint(0F, 0F, 0F);
		bottomTrapBody.setTextureSize(64, 32);
		bottomTrapBody.mirror = true;
		setRotation(bottomTrapBody, 0F, 0F, 0F);
		rightFlap = new ModelRenderer(this, 72, 18);
		addBox(rightFlap, 0F, 0F, -8F, 4, 1, 16);
		rightFlap.setRotationPoint(-4F, -2.5F, 0F);
		rightFlap.setTextureSize(64, 32);
		rightFlap.mirror = true;
		setRotation(rightFlap, 0F, 0F, -2.094395F);
		leftFlap = new ModelRenderer(this, 114, 18);
		addBox(leftFlap, -4F, 0F, -8F, 4, 1, 16);
		leftFlap.setRotationPoint(4F, -2.5F, 0F);
		leftFlap.setTextureSize(64, 32);
		leftFlap.mirror = true;
		setRotation(leftFlap, 0F, 0F, 2.094395F);
		rightFrontFoot = new ModelRenderer(this, 0, 32);
		addBox(rightFrontFoot, -5F, 4.5F, -8F, 3, 1, 3);
		rightFrontFoot.setRotationPoint(0F, 0F, 0F);
		rightFrontFoot.setTextureSize(64, 32);
		rightFrontFoot.mirror = true;
		setRotation(rightFrontFoot, 0F, 0F, 0F);
		leftFrontFoot = new ModelRenderer(this, 13, 32);
		addBox(leftFrontFoot, 2F, 4.5F, -8F, 3, 1, 3);
		leftFrontFoot.setRotationPoint(0F, 0F, 0F);
		leftFrontFoot.setTextureSize(64, 32);
		leftFrontFoot.mirror = true;
		setRotation(leftFrontFoot, 0F, 0F, 0F);
		rightBackFoot = new ModelRenderer(this, 26, 32);
		addBox(rightBackFoot, -5F, 4.5F, 5F, 3, 1, 3);
		rightBackFoot.setRotationPoint(0F, 0F, 0F);
		rightBackFoot.setTextureSize(64, 32);
		rightBackFoot.mirror = true;
		setRotation(rightBackFoot, 0F, 0F, 0F);
		leftBackFoot = new ModelRenderer(this, 39, 32);
		addBox(leftBackFoot, 2F, 4.5F, 5F, 3, 1, 3);
		leftBackFoot.setRotationPoint(0F, 0F, 0F);
		leftBackFoot.setTextureSize(64, 32);
		leftBackFoot.mirror = true;
		setRotation(leftBackFoot, 0F, 0F, 0F);
	}
	
	private void addBox(ModelRenderer model, float x, float y, float z, int xSize, int ySize, int zSize) {
		model.addBox(x + xOffset, y + yOffset, z + zOffset, xSize, ySize, zSize);
	}

	private void setRotation(ModelRenderer model, float x, float y, float z) {
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}

	@Override
	public void setRotationAngles(float moveTime, float moveSpeed, float ticksExisted, float lookYaw, float lookPitch, float scale, Entity entity) {
		super.setRotationAngles(moveTime, moveSpeed, ticksExisted, lookYaw, lookPitch, scale, entity);
	}
	
	/**
     * This method renders out all parts of the box model.
     */
    public void renderAll() {
        this.rightTrapBody.render(0.0625F);
        this.leftTrapBody.render(0.0625F);
        this.frontTrapBody.render(0.0625F);
        this.backTrapBody.render(0.0625F);
        this.bottomTrapBody.render(0.0625F);
        this.rightFlap.render(0.0625F);
        this.leftFlap.render(0.0625F);
        this.rightFrontFoot.render(0.0625F);
        this.leftFrontFoot.render(0.0625F);
        this.rightBackFoot.render(0.0625F);
        this.leftBackFoot.render(0.0625F);
    }

}