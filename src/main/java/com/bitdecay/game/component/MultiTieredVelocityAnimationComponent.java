package com.bitdecay.game.component;

/**
 * Speed up an animation based on its velocity
 */
public class MultiTieredVelocityAnimationComponent extends AbstractComponent {

    public float speedScale = 0;
    public float speedToggle = 0;
    public AnimatedImageComponent slow;
    public AnimatedImageComponent fast;

    public MultiTieredVelocityAnimationComponent(float speedScale, float speedToggle, AnimatedImageComponent slow, AnimatedImageComponent fast){
        this.speedScale = speedScale;
        this.speedToggle = speedToggle;
        this.slow = slow;
        this.fast = fast;
    }

    public MultiTieredVelocityAnimationComponent slow() {
        fast.isVisible = false;
        slow.isVisible = true;
        return this;
    }

    public MultiTieredVelocityAnimationComponent fast() {
        fast.isVisible = true;
        slow.isVisible = false;
        return this;
    }
}
