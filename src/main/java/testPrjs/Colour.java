package testPrjs;
/*
 * A map is called n-colourable if each region of the map can be assigned a colour
from n different colours such that no two adjacent regions have the same colour. The
four colour conjecture is that every map is 4-colourable. In 1890 Heawood proved
that every map is 5-colourable. In 1976 Appel and Haken proved the four colour
conjecture with extensive use of computer calculations.
We can describe the m regions of a map using a m x m adjacency matrix A where
A;j = 1 if region i is adjacent to region j and A;j = 0 otherwise. We set A;i = O. For
the fitness function we can determine the number of adjacent regions which have
the same colour. The lower the number, the fitter the individual.
 */
public class Colour {
	static int population = 1000;
	static double mu = 0.01;

	public static void main(String[] args) {
		int[][] adjM = { { 0, 1, 0, 1, 0, 0, 0, 0, 0, 0 }, { 1, 0, 1, 0, 0, 1, 0, 0, 0, 0 },
				{ 0, 1, 0, 0, 0, 0, 1, 0, 0, 0 }, { 1, 0, 0, 0, 1, 1, 0, 0, 0, 0 }, { 0, 0, 0, 1, 0, 1, 0, 1, 0, 0 },
				{ 0, 1, 0, 1, 1, 0, 1, 0, 1, 1 }, { 0, 0, 1, 0, 0, 1, 0, 0, 0, 1 }, { 0, 0, 0, 0, 1, 0, 0, 0, 1, 0 },
				{ 0, 0, 0, 0, 0, 1, 0, 1, 0, 1 }, { 0, 0, 0, 0, 0, 1, 1, 0, 1, 0 } };
		System.out.println(GA(adjM, "RGBY", 10));
	}

	static int fitness(int[][] adjM, String s, int N) {

		int count = 0;
		for (int i = 0; i < N - 1; i++)

		{
			for (int j = i + 1; j < N; j++) {
				if ((s.charAt(i) == s.charAt(j)) && (adjM[i][j] == 1))
					count++;
			}
		}
		return count;
	}

	static void mutate(String[] p, String colors) {
		int j;
		for (int i = 0; i < p.length; i++) {
			if (Math.random() < mu) {
				int pos = (int) (Math.random() * (p[i].length() - 1));
				int mut = (int) (Math.random() * (colors.length() - 2));
				char[] ca1 = p[i].toCharArray();
				char[] ca2 = colors.toCharArray();
				for (j = 0; ca1[pos] != ca2[j]; j++) {
				}
				;
				ca1[pos] = ca2[(j + mut) % colors.length()];
				p[i] = new String(ca1);
			}
		}
	}

	static void crossing(String[] p, int[][] adjM) {

		int pl = (int) (Math.random() * (p.length - 1));
		int p2 = pl;
		int cl = (int) (Math.random() * (p[0].length() - 1));
		int c2 = cl;
		while (p2 == pl)
			p2 = (int) (Math.random() * (p.length - 1));
		while (c2 == cl)
			c2 = (int) (Math.random() * (p[0].length() - 1));
		if (c2 < cl) {
			int temp = c2;
			c2 = cl;
			cl = temp;
		}

		String[] temp = new String[4];
		temp[0] = p[pl];
		temp[1] = p[p2];
		temp[2] = p[pl].substring(0, cl) + p[p2].substring(cl + 1, c2) + p[pl].substring(c2 + 1, p[pl].length() - 1);
		temp[3] = p[p2].substring(0, cl) + p[pl].substring(cl + 1, c2) + p[p2].substring(c2 + 1, p[p2].length() - 1);

		int i, f;
		for (i = 0, f = 0; i < 4; i++) {
			if (fitness(adjM, temp[i], temp[i].length()) > fitness(adjM, temp[f], temp[f].length()))
				f = i;
		}

		{
			String tmp = temp[f];
			temp[f] = temp[0];
			temp[0] = tmp;
		}
		for (i = 1, f = 1; i < 4; i++) {
			if (fitness(adjM, temp[i], temp[i].length()) > fitness(adjM, temp[f], temp[f].length()))
				f = i;
		}
		{
			String tmp = temp[f];
			temp[f] = temp[1];
			temp[1] = tmp;
		}
		p[pl] = temp[2];
		p[p2] = temp[3];
	}

	static String GA(int[][] adjM, String colors, int N) {
		int maxfitness, mfi = 0;
		String[] p = new String[population];
		char[] temp = new char[N];
		for (int i = 0; i < population; i++) {
			for (int j = 0; j < N; j++) {
				temp[j] = colors.charAt((int) ((Math.random() * colors.length())));
			}
			p[i] = new String(temp);
		}
		maxfitness = fitness(adjM, p[0], p[0].length());
		while (maxfitness != 0) {
			mutate(p, colors);
			crossing(p, adjM);
			for (int i = 0; i < p.length; i++) {
				if (fitness(adjM, p[i], p[i].length()) < maxfitness) {
					maxfitness = fitness(adjM, p[i], p[i].length());
					mfi = i;
				}
			}
		}
		return p[mfi];
	}
}