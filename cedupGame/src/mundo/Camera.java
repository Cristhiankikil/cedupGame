package mundo;

import main.Game;

public class Camera {
    public static int x, y;

    public static void update(int playerX, int playerY) {
        x = playerX - Game.WIDTH / 2;
        y = playerY - Game.HEIGHT / 2;

        // Garantir que a câmera não mova para fora dos limites
        x = Math.max(0, Math.min(x, Mundo.WIDTH - Game.WIDTH));
        y = Math.max(0, Math.min(y, Mundo.HEIGHT - Game.HEIGHT));
    }


    public static int Clamp(int inicio, int minimo, int maximo) {
        if (inicio < minimo) {
            inicio = minimo;
        }
        if (inicio > maximo) {
            inicio = maximo;
        }
        return inicio;
    }
}
