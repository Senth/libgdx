package com.badlogic.gdx.math;

import java.io.Serializable;

import com.badlogic.gdx.utils.NumberUtils;

/**
 * Minimalistic Vector4 implementation
 * 
 * @author Matteus Magnusson <matteus.magnusson@spiddekauga.com>
 */
public class Vector4 implements Serializable, Vector<Vector4> {
	private static final long serialVersionUID = 3840568531545372522L;
	
	public float x = 0;
	public float y = 0;
	public float z = 0;
	public float w = 0;
	
	public final static Vector4 X = new Vector4(1, 0, 0, 0);
	public final static Vector4 Y = new Vector4(0, 1, 0, 0);
	public final static Vector4 Z = new Vector4(0, 0, 1, 0);
	public final static Vector4 W = new Vector4(0, 0, 0, 1);
	public final static Vector4 Zero = new Vector4(0, 0, 0, 0);
	
	/** Constructors a vector at (0,0,0,0) */
	public Vector4 () {
		// Does nothing
	}
	
	/** Creates a vector with the given components
	 * @param x The x-component
	 * @param y The y-component
	 * @param z The z-component
	 * @param w The w-component */
	public Vector4(float x, float y, float z, float w) {
		this.set(x, y, z, w);
	}
	
	/** Creates a vector from the given vector
	 * @param vector The vector */
	public Vector4(final Vector4 vector) {
		this.set(vector);
	}
	
	
	@Override
	public Vector4 cpy () {
		return new Vector4(this);
	}

	/** @return Euclidian length */
	public static float len(final float x, final float y, final float z, final float w) {
		return (float)Math.sqrt(x * x + y * y + z * z + w * w);
	}
	
	@Override
	public float len () {
		return len(x, y, z, w);
	}
	
	/** @return Squared euclidian length */
	public static float len2(final float x, final float y, final float z, final float w) {
		return x * x + y * y + z * z + w * w;
	}

	@Override
	public float len2 () {
		return len2(x, y, z, w);
	}

	@Override
	public Vector4 limit (float limit) {
		if (len2() > limit * limit) nor().scl(limit);
		return this;
	}

	@Override
	public Vector4 clamp (float min, float max) {
		final float l2 = len2();
		if (l2 == 0f) return this;
		if (l2 > max * max) return nor().scl(max);
		if (l2 < min * min) return nor().scl(min);
		return this;
	}

