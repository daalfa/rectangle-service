# Rectangles

## Problem Description:
You are required to write code in the language of your choice implementing certain algorithms that
analyze rectangles and features that exist among rectangles. Your implementation is required to cover
the following:
1. Intersection: You must be able to determine whether two rectangles have one or more
   intersecting lines and produce a result identifying the points of intersection. For your
   convenience, the scenario is diagrammed in Appendix 1.
2. Containment: You must be able to determine whether a rectangle is wholly contained within
   another rectangle. For your convenience, the scenario is diagrammed in Appendix 2.
3. Adjacency: Implement the ability to detect whether two rectangles are adjacent. Adjacency is
   defined as the sharing of at least one side. Side sharing may be proper, sub-line or partial. A
   sub-line share is a share where one side of rectangle A is a line that exists as a set of points
   wholly contained on some other side of rectangle B, where partial is one where some line
   segment on a side of rectangle A exists as a set of points on some side of Rectangle B. For your
   convenience, these scenarios are diagrammed in Appendix 3.

## Your Submission Must Include:
1. An implementation of the rectangle entity as well as implementations for the algorithms that
   define the operations listed above.
2. Appropriate documentation
3. Test cases/unit tests


   Feel free to expand on this problem as you wish. Document any expansion and provide it as part of your
   submission.
   Your submitted source code must compile (if necessary) and the resulting executable must run on Linux.
   Please document any library or framework dependencies
   
## Usage

* Post `/api/rectangle/intersection` to return points of intersection
* Post `/api/rectangle/containment` to return if rectangle contains another rectangle
* Post `/api/rectangle/adjacency` to return if rectangle is adjacent to another rectangle
* [http://localhost:8080/swagger-ui/]() to test the API

All Post requests use the same request body:
```json
{
  "rectangle1": {
    "height": 0,
    "width": 0,
    "x": 0,
    "y": 0
  },
  "rectangle2": {
    "height": 0,
    "width": 0,
    "x": 0,
    "y": 0
  }
}
```


### `/intersection` endpoint

Intersection api will not calculate if the rectangles are adjacent even if one side is intersected.

Example of success response with code `200`:
```json
{
  "point1": {
    "x": 0.5,
    "y": 1
  },
  "point2": {
    "x": 1,
    "y": 0.5
  }
}
```

Example of failure response code `404`


### `/containment` endpoint

Containment api will not calculate if the rectangles are exactly the same.

Example of success response with code `200`:
```json
{
   "isContained": true
}
```

Example of negative response with code `200`:
```json
{
   "isContained": false
}
```

### `/adjacency` endpoint

Adjacency api will not calculate if one rectangle is contained in another rectangle.

Example of success response with code `200`:
```json
{
   "isAdjacent": true
}
```

Example of negative response with code `200`:
```json
{
   "isAdjacent": false
}
```

## Build

To run the project, use maven command: `mvn spring-boot:run`
