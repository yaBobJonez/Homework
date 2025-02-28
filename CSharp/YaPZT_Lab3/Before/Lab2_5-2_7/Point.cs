using System.Numerics;

namespace Lab2_5_2_7;

public class Point<T> where T : INumber<T>
{
    private T x, y;
    public Point(T x, T y)
    {
        this.x = x;
        this.y = y;
    }
    public Point() : this((T)(dynamic)0, (T)(dynamic)0){}
    public T X { get => x; protected set => x = value; }
    public T Y { get => y; protected set => y = value; }
}