	/** Sets the vector to the given components
	 * 
	 * @param x The x-component
	 * @param y The y-component
	 * @param z The z-component
	 * @param w The w-component
	 * @return this vector for chaining */
	public Vector4 set (float x, float y, float z, float w) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
		return this;
	}
	
	@Override
	public Vector4 set (Vector4 v) {
		return this.set(v.x, v.y, v.z, v.w);
	}

	@Override
	public Vector4 sub (Vector4 v) {
		return this.sub(v.x, v.y, v.z, v.w);
	}
	
	/** Subtracts the other vector from this vector.
	 * 
	 * @param x The x-component of the other vectorw
	 * @param y The y-component of the other vector
	 * @param z The z-component of the other vector
	 * @param w The w-component of the other vector
	 * @return This vector for chaining */
	public Vector4 sub (float x, float y, float z, float w) {
		return this.set(this.x - x, this.y - y, this.z - z, this.w - w);
	}

	@Override
	public Vector4 nor () {
		final float len2 = this.len2();
		if (len2 == 0f || len2 == 1f) return this;
		return this.scl(1f / (float)Math.sqrt(len2));
	}
	
	/** Add the other vector to this vector.
	 * 
	 * @param x The x-component of the other vector
	 * @param y The y-component of the other vector
	 * @param z The z-component of the other vector
	 * @param w The w-component of the other vector
	 * @return This vector for chaining */
	public Vector4 add (float x, float y, float z, float w) {
		return this.set(this.x + x, this.y + y, this.z + z, this.w + w);
	}

	@Override
	public Vector4 add (Vector4 v) {
		return this.add(v.x, v.y, v.z, v.w);
	}

	@Override
	public float dot (Vector4 v) {
		return x * v.x + y * v.y + z * v.z + w * v.w;
	}

	@Override
	public Vector4 scl (float scalar) {
		return this.set(x * scalar, y * scalar, z * scalar, w * scalar);
	}

	@Override
	public Vector4 scl (Vector4 v) {
		return this.set(x * v.x, y * v.y, z * v.z, w * v.w);
	}

	@Override
	public float dst (Vector4 v) {
		return len(x - v.x, y - v.y, z - v.z, w - v.w);
	}

	@Override
	public float dst2 (Vector4 v) {
		return len2(x - v.x, y - v.y, z - v.z, w - v.w);
	}

	@Override
	public Vector4 lerp (Vector4 target, float alpha) {
		scl(1 - alpha);
		add(target.x * alpha, target.y * alpha, target.z * alpha, target.w * alpha);
		return this;
	}

	@Override
	public boolean isUnit () {
		return isUnit(0.000000001f);
	}

	@Override
	public boolean isUnit (float margin) {
		return Math.abs(len2() - 1f) < margin;
	}

	@Override
	public boolean isZero () {
		return this.equals(Zero);
	}

	@Override
	public boolean isZero (float margin) {
		return len2() < margin;
	}

	@Override
	public boolean isCollinear (Vector4 vector, float epsilon) {
		return MathUtils.isZero(dot(vector) - 1, epsilon);
	}

	@Override
	public boolean isCollinear (Vector4 vector) {
		return MathUtils.isZero(dot(vector) - 1);
	}

	@Override
	public boolean isCollinearOpposite (Vector4 vector, float epsilon) {
		return MathUtils.isZero(dot(vector) + 1, epsilon);
	}

	@Override
	public boolean isCollinearOpposite (Vector4 vector) {
		return MathUtils.isZero(dot(vector) + 1);
	}

	@Override
	public boolean isPerpendicular (Vector4 vector) {
		return MathUtils.isZero(dot(vector));
	}

	@Override
	public boolean isPerpendicular (Vector4 vector, float epsilon) {
		return MathUtils.isZero(dot(vector), epsilon);
	}

	@Override
	public boolean hasSameDirection (Vector4 vector) {
		return dot(vector) > 0;
	}

	@Override
	public boolean hasOppositeDirection (Vector4 vector) {
		return dot(vector) < 0;
	}

	@Override
	public boolean equals (Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		Vector4 other = (Vector4)obj;
		if (NumberUtils.floatToIntBits(x) != NumberUtils.floatToIntBits(other.x)) return false;
		if (NumberUtils.floatToIntBits(y) != NumberUtils.floatToIntBits(other.y)) return false;
		if (NumberUtils.floatToIntBits(z) != NumberUtils.floatToIntBits(other.z)) return false;
		if (NumberUtils.floatToIntBits(w) != NumberUtils.floatToIntBits(other.w)) return false;
		return true;
	}
	
	@Override
	public boolean epsilonEquals (Vector4 other, float epsilon) {
		if (other == null) return false;
		if (Math.abs(other.x - x) > epsilon) return false;
		if (Math.abs(other.y - y) > epsilon) return false;
		if (Math.abs(other.z - z) > epsilon) return false;
		if (Math.abs(other.w - w) > epsilon) return false;
		return true;
	}

	@Override
	public Vector4 mulAdd (Vector4 v, float scalar) {
		this.x += v.x * scalar;
		this.y += v.y * scalar;
		this.z += v.z * scalar;
		this.w += v.w * scalar;
		return this;
	}

	@Override
	public Vector4 mulAdd (Vector4 v, Vector4 mulVec) {
		this.x += v.x * mulVec.x;
		this.y += v.y * mulVec.y;
		this.z += v.z * mulVec.z;
		this.w += v.w * mulVec.w;
		return this;
	}

	/** Project this point onto a line
	 * @param line line to project onto
	 * @return this for chaining */
	public Vector4 prj(Vector4 line) {
		if (line.equals(Zero)) throw new IllegalArgumentException("Line is a zero vector");
		
		// l * p
		// ----- * l = projection(p)
		// l * l
		float dotUpper = this.dot(line);
		float dotLower = line.dot(line);
		set(line).scl(dotUpper / dotLower);
		return this;
	}
	
	/** Project this point onto a line segment
	 * @param start start of the line segment
	 * @param end end of the line segment
	 * @return this for chaining */
	public Vector4 prj(Vector4 start, Vector4 end) {
		Vector4 line = new Vector4(end).sub(start);
		if (line.len2() == 0) {
			set(start);
			return this;
		}
		
		prj(line);
		
		
		// Clamp
		Vector4 minVec;
		Vector4 maxVec;
		float minValue;
		float maxValue;
		float pointValue;
		if (start.x != end.x) {
			pointValue = x;
			if (start.x < end.x) {
				minVec = start;
				maxVec = end;
			} else {
				minVec = end;
				maxVec = start;
			}
			minValue = minVec.x;
			maxValue = maxVec.x;
		} else if (start.y != end.y) {
			pointValue = y;
			if (start.y < end.y) {
				minVec = start;
				maxVec = end;
			} else {
				minVec = end;
				maxVec = start;
			}
			minValue = minVec.y;
			maxValue = maxVec.y;
		} else if (start.z != end.z) {
			pointValue = z;
			if (start.z < end.z) {
				minVec = start;
				maxVec = end;
			} else {
				minVec = end;
				maxVec = start;
			}
			minValue = minVec.z;
			maxValue = maxVec.z;
		} else {
			pointValue = w;
			if (start.w < end.w) {
				minVec = start;
				maxVec = end;
			} else {
				minVec = end;
				maxVec = start;
			}
			minValue = minVec.w;
			maxValue = maxVec.w;
		}
		
		if (pointValue < minValue) set(minVec);
		else if (pointValue > maxValue) set(maxVec);
		
		return this;
	}
}
