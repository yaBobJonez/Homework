using System.Numerics;

namespace Lab2_5_2_7;

public class Program
{
    static string usage = "Використання:\nf <x1>,<y1> <x2>,<y2> <x3>,<y3> - трикутник типу float\n" +
                          "d <x1>,<y1> <x2>,<y2> <x3>,<y3> - трикутник типу double\nexit - вийти";

    static void graph<T>(Triangle<T> t) where T : INumber<T>
    {
        Console.WriteLine("        {0:0.00};{1:0.00}        ", t.P2.X, t.P2.Y);
        Console.WriteLine("           /\\             ");
        Console.WriteLine("          /  \\            ");
        Console.WriteLine("    {0:0.00} /    \\ {1:0.00}    ", t.Side12, t.Side23);
        Console.WriteLine("        /      \\          ");
        Console.WriteLine("       /        \\         ");
        Console.WriteLine("      /___{0:0.00}___\\        ", t.Side31);
        Console.WriteLine("{0:0.00};{1:0.00}    {2:0.00};{3:0.00} ", t.P1.X, t.P1.Y, t.P3.X, t.P3.Y);
        Console.WriteLine("Площа: {0}", Math.Round((dynamic)t.GetArea(), 2));
        Console.WriteLine("Периметр: {0}", Math.Round((dynamic)t.GetPerimeter(), 2));
        Console.WriteLine("Цей трикутник є {0}", t.Type);
    }
    
    public static void Main(string[] args)
    {
        string input;
        Triangle<double> d;
        Triangle<float> f;
        Console.WriteLine(usage);
        while(true)
        {
            if ((input = Console.ReadLine()) == "exit") break;
            string[] parsed = input.Split(" ");
            if(parsed[0] == "help"){ Console.WriteLine(usage); continue; }
            if(parsed.Length != 4){ Console.Error.WriteLine("Некоректний формат вводу!"); continue; }
            string[] p1 = parsed[1].Split(",");
            string[] p2 = parsed[2].Split(",");
            string[] p3 = parsed[3].Split(",");
            if(p1.Length != 2 || p2.Length != 2 || p3.Length != 2){ Console.Error.WriteLine("Неприйнятні аргументи!"); continue; }
            switch(parsed[0])
            {
                case "d": try {
                    if(!double.TryParse(p1[0], out double p1x) || !double.TryParse(p1[1], out double p1y)
                       || !double.TryParse(p2[0], out double p2x) || !double.TryParse(p2[1], out double p2y)
                       || !double.TryParse(p3[0], out double p3x) || !double.TryParse(p3[1], out double p3y))
                    { Console.Error.WriteLine("Неприйнятні аргументи!"); continue; }
                    var P1 = new Point<double>(p1x, p1y);
                    var P2 = new Point<double>(p2x, p2y);
                    var P3 = new Point<double>(p3x, p3y);
                    d = new Triangle<double>(P1, P2, P3);
                    graph(d);
                    } catch(Exception e){ Console.Error.WriteLine(e.Message); continue; }
                    break;
                case "f": try {
                    if(!float.TryParse(p1[0], out float p1x) || !float.TryParse(p1[1], out float p1y)
                       || !float.TryParse(p2[0], out float p2x) || !float.TryParse(p2[1], out float p2y)
                       || !float.TryParse(p3[0], out float p3x) || !float.TryParse(p3[1], out float p3y))
                    { Console.Error.WriteLine("Неприйнятні аргументи!"); continue; }
                    var P1 = new Point<float>(p1x, p1y);
                    var P2 = new Point<float>(p2x, p2y);
                    var P3 = new Point<float>(p3x, p3y);
                    f = new Triangle<float>(P1, P2, P3);
                    graph(f);
                    } catch(Exception e){ Console.Error.WriteLine(e.Message); continue; }
                    break;
                default:
                    Console.Error.WriteLine("Некоректний формат вводу! (help - використання)");
                    break;
            }
        }
    }
}