package com.davi.demo.service;

import com.davi.demo.model.AdjacencyResult;
import com.davi.demo.model.ContainmentResult;
import com.davi.demo.model.IntersectionResult;
import com.davi.demo.model.Point;
import com.davi.demo.model.Rectangle;
import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Service
public class RectangleService {


	/**
	 * If there is an Intersection, but also adjacent, then return empty
	 */
	public Optional<IntersectionResult> calculateIntersection(final Rectangle rectangle1, final Rectangle rectangle2) {
		validateRectangles(rectangle1, rectangle2);

		double left1 = rectangle1.getX();
		double right1 = left1 + rectangle1.getWidth();
		double bottom1 = rectangle1.getY();
		double top1 = bottom1 + rectangle1.getHeight();

		double left2 = rectangle2.getX();
		double right2 = left2 + rectangle2.getWidth();
		double bottom2 = rectangle2.getY();
		double top2 = bottom2 + rectangle2.getHeight();

		if(top1 == top2 || bottom1 == bottom2 || left1 == left2 || right1 == right2) {
			return Optional.empty();
		}

		boolean top1IntersectRectangle2 = isBetween(top1, bottom2, top2);
		boolean left1IntersectRectangle2 = isBetween(left1, left2, right2);
		boolean bottom1IntersectRectangle2 = isBetween(bottom1, bottom2, top2);
		boolean right1IntersectRectangle2 = isBetween(right1, left2, right2);

		boolean leftAndRight1GreaterThanRectangle2 = (left1 < left2 && right1 > right2);
		boolean topAndBottom1GreaterThanRectangle2 = (top1 > top2 && bottom1 < bottom2);


		boolean topRightIntersect = top1IntersectRectangle2 && right1IntersectRectangle2;
		boolean topLeftIntersect = top1IntersectRectangle2 && left1IntersectRectangle2;
		boolean bottomRightIntersect = bottom1IntersectRectangle2 && right1IntersectRectangle2;
		boolean bottomLeftIntersect = bottom1IntersectRectangle2 && left1IntersectRectangle2;

		boolean topOnlyIntersect = top1IntersectRectangle2 && leftAndRight1GreaterThanRectangle2;
		boolean bottomOnlyIntersect = bottom1IntersectRectangle2 && leftAndRight1GreaterThanRectangle2;
		boolean leftOnlyIntersect = left1IntersectRectangle2 && topAndBottom1GreaterThanRectangle2;
		boolean rightOnlyIntersect = right1IntersectRectangle2 && topAndBottom1GreaterThanRectangle2;

		Optional<IntersectionResult> intersectionResultOpt = Optional.empty();

		if(topRightIntersect) {
			intersectionResultOpt = Optional.of(createIntersectionResult(left2, top1, right1, bottom2));
		}

		if(topLeftIntersect) {
			intersectionResultOpt = Optional.of(createIntersectionResult(left1, bottom2, right2, top1));
		}

		if(bottomRightIntersect) {
			intersectionResultOpt = Optional.of(createIntersectionResult(left2, bottom1, right1, top2));
		}

		if(bottomLeftIntersect) {
			intersectionResultOpt = Optional.of(createIntersectionResult(left1, top2, right2, bottom1));
		}

		if(topOnlyIntersect) {
			intersectionResultOpt = Optional.of(createIntersectionResult(left2, top1, right2, top1));
		}

		if(bottomOnlyIntersect) {
			intersectionResultOpt = Optional.of(createIntersectionResult(left2, bottom1, right2, bottom1));
		}

		if(leftOnlyIntersect) {
			intersectionResultOpt = Optional.of(createIntersectionResult(left1, top2, left1, bottom2));
		}

		if(rightOnlyIntersect) {
			intersectionResultOpt = Optional.of(createIntersectionResult(right1, top2, right1, bottom2));
		}

		return intersectionResultOpt;
	}

	private IntersectionResult createIntersectionResult(final Double x1, final Double y1, final Double x2, final Double y2) {
		val p1 = new Point(x1, y1);
		val p2 = new Point(x2, y2);
		return new IntersectionResult(p1, p2);
	}

	/**
	 * If rectangle A equals rectangle B, then containment is false
	 */
	public ContainmentResult calculateContainment(final Rectangle rectangle1, final Rectangle rectangle2) {
		validateRectangles(rectangle1, rectangle2);

		double left1 = rectangle1.getX();
		double right1 = left1 + rectangle1.getWidth();
		double bottom1 = rectangle1.getY();
		double top1 = bottom1 + rectangle1.getHeight();

		double left2 = rectangle2.getX();
		double right2 = left2 + rectangle2.getWidth();
		double bottom2 = rectangle2.getY();
		double top2 = bottom2 + rectangle2.getHeight();

		if(top1 == top2 && bottom1 == bottom2 && left1 == left2 && right1 == right2) {
			return new ContainmentResult(false);
		}

		boolean firstRectangleContainSecond = (top1 >= top2 && bottom1 <= bottom2 && left1 <= left2 && right1 >= right2);
		boolean secondRectangleContainFirst = (top2 >= top1 && bottom2 <= bottom1 && left2 <= left1 && right2 >= right1);

		boolean isContainment = firstRectangleContainSecond || secondRectangleContainFirst;

		return new ContainmentResult(isContainment);
	}

	/**
	 * If rectangle A contains B, then adjacency is false
	 */
	public AdjacencyResult calculateAdjacency(final Rectangle rectangle1, final Rectangle rectangle2) {
		validateRectangles(rectangle1, rectangle2);

		double left1 = rectangle1.getX();
		double right1 = left1 + rectangle1.getWidth();
		double bottom1 = rectangle1.getY();
		double top1 = bottom1 + rectangle1.getHeight();

		double left2 = rectangle2.getX();
		double right2 = left2 + rectangle2.getWidth();
		double bottom2 = rectangle2.getY();
		double top2 = bottom2 + rectangle2.getHeight();

		boolean horizontalMatch = top1 == bottom2 || bottom1 == top2;
		boolean verticalIntersect = (isBetween(left1, left2, right2) || isBetween(right1, left2, right2));
		boolean sameVertical = left1 == left2 && right1 == right2;

		boolean verticalMatch = left1 == right2 || right1 == left2;
		boolean horizontalIntersect = isBetween(top1, bottom2, top2) || isBetween(bottom1, bottom2, top2);
		boolean sameHorizontal = top1 == top2 && bottom1 == bottom2;

		boolean isAdjacent = (horizontalMatch && verticalIntersect)
				|| (verticalMatch && horizontalIntersect)
				|| (horizontalMatch && sameVertical)
				|| (verticalMatch && sameHorizontal);

		return new AdjacencyResult(isAdjacent);
	}

	private boolean isBetween(final Double value, final Double min, final Double max) {
		return value > min && value < max;
	}

	/**
	 * Validate if Rectangle have greater than 0 height and width
	 */
	protected void validateRectangles(final Rectangle rectangle1, final Rectangle rectangle2) {
		if(rectangle1.getHeight() <= 0.0 || rectangle1.getWidth() <= 0.0
				|| rectangle2.getHeight() <= 0.0 || rectangle2.getWidth() <= 0.0) {
			throw new ResponseStatusException(BAD_REQUEST);
		}
	}
}
