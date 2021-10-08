package com.turtle.api.util;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import com.turtle.api.vec2d;

public class VertexUtils {

	public static vec2d getMinVertex(List<vec2d> vertices) {

		AtomicReference<vec2d> min = new AtomicReference<>(vertices.get(0));

		vertices.forEach(e -> {
			if (e.getX() == min.get().getX()) {
				if (e.getY() < min.get().getY()) {
					min.set(e);
				}
			}
			if (e.getX() < min.get().getX() && e.getY() == min.get().getY()) {
				min.set(e);
			}

			if (e.getX() < min.get().getX() && e.getY() < min.get().getY()) {
				min.set(e);
			}

		});
		return min.get();
	}

	public static vec2d getMaxVertex(List<vec2d> vertices) {
			AtomicReference<vec2d> max = new AtomicReference<>(vertices.get(vertices.size() - 1));

			vertices.forEach(e -> {
				if (e.getX() == max.get().getX()) {
					if (e.getY() > max.get().getY()) {
						max.set(e);
					}
				}
				if (e.getX() > max.get().getX() && e.getY() == max.get().getY()) {
					max.set(e);
				}
				if (e.getX() > max.get().getX() && e.getY() > max.get().getY()) {
					max.set(e);
				}
			});
			return max.get();
	}

}
