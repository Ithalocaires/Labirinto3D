import java.util.Random;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Maze {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Pede ao usuário o tamanho do labirinto desejado
        System.out.print("Digite o tamanho do labirinto (x y z): ");
        int x = scanner.nextInt();
        int y = scanner.nextInt();
        int z = scanner.nextInt();

        // Cria uma labirinto randômico
        int[][][] maze = new int[x][y][z];
        Random random = new Random();
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                for (int k = 0; k < z; k++) {
                    maze[i][j][k] = random.nextInt(100);
                }
            }
        }

        // Pede ao usuário as coordenadas de entrada e saída
        System.out.print("Digite as coordenadas do ponto de entrada (x y z): ");
        int start_x = scanner.nextInt() - 1;
        int start_y = scanner.nextInt() - 1;
        int start_z = scanner.nextInt() - 1;

        System.out.print("Digite as coordenadas do ponto de saída (x y z): ");
        int end_x = scanner.nextInt() - 1;
        int end_y = scanner.nextInt() - 1;
        int end_z = scanner.nextInt() - 1;

        // remove as paredes das coordenadas de entrada e saída
        maze[start_x][start_y][start_z] = 0;
        maze[end_x][end_y][end_z] = 0;

        // recebe o valor de path após passar pela função findPath e imprime para o
        // usuário
        List<int[]> path = new ArrayList<>();
        exibirMatriz(maze);
        int minCost = findPath(maze, start_x, start_y, start_z, end_x, end_y, end_z, path);
        System.out.println("Menor soma: " + minCost);

        // a variável array recebe o valor de path
        for (int[] array : path) {
            System.out.println("Caminho: X:" + array[0] + " Y:" + array[1] + " Z:" + array[2]);
        }

    }

    public static int findPath(int[][][] maze, int x, int y, int z, int targetX, int targetY, int targetZ,
            List<int[]> path) {

        // essas variáveis armazenam o tamanho do labirinto diretamente
        int xSize = maze.length;
        int ySize = maze[0].length;
        int zSize = maze[0][0].length;

        // verifica se a posição atual está dento do labirinto
        if (x < 0 || x >= xSize || y < 0 || y >= ySize || z < 0 || z >= zSize || maze[x][y][z] == -1) {
            return Integer.MAX_VALUE;
        }

        // verifica se a posição atual é a posição do destino
        if (x == targetX && y == targetY && z == targetZ) {
            path.add(new int[] { x, y, z });
            return maze[x][y][z];
        }
        int aux = maze[x][y][z];
        maze[x][y][z] = -1;

        // matriz representando as direções nas quais são possíveis o programa percorrer
        List<int[]> directions = new ArrayList<>();
        directions.add(new int[] { 1, 0, 0 });
        directions.add(new int[] { -1, 0, 0 });
        directions.add(new int[] { 0, 1, 0 });
        directions.add(new int[] { 0, -1, 0 });
        directions.add(new int[] { 0, 0, 1 });
        directions.add(new int[] { 0, 0, -1 });
        int minCost = Integer.MAX_VALUE;
        List<int[]> minPath = null;

        // a partir da direção que o programa resolveu seguir é atribuído o valor da
        // nova posição
        for (int[] dir : directions) {
            int newX = x + dir[0], newY = y + dir[1], newZ = z + dir[2];
            List<int[]> newPath = new ArrayList<>();
            int cost = findPath(maze, newX, newY, newZ, targetX, targetY, targetZ, newPath);
            if (-1 < cost && cost < minCost) {
                minCost = cost;
                minPath = newPath;
            }
        }

        // verifica se o caminho mais curto existe
        if (minPath != null) {
            path.add(new int[] { x, y, z });
            path.addAll(minPath);
        }
        return minCost + aux;
    }

    // função responsável por exibir a matriz que foi gerada randomicamente nas
    // linhas 17 a 25
    public static void exibirMatriz(int[][][] matriz) {
        for (int i = 0; i < matriz.length; i++) {
            System.out.println("Nível " + i + ":");
            for (int j = 0; j < matriz[i].length; j++) {
                for (int k = 0; k < matriz[i][j].length; k++) {
                    System.out.print(matriz[i][j][k] + " ");
                }
                System.out.println();
            }
            System.out.println();
        }
    }
}
