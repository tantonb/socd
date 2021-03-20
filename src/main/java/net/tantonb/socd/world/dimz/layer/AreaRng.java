package net.tantonb.socd.world.dimz.layer;

import it.unimi.dsi.fastutil.longs.Long2IntLinkedOpenHashMap;
import net.minecraft.util.FastRandom;
import net.minecraft.world.gen.IExtendedNoiseRandom;
import net.minecraft.world.gen.ImprovedNoiseGenerator;
import net.minecraft.world.gen.area.LazyArea;
import net.minecraft.world.gen.layer.traits.IPixelTransformer;

import java.util.Random;

public class AreaRng implements IExtendedNoiseRandom<LazyArea> {

    private final Long2IntLinkedOpenHashMap cache;
    private final ImprovedNoiseGenerator noise;
    private final int maxCache;
    private final long seed;
    private long positionSeed;

    public AreaRng(int maxCache, long seed, long modifier){
        this.seed = hash(seed, modifier);
        this.noise = new ImprovedNoiseGenerator(new Random(seed));
        this.cache = new Long2IntLinkedOpenHashMap(16, 0.25F);
        this.cache.defaultReturnValue(Integer.MIN_VALUE);
        this.maxCache = maxCache;
    }

    public AreaRng(int maxCache, long seed) {
        this(maxCache, seed, 1245132512L);
    }

    private static long hash(long left, long right) {
        long lvt_4_1_ = FastRandom.mix(right, right);
        lvt_4_1_ = FastRandom.mix(lvt_4_1_, right);
        lvt_4_1_ = FastRandom.mix(lvt_4_1_, right);
        long lvt_6_1_ = FastRandom.mix(left, lvt_4_1_);
        lvt_6_1_ = FastRandom.mix(lvt_6_1_, lvt_4_1_);
        return FastRandom.mix(lvt_6_1_, lvt_4_1_);
    }

    public LazyArea makeArea(IPixelTransformer pixFormer) {
        return new LazyArea(cache, maxCache, pixFormer);
    }

    public LazyArea makeArea(IPixelTransformer pixFormer, LazyArea area) {
        return new LazyArea(cache, Math.min(1024, area.getmaxCacheSize() * 4), pixFormer);
    }

    public LazyArea makeArea(IPixelTransformer pixFormer, LazyArea area1, LazyArea area2) {
        int maxCacheSize =
                Math.min(1024, Math.max(area1.getmaxCacheSize(), area2.getmaxCacheSize()) * 4);
        return new LazyArea(cache, maxCacheSize, pixFormer);
    }

    public void setPosition(long x, long z) {
        long i = this.seed;
        i = FastRandom.mix(i, x);
        i = FastRandom.mix(i, z);
        i = FastRandom.mix(i, x);
        i = FastRandom.mix(i, z);
        this.positionSeed = i;
    }

    public int random(int bound) {
        int i = (int)Math.floorMod(this.positionSeed >> 24, (long)bound);
        this.positionSeed = FastRandom.mix(this.positionSeed, this.seed);
        return i;
    }

    public ImprovedNoiseGenerator getNoiseGenerator() {
        return this.noise;
    }

    public int roll(int sides) {
        return random(sides) + 1;
    }

    public boolean rollUpTo(int sides, int limit) {
        return roll(sides) <= limit;
    }

    public boolean rollUnder(int sides, int limit) {
        return roll(sides) < limit;
    }

    public boolean rollWithin(int sides, int lower, int upper) {
        int roll = roll(sides);
        return roll >= lower && roll <= upper;
    }

    public boolean rollBetween(int sides, int lower, int upper) {
        int roll = roll(sides);
        return roll > lower && roll < upper;
    }

    public int pickOne(int ...options) {
        return options[random(options.length)];
    }

    public AreaRng modified(long modifier) {
        return new AreaRng(maxCache, seed, modifier);
    }
}
