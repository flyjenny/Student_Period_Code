+--------------------------+
|PROGRAM NAME: intersection|
+--------------------------+
INPUT FORMAT:

Line 1:		integer H(1< H < 20), the number of horizontal lines
Line 2:		integer V(1< V < 20), the number of vertical lines
Lines
3..H+2: 	H triple integer, first is the X coordinate of line head, second one is the X coordinate of line tail, and the last one is the Y coordinate of the horizonal line(1 <= echo one <= 1000)
Lines
H+3..V+H+2: V triple integer, first is the Y coordinate of line head, second one is the Y coordinate of line tail, and the last one is the X coordinate of the vertical line(1 <= each one <= 1000)

SAMPLE INPUT (file intersection.in)

2
3
1 6 3
8 10 2
1 4 2
2 5 5
1 4 9

OUTPUT FORMAT (file intersection.out)

output file contain several lines:
Line 1:		N The number of intersection vertexs
Line s
2..N+1:		The coordinate of interseciotn vertexs(first is the X coordinate, second is the Y coordinate), one vertex one line
PS: if no intersection vertex exist, just ouput 0 into the output file

SAMPLE OUTPUT (file intersection.out)

3
2 3
5 3
9 2

