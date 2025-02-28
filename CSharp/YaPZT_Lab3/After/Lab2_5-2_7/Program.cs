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
        Console.WriteLine(usage);
        while(true)
        {
            string[] input = Console.ReadLine()!.Split(' ');
            string command = input[0];
            if (command is "exit") break;
            if (command is "help")
            {
                Console.WriteLine(usage);
                continue;
            }
            try
            {
                switch (command)
                {
                    case "d":
                        graph(ProcessTriangle<double>(input));
                        break;
                    case "f":
                        graph(ProcessTriangle<float>(input));
                        break;
                    default:
                        Console.Error.WriteLine("Некоректний формат вводу!");
                        break;
                }
            }
            catch (ArgumentException e)
            {
                Console.Error.WriteLine(e.Message);
            }
            catch (FormatException)
            {
                Console.Error.WriteLine("Неприйнятні аргументи!");
            }
        }
    }

    private static Triangle<T> ProcessTriangle<T>(string[] input) where T : IParsable<T>, INumber<T>
    {
        var coords = input.Skip(1)
            .Select(p => p.Split(',').Select(n => T.Parse(n, null)))
            .SelectMany(p => p)
            .ToArray();
        if (coords.Length != 6)
            throw new ArgumentException("Некоректний формат вводу!");
        var p1 = new Point<T>(coords[0], coords[1]);
        var p2 = new Point<T>(coords[2], coords[3]);
        var p3 = new Point<T>(coords[4], coords[5]);
        return new Triangle<T>(p1, p2, p3);
    }
}