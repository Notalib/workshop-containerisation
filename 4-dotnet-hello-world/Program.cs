namespace hello_world;

class Program
{
  static void Main(string[] args)
  {
    string env = Environment.GetEnvironmentVariable("DOTNET_ENVIRONMENT") ?? "None";
    Console.WriteLine($"Hello, World! in environment {env}");
  }
}
