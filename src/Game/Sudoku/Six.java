package Game.Sudoku;

import Tool.DataStructure.tuple.Pair;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Six
{
    private final int size = 6;
    private final int[][] Graph = new int[size][size];
    private final boolean[][] Fixed = new boolean[size][size];
    private boolean isOpen = false;

    private Set<Integer> getFull()
    {
        return Set.of(1,2,3,4,5,6);
    }

    public Six(boolean bool)
    {
        if (bool)
        {
            for (int i = 0; i < size; i++) for (int j = 0; j < size; j++) Graph[i][j] = 0;
        } else
        {
            Scanner sc = new Scanner(System.in);
            for (int i = 0; i < size; i++)
            {
                for (int j = 0; j < size; j++)
                {
                    Graph[i][j] = sc.nextInt();
                    if (Graph[i][j] != 0) Fixed[i][j] = true;
                }
            }
        }
    }

    @Override
    public String toString()
    {
        StringBuilder s = new StringBuilder();
        s.append("\n");
        for (int i = 0; i < size; i++)
        {
            if (i % 2 == 0 && i != 0) s.append("----------------------\n");
            for (int j = 0; j < size; j++)
            {
                if (j == size / 2) s.append(" | ");
                if (Fixed[i][j]) s.append("[").append(Graph[i][j]).append("]");
                else s.append(" ").append(Graph[i][j]).append(" ");
            }
            s.append("\n");
        }
        return s.toString();
    }

    public void setMap(int i, int j, int x)
    {
        if (i >= size || i < 0 || j >= size || j < 0) System.out.println("Error");
        Graph[--i][--j] = x;
        if (isOpen) Fixed[i][j] = true;
    }

    public void openSet()
    {
        isOpen = true;
    }

    public void closeSet()
    {
        isOpen = false;
    }

    private Pair<Pair<Integer, Integer>, Pair<Integer, Integer>> square(int i, int j)
    {
        int I=i/2*2,J=j/2*2;
        return new Pair<>(new Pair<>(I, J), new Pair<>(I, J+1));
    }

    private boolean isVaild(int x, int op)
    {
        Set<Integer> set= new HashSet<>(getFull());

        if(op==1)//竖着
        {
            for (int i=0;i<size;i++)
            {
                int t= Graph[i][x];
                if(t!=0)
                {
                    if(set.contains(t)) set.remove(t);
                    else return false;
                }
            }
        }
        if(op==2)//横着
        {
            for (int i=0;i<size;i++)
            {
                int t= Graph[x][i];
                if(t!=0)
                {
                    if(set.contains(t)) set.remove(t);
                    else return false;
                }
            }
        }
        if(op==3)//框框
        {
            //var bianjie=square()
        }
        return true;
    }

}
//0 0 3 0 0 0 6 0 0 0 3 0 0 2 0 0 0 4 4 0 0 0 2 0 0 5 0 0 4 6 0 0 0 1 5 0