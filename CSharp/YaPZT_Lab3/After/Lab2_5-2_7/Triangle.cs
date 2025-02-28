using System.Numerics;

namespace Lab2_5_2_7;

public class Triangle<T> where T : INumber<T>
{
    public Point<T> P1 { get; protected set; }
    public Point<T> P2 { get; protected set; }
    public Point<T> P3 { get; protected set; }
    public T Side12 {
        get
        {
            double s = Math.Sqrt(Math.Pow((dynamic)(P2.X - P1.X), 2) + Math.Pow((dynamic)(P2.Y - P1.Y), 2));
            return typeof(T) == typeof(float) ? (dynamic)(float)s : (dynamic)s;
        }
    }
    public T Side23 {
        get
        {
            double s = Math.Sqrt(Math.Pow((dynamic)(P3.X - P2.X), 2) + Math.Pow((dynamic)(P3.Y - P2.Y), 2));
            return typeof(T) == typeof(float) ? (dynamic)(float)s : (dynamic)s;
        }
    }

    public T Side31
    {
        get
        {
            double s = Math.Sqrt(Math.Pow((dynamic)(P1.X - P3.X), 2) + Math.Pow((dynamic)(P1.Y - P3.Y), 2));
            return typeof(T) == typeof(float) ? (dynamic)(float)s : (dynamic)s;
        }
    }

    public Triangle(Point<T> p1, Point<T> p2, Point<T> p3)
    {
        if( (dynamic)(p1.X * (p2.Y - p3.Y) + p2.X * (p3.Y - p1.Y) + p3.X * (p1.Y - p2.Y)) == 0.0 )
            throw new ArgumentException("Точки не утворюють трикутник!");
        this.P1 = p1;
        this.P2 = p2;
        this.P3 = p3;
    }
    public T GetPerimeter() => Side12 + Side23 + Side31;
    public T GetArea()
    {
        T s = (dynamic)(Side12 + Side23 + Side31) / 2;
        double res = Math.Sqrt( (dynamic)(s * (s - Side12) * (s - Side23) * (s - Side31)) );
        return typeof(T) == typeof(float) ? (dynamic)(float)res : (dynamic)res;
    }
    public string Type
    {
        get
        {
            if (Side12 == Side23 && Side23 == Side31) return TriangleType.Equilateral;
            bool isRight = Math.Round((dynamic)Side12 * Side12 + Side23 * Side23, 5) == Math.Round((dynamic)Side31 * Side31, 5)
                           || Math.Round((dynamic)Side12 * Side12 + Side31 * Side31, 5) == Math.Round((dynamic)Side23 * Side23, 5)
                           || Math.Round((dynamic)Side23 * Side23 + Side31 * Side31, 5) == Math.Round((dynamic)Side12 * Side12, 5);
            bool isIsosceles = Side12 == Side23 || Side23 == Side31 || Side31 == Side12;
            return (isRight && isIsosceles)? TriangleType.RightIsosceles
                : isRight ? TriangleType.Right
                : isIsosceles ? TriangleType.Isosceles
                : TriangleType.Scalene;
        }
    }
}