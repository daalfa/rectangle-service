package com.davi.demo.service;


import com.davi.demo.model.Point;
import com.davi.demo.model.Rectangle;
import lombok.val;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RectangleServiceTest {

    private RectangleService rectangleService;

    @Before
    public void setUp() {
        this.rectangleService = new RectangleService();
    }

    @Test
    public void shouldReturnIsContained() {
        val r1 = new Rectangle(0.0, 0.0, 4.0, 4.0);
        val r2 = new Rectangle(1.0, 0.5, 1.0, 1.0);

        val result = rectangleService.calculateContainment(r1, r2);

        assertTrue(result.getIsContained());
    }

    @Test
    public void shouldReturnIsContained_inverted() {
        val r2 = new Rectangle(0.0, 0.0, 4.0, 4.0);
        val r1 = new Rectangle(1.0, 0.5, 1.0, 1.0);

        val result = rectangleService.calculateContainment(r1, r2);

        assertTrue(result.getIsContained());
    }

    @Test
    public void shouldReturnIsNotContained() {
        val r1 = new Rectangle(0.0, 0.0, 1.0, 1.0);
        val r2 = new Rectangle(2.0, 0.0, 1.0, 1.0);

        val result = rectangleService.calculateContainment(r1, r2);

        assertFalse(result.getIsContained());
    }

    @Test
    public void shouldReturnIsNotContained_equalRectangles() {
        val r1 = new Rectangle(0.0, 0.0, 1.0, 1.0);
        val r2 = new Rectangle(0.0, 0.0, 1.0, 1.0);

        val result = rectangleService.calculateContainment(r1, r2);

        assertFalse(result.getIsContained());
    }

    @Test
    public void shouldReturnIsAdjacent() {
        val r1 = new Rectangle(0.0, 0.0, 1.0, 1.0);
        val r2 = new Rectangle(1.0, 0.0, 1.0, 1.0);

        val result = rectangleService.calculateAdjacency(r1, r2);

        assertTrue(result.getIsAdjacent());
    }

    @Test
    public void shouldReturnIsAdjacent_inverted() {
        val r2 = new Rectangle(0.0, 0.0, 1.0, 1.0);
        val r1 = new Rectangle(1.0, 0.0, 1.0, 1.0);

        val result = rectangleService.calculateAdjacency(r1, r2);

        assertTrue(result.getIsAdjacent());
    }

    @Test
    public void shouldReturnIsAdjacent_partial_inverted() {
        val r2 = new Rectangle(0.0, 0.0, 1.0, 1.0);
        val r1 = new Rectangle(1.0, 0.5, 1.0, 1.0);

        val result = rectangleService.calculateAdjacency(r1, r2);

        assertTrue(result.getIsAdjacent());
    }

    @Test
    public void shouldReturnIsNotAdjacent() {
        val r1 = new Rectangle(0.0, 0.0, 1.0, 1.0);
        val r2 = new Rectangle(2.0, 0.0, 1.0, 1.0);

        val result = rectangleService.calculateAdjacency(r1, r2);

        assertFalse(result.getIsAdjacent());
    }

    @Test
    public void shouldReturnIsNotAdjacent_corner() {
        val r1 = new Rectangle(0.0, 0.0, 1.0, 1.0);
        val r2 = new Rectangle(1.0, 1.0, 1.0, 1.0);

        val result = rectangleService.calculateAdjacency(r1, r2);

        assertFalse(result.getIsAdjacent());
    }

    @Test
    public void shouldReturnIsNotAdjacent_containment() {
        val r1 = new Rectangle(0.0, 0.0, 4.0, 4.0);
        val r2 = new Rectangle(0.0, 0.0, 1.0, 4.0);

        val result = rectangleService.calculateAdjacency(r1, r2);

        assertFalse(result.getIsAdjacent());
    }

    @Test
    public void shouldReturnIsNotAdjacent_intersection() {
        val r1 = new Rectangle(0.0, 0.0, 1.0, 1.0);
        val r2 = new Rectangle(0.5, 0.5, 0.5, 1.0);

        val result = rectangleService.calculateAdjacency(r1, r2);

        assertFalse(result.getIsAdjacent());
    }

    @Test
    public void shouldReturnIntersection_topRight() {
        val r1 = new Rectangle(0.5, 0.5, 1.0, 1.0);
        val r2 = new Rectangle(1.0, 1.0, 1.0, 1.0);

        val resultOpt = rectangleService.calculateIntersection(r1, r2);

        assertTrue(resultOpt.isPresent());
        val point1 = resultOpt.get().getPoint1();
        val point2 = resultOpt.get().getPoint2();

        val expected1 = new Point(1.0, 1.5);
        val expected2 = new Point(1.5, 1.0);

        assertEquals(expected1, point1);
        assertEquals(expected2, point2);
    }

    @Test
    public void shouldReturnIntersection_bottomRight() {
        val r1 = new Rectangle(0.5, 0.5, 1.0, 1.0);
        val r2 = new Rectangle(1.0, 0.0, 1.0, 1.0);

        val resultOpt = rectangleService.calculateIntersection(r1, r2);

        assertTrue(resultOpt.isPresent());
        val point1 = resultOpt.get().getPoint1();
        val point2 = resultOpt.get().getPoint2();

        val expected1 = new Point(1.0, 0.5);
        val expected2 = new Point(1.5, 1.0);

        assertEquals(expected1, point1);
        assertEquals(expected2, point2);
    }

    @Test
    public void shouldReturnIntersection_topLeft() {
        val r1 = new Rectangle(0.5, 0.5, 1.0, 1.0);
        val r2 = new Rectangle(0.0, 1.0, 1.0, 1.0);

        val resultOpt = rectangleService.calculateIntersection(r1, r2);

        assertTrue(resultOpt.isPresent());
        val point1 = resultOpt.get().getPoint1();
        val point2 = resultOpt.get().getPoint2();

        val expected1 = new Point(0.5, 1.0);
        val expected2 = new Point(1.0, 1.5);

        assertEquals(expected1, point1);
        assertEquals(expected2, point2);
    }

    @Test
    public void shouldReturnIntersection_bottomLeft() {
        val r1 = new Rectangle(0.5, 0.5, 1.0, 1.0);
        val r2 = new Rectangle(0.0, 0.0, 1.0, 1.0);

        val resultOpt = rectangleService.calculateIntersection(r1, r2);

        assertTrue(resultOpt.isPresent());
        val point1 = resultOpt.get().getPoint1();
        val point2 = resultOpt.get().getPoint2();

        val expected1 = new Point(0.5, 1.0);
        val expected2 = new Point(1.0, 0.5);

        assertEquals(expected1, point1);
        assertEquals(expected2, point2);
    }

    @Test
    public void shouldReturnIntersection_topOnly() {
        val r1 = new Rectangle(0.0, 0.0, 1.5, 1.5);
        val r2 = new Rectangle(0.5, 1.0, 1.0, 0.5);

        val resultOpt = rectangleService.calculateIntersection(r1, r2);

        assertTrue(resultOpt.isPresent());
        val point1 = resultOpt.get().getPoint1();
        val point2 = resultOpt.get().getPoint2();

        val expected1 = new Point(0.5, 1.5);
        val expected2 = new Point(1.0, 1.5);

        assertEquals(expected1, point1);
        assertEquals(expected2, point2);
    }

    @Test
    public void shouldReturnIntersection_rightOnly() {
        val r1 = new Rectangle(0.0, 0.0, 1.5, 1.5);
        val r2 = new Rectangle(1.0, 0.5, 0.5, 1.0);

        val resultOpt = rectangleService.calculateIntersection(r1, r2);

        assertTrue(resultOpt.isPresent());
        val point1 = resultOpt.get().getPoint1();
        val point2 = resultOpt.get().getPoint2();

        val expected1 = new Point(1.5, 1.0);
        val expected2 = new Point(1.5, 0.5);

        assertEquals(expected1, point1);
        assertEquals(expected2, point2);
    }

    @Test
    public void shouldReturnIntersection_bottomOnly() {
        val r1 = new Rectangle(0.0, 0.0, 1.5, 1.5);
        val r2 = new Rectangle(0.5, -0.5, 1.0, 0.5);

        val resultOpt = rectangleService.calculateIntersection(r1, r2);

        assertTrue(resultOpt.isPresent());
        val point1 = resultOpt.get().getPoint1();
        val point2 = resultOpt.get().getPoint2();

        val expected1 = new Point(0.5, 0.0);
        val expected2 = new Point(1.0, 0.0);

        assertEquals(expected1, point1);
        assertEquals(expected2, point2);
    }

    @Test
    public void shouldReturnIntersection_leftOnly() {
        val r1 = new Rectangle(0.0, 0.0, 1.5, 1.5);
        val r2 = new Rectangle(-0.5, 0.5, 0.5, 1.0);

        val resultOpt = rectangleService.calculateIntersection(r1, r2);

        assertTrue(resultOpt.isPresent());
        val point1 = resultOpt.get().getPoint1();
        val point2 = resultOpt.get().getPoint2();

        val expected1 = new Point(0.0, 1.0);
        val expected2 = new Point(0.0, 0.5);

        assertEquals(expected1, point1);
        assertEquals(expected2, point2);
    }

    @Test
    public void shouldNotReturnIntersection() {
        val r1 = new Rectangle(0.0, 0.0, 1.0, 1.0);
        val r2 = new Rectangle(1.0, 0.0, 1.0, 1.0);

        val resultOpt = rectangleService.calculateIntersection(r1, r2);

        assertTrue(resultOpt.isEmpty());
    }

    @Test
    public void shouldNotReturnIntersection_corner() {
        val r1 = new Rectangle(0.0, 0.0, 1.0, 1.0);
        val r2 = new Rectangle(1.0, 1.0, 1.0, 1.0);

        val resultOpt = rectangleService.calculateIntersection(r1, r2);

        assertTrue(resultOpt.isEmpty());
    }

    @Test
    public void shouldNotReturnIntersection_adjacent() {
        val r1 = new Rectangle(0.0, 0.0, 1.0, 1.0);
        val r2 = new Rectangle(0.5, 0.5, 0.5, 1.0);

        val resultOpt = rectangleService.calculateIntersection(r1, r2);

        assertTrue(resultOpt.isEmpty());
    }

    @Test
    public void shouldValidateRectangle_negative() {
        val r1 = new Rectangle(0.0, 0.0, -1.0, 1.0);
        val r2 = new Rectangle(0.0, 0.0, 1.0, 1.0);

        val exception = assertThrows(ResponseStatusException.class,
                () -> rectangleService.validateRectangles(r1, r2));

        assertEquals(BAD_REQUEST, exception.getStatus());
    }

    @Test
    public void shouldValidateRectangle_zero() {
        val r1 = new Rectangle(0.0, 0.0, 1.0, 1.0);
        val r2 = new Rectangle(0.0, 0.0, 1.0, 0.0);

        val exception = assertThrows(ResponseStatusException.class,
                () -> rectangleService.validateRectangles(r1, r2));

        assertEquals(BAD_REQUEST, exception.getStatus());
    }
}