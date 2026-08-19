
import main.java.br.pucpr.user.Theme;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Locale;

public class PlanetasPrinter {

    private static final DecimalFormatSymbols SYMBOLS = new DecimalFormatSymbols(new Locale("pt", "BR"));
    private static final DecimalFormat DIAMETER_FORMAT = new DecimalFormat("#,##0.0", SYMBOLS);
    private static final DecimalFormat KM_FORMAT = new DecimalFormat("#,##0", SYMBOLS);
    private static final DecimalFormat UA_FORMAT = new DecimalFormat("#,##0.00", SYMBOLS);
    private static final double KM_PER_UA = 149_597_870.7;

    public void print(ArrayList<Planet> planets, boolean alignRight, Theme theme) {
        if (planets == null || planets.isEmpty()) {
            System.out.println("ERRO: Lista de planetas vazia ou nula.");
            return;
        }
        final var borderChar = theme.getBorderChar();

        // Borda superior e cabeçalho
        final var BORDER_WIDTH = 80;
        var sb = new StringBuilder();
        sb.repeat(borderChar, BORDER_WIDTH).append("\n");
        sb.append(String.format("| %-12s | %-12s | %-15s | %-15s | %-10s |%n", "Nome", "Diâmetro", "Dist. sol (km)", "Dist. sol (ua)", "Tipo"));
        sb.repeat(borderChar, BORDER_WIDTH).append("\n");

        for (var planet : planets) {
            if (planet == null) {
                continue;
            }
            sb.append(
                    String.format(
                            "| %-12s | %-12s | %-15s | %-15s | %-10s |%n",
                            formatName(planet),
                            formatDiameter(planet.diameterKm()),
                            formatSunDistanceKm(planet.sunDistanceKm()),
                            formatSunDistanceUa(planet.sunDistanceKm()),
                            formatType(planet.type())));
        }
        // Borda inferior
        sb.repeat(borderChar, BORDER_WIDTH).append("\n");

        // Espaçamento
        if (alignRight) {
            var lines = sb.toString().split("\n");
            for (var line : lines) {
                System.out.println("                    " + line);
            }
        } else {
            System.out.print(sb);
        }
    }

    private static String formatName(Planet planet) {
        return planet != null ? planet.name() : "null";
    }

    private static String formatDiameter(double diameter) {
        return DIAMETER_FORMAT.format(diameter);
    }

    private static String formatSunDistanceKm(long distanceKm) {
        return KM_FORMAT.format(distanceKm);
    }

    private static String formatSunDistanceUa(long distanceKm) {
        double ua = distanceKm / KM_PER_UA;
        return UA_FORMAT.format(ua);
    }

    private static String formatType(PlanetType type) {
        if (type == null) {
            return "null";
        }
        return switch (type) {
            case ROCK -> "Rochoso";
            case GAS -> "Gasoso";
            case ICE -> "Gelado";
            case DWARF -> "Anão";
        };
    }
}

/*
Sua solução ficou com muito código duplicado?
Sim, utilizei o formato do UserPrinter para fazer o print do PlanetasPrinter, além de reutilizar a lógica de nome de users para planet.

O que aconteceria se uma terceira classe tivesse que ser adicionada?
Faria a cópia da estrutura e mudaria(se fosse necessário) apenas a lógica.
*